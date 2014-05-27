/*
 *  DatabaseUpdateClient.java
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

import java.io.IOException;
import java.util.Set;

import org.escoladeltreball.arcowabungaproject.model.Drink;
import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.model.Offer;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.system.ServerConstants;

public class DatabaseUpdateClient extends Client {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    private Set<Ingredient> ingredients;
    private Set<Pizza> predefinedPizzas;
    private Set<Drink> drinks;
    private Set<Offer> offers;
    private int newDBVersion;

    private boolean update;

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * Class constructor.
     */
    public DatabaseUpdateClient() {
	super();
	option = ServerConstants.SERVER_OPTION_DATABASE_UPDATE;
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

    /**
     * Connect to DatabaseUpdateServer and update database if needed
     * 
     * @param port
     *            the server port
     * @return true if database is updated, false otherwise
     */
    @SuppressWarnings("unchecked")
    private boolean conectToDatabaseUpdateServer(int port) {
	if (port != 0) {
	    init(port);
	    try {
		// Send the client database version
		// out.writeInt(DAOFactory.getInstance().getCurrentVersion());
		out.writeInt(1);
		out.flush();
		System.out.println("Versio enviada");
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    // Read from server if need update
	    int serverResponse = readInt();
	    if (serverResponse == ServerConstants.SERVER_NEED_UPDATE) {
		// Read from server the ingredient's set
		ingredients = (Set<Ingredient>) readObject();
		System.out.println(ingredients);
		// Read from server the pizza's set
		predefinedPizzas = (Set<Pizza>) readObject();
		System.out.println(predefinedPizzas);
		// Read from server the drink's set
		drinks = (Set<Drink>) readObject();
		System.out.println(drinks);
		// Read from server the offer's set
		offers = (Set<Offer>) readObject();
		System.out.println(offers);
		// Read from server new version
		newDBVersion = readInt();
		System.out.println(newDBVersion);
		try {
		    // Send to server update confirmation
		    out.writeInt(ServerConstants.CLIENT_RESPONSE_OK);
		    out.flush();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		update = true;
		// Read from server to close connection
		serverResponse = readInt();
	    }

	    close();
	}
	return update;
    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    @Override
    public boolean connect() {
	tryIPs();
	int newPort = connectToHallServer(option);
	return conectToDatabaseUpdateServer(newPort);
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

    /**
     * @return the ingredients
     */
    public Set<Ingredient> getIngredients() {
	return ingredients;
    }

    /**
     * @return the predefinedPizzas
     */
    public Set<Pizza> getPredefinedPizzas() {
	return predefinedPizzas;
    }

    /**
     * @return the drinks
     */
    public Set<Drink> getDrinks() {
	return drinks;
    }

    /**
     * @return the offers
     */
    public Set<Offer> getOffers() {
	return offers;
    }

    /**
     * @return the newDBVersion
     */
    public int getNewDBVersion() {
	return newDBVersion;
    }

    public boolean isUpdate() {
	return update;
    }
}
