/*
 *  DatabaseUpdateServer.java
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

import java.io.IOException;

import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;
import org.escoladeltreball.arcowabungaproject.model.system.ServerConstants;
import org.escoladeltreball.arcowabungaproject.server.dao.DAOPostgreSQL;

public class DatabaseUpdateServer extends Server {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * DatabaseUpdateServer constructor
     * 
     * @param port
     *            the port of the server
     */
    public DatabaseUpdateServer(int port) {
	super(port);
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    // ====================
    // PROTECTED METHODS
    // ====================

    // ====================
    // PRIVATE METHODS
    // ====================

    // ====================
    // OVERRIDE METHODS
    // ====================

    @Override
    public void run() {
	try {
	    init();
	    waitClient();
	    int clientDBVersion = readInt();
	    Pizzeria pizzeria = Pizzeria.getInstance();
	    DAOFactory dao = DAOPostgreSQL.getInstance();
	    int currentDBVersion = dao.getCurrentVersion();
	    if (clientDBVersion != currentDBVersion) {
		out.writeInt(ServerConstants.SERVER_NEED_UPDATE);
		out.flush();
		print("Updating client database 0%");
		out.writeObject(pizzeria.getIngredients());
		out.flush();
		print("Updating client database 20%");
		out.writeObject(pizzeria.getPredefinedPizzas());
		out.flush();
		print("Updating client database 40%");
		out.writeObject(pizzeria.getDrinks());
		out.flush();
		print("Updating client database 60%");
		out.writeObject(pizzeria.getOffers());
		out.flush();
		print("Updating client database 80%");
		out.writeInt(currentDBVersion);
		out.flush();
		print("Updating client database 100%");
		boolean ok = readInt() == ServerConstants.CLIENT_RESPONSE_OK;
		if (ok) {
		    print("Client database updated correctly");
		}
	    }
	    out.writeInt(ServerConstants.SERVER_END_CONNECTION);
	    out.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	close();
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

}
