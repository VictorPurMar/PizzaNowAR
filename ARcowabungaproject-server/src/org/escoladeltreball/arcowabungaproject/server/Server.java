/*
 *  Server.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *
 *   ARcowabungaproject is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   ARcowabungaproject is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with ARcowabungaproject.  If not, see <http://www.gnu.org/licenses/>. 
 */

package org.escoladeltreball.arcowabungaproject.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.escoladeltreball.arcowabungaproject.model.system.ServerConstants;

public abstract class Server extends Thread {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    /**
     * The server socket
     */
    protected ServerSocket serverSocket;
    /**
     * The socket for client connections
     */
    protected Socket socketService;

    /**
     * The input stream for read from the client
     */
    protected ObjectInputStream in;
    /**
     * The input stream for write to the client
     */
    protected ObjectOutputStream out;

    /**
     * The server port
     */
    private int port;
    /**
     * The flag to indicate that the server is stopped
     */
    private boolean stop;

    /**
     * A map to save all open servers
     */
    protected static Map<Integer, Server> listeningServers = new HashMap<Integer, Server>();

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * Server constructor
     * 
     * @param port
     *            the port of the server
     */
    protected Server(int port) {
	super();
	setName(getClass().getSimpleName() + ":" + port);
	this.port = port;
	stop = true;
	synchronized (listeningServers) {
	    listeningServers.put(port, this);
	}
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    /**
     * Stop the server if it is running
     */
    public void stopServer() {
	if (!stop) {
	    stop = true;
	    close();
	}
    }

    /**
     * Start the server if it is stopped
     */
    public void startServer() {
	if (stop) {
	    stop = false;
	    start();
	}
    }

    // ====================
    // PROTECTED METHODS
    // ====================

    /**
     * Wait a client to connect and open input and output streams for it
     * 
     * @throws IOException
     */
    protected void waitClient() throws IOException {
	print("Waiting client ...");
	socketService = serverSocket.accept();
	out = new ObjectOutputStream(new BufferedOutputStream(
		socketService.getOutputStream()));
	out.flush();
	in = new ObjectInputStream(new BufferedInputStream(
		socketService.getInputStream()));
	print("Client conected");
    }

    /**
     * Close a client connection and its input and output
     * 
     * @throws IOException
     */
    protected void closeClient() throws IOException {
	if (socketService != null) {
	    out.close();
	    in.close();
	    socketService.close();
	    socketService = null;
	    print("Client closed");
	}
    }

    /**
     * Close the server
     */
    protected void close() {
	try {
	    closeClient();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	try {
	    if (serverSocket != null) {
		serverSocket.close();
		print("Closed");
		serverSocket = null;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	synchronized (listeningServers) {
	    listeningServers.remove(port);
	}
    }

    /**
     * Open the server to a port
     */
    protected void init() {
	try {
	    serverSocket = new ServerSocket(port);
	    print("Opened");
	} catch (IOException e) {
	    e.printStackTrace();
	    stop = true;
	    throw new RuntimeException("Couln't open server");
	}
    }

    /**
     * Print to System.out the msg with the server name like it prefix
     * 
     * @param msg
     *            the message to be written
     */
    protected void print(String msg) {
	System.out.println(getName() + "> " + msg);
    }

    /**
     * Look for a valid port to open a server
     * 
     * @return a valid port
     */
    protected int getValidPort() {
	int newPort = ServerConstants.HALL_SERVER_PORT + 1;
	synchronized (listeningServers) {
	    while (listeningServers.containsKey(newPort)
		    || !isValidPort(newPort)) {
		newPort++;
	    }
	}
	return newPort;
    }

    /**
     * Check if the port is valid to open a server
     * 
     * @param port
     *            the port to be checked
     * @return true if the port is valid, false otherwise
     */
    protected boolean isValidPort(int port) {
	ServerSocket ss = null;
	DatagramSocket ds = null;
	try {
	    ss = new ServerSocket(port);
	    ss.setReuseAddress(true);
	    ds = new DatagramSocket(port);
	    ds.setReuseAddress(true);
	    return true;
	} catch (IOException e) {
	} finally {
	    if (ds != null) {
		ds.close();
	    }

	    if (ss != null) {
		try {
		    ss.close();
		} catch (IOException e) {
		    /* should not be thrown */
		}
	    }
	}
	return false;
    }

    /**
     * Wait until read a int from in
     * 
     * @return the read int
     */
    protected int readInt() {
	int n = 0;
	while (n == 0) {
	    try {
		n = in.readInt();
	    } catch (Exception e) {
		try {
		    Thread.sleep(500);
		} catch (InterruptedException e1) {
		    e1.printStackTrace();
		}
	    }
	}
	return n;
    }

    /**
     * Wait until read a Object from in
     * 
     * @return the read Object
     */
    protected Object readObject() {
	Object obj = null;
	while (obj == null) {
	    try {
		obj = in.readObject();
	    } catch (Exception e) {
		try {
		    Thread.sleep(500);
		} catch (InterruptedException e1) {
		    e1.printStackTrace();
		}
	    }
	}
	return obj;
    }

    // ====================
    // PRIVATE METHODS
    // ====================

    // ====================
    // OVERRIDE METHODS
    // ====================

    @Override
    public abstract void run();

    // ====================
    // GETTERS & SETTERS
    // ====================

    /**
     * Get the port
     * 
     * @return the port
     */
    public int getPort() {
	return port;
    }

    /**
     * Set the port
     * 
     * @param port
     *            the port
     */
    public void setPort(int port) {
	this.port = port;
    }

    /**
     * Get stop
     * 
     * @return true if the server is stopped, false otherwise
     */
    public boolean isStopped() {
	return stop;
    }

}
