/*
 *  Client.java
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
package org.escoladeltreball.arcowabungaproject.model.system.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.escoladeltreball.arcowabungaproject.model.system.ServerConstants;

public abstract class Client {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    /**
     * Attributes for a socket connection.
     */
    protected Socket socket;

    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    protected int option;

    protected static String ipAddress;
    protected static List<String> frequentIPAddress;

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * Class constructor.
     */
    public Client() {
	frequentIPAddress = new ArrayList<String>();
	frequentIPAddress.add("localhost");

	frequentIPAddress.add("192.168.43.119");
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    public abstract boolean connect();

    // ====================
    // PROTECTED METHODS
    // ====================

    /**
     * Open a connection to the hall server and return the port of this
     * connection.
     * 
     * @param option
     *            an integer value
     * @return an integer value, the connection's port
     */
    protected int connectToHallServer(int option) {
	init(4444);
	int newport = 0;
	try {
	    out.writeInt(option);
	    out.flush();
	    newport = readInt();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	close();
	return newport;
    }

    /**
     * Initialize the socket.
     * 
     * @param port
     *            an integer value
     */
    protected void init(int port) {
	try {
	    socket = new Socket(ipAddress, port);
	    out = new ObjectOutputStream(new BufferedOutputStream(
		    socket.getOutputStream()));
	    out.flush();
	    in = new ObjectInputStream(new BufferedInputStream(
		    socket.getInputStream()));
	    System.out.println("conectat al port " + port);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Close the socket.
     */
    protected void close() {
	try {
	    out.close();
	    in.close();
	    socket.close();
	    out = null;
	    in = null;
	    socket = null;
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Read an integer value of the input socket.
     * 
     * @return an integer value
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
     * Read an object of the input socket.
     * 
     * @return an Object
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

    /**
     * Try the connection with one IP value.
     * 
     * @return true if the connection was successful, false if it is not
     */
    protected boolean tryIP() {
	try {
	    socket = new Socket(ipAddress, ServerConstants.HALL_SERVER_PORT);
	    socket.close();
	    socket = null;
	} catch (UnknownHostException e) {
	    return false;
	} catch (IOException e) {
	    return false;
	}
	return true;
    }

    /**
     * 
     * Try the connection with all the IP avaliables.
     * 
     * @return true if the connection was successful, false if it is not
     */
    protected boolean tryIPs() {
	boolean foundIP = false;
	for (int i = 0; i < frequentIPAddress.size() && !foundIP; i++) {
	    ipAddress = frequentIPAddress.get(i);
	    foundIP = tryIP();
	}
	for (int n = 11; n < 255 && !foundIP; n++) {
	    ipAddress = ServerConstants.IP_ADRESS_PREFIX + n;
	    foundIP = tryIP();
	}
	return true;
    }

    // ====================
    // PRIVATE METHODS
    // ====================

    // ====================
    // OVERRIDE METHODS
    // ====================

    // ====================
    // GETTERS & SETTERS
    // ====================

}
