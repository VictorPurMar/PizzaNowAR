/*
 *  OrderReceiverServer.java
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

import org.escoladeltreball.arcowabungaproject.model.IdObject;
import org.escoladeltreball.arcowabungaproject.model.Order;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;
import org.escoladeltreball.arcowabungaproject.model.system.ServerConstants;
import org.escoladeltreball.arcowabungaproject.server.gui.OrderManagerPanel;

public class OrderReceiverServer extends Server {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    private Order order;

    // ====================
    // CONSTRUCTORS
    // ====================

    public OrderReceiverServer(int port) {
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
	init();
	try {
	    waitClient();
	    order = (Order) readObject();
	    order = new Order(IdObject.nextCustomId(), order);
	    Pizzeria.getInstance().addOrderSaved(order);
	    OrderManagerPanel.getInstance().addWaitOrder(order);
	    print(order.toString());
	    try {
		out.writeInt(ServerConstants.SERVER_RESPONSE_OK);
		out.flush();
		// out.writeInt(ServerConstants.SERVER_END_CONNECTION);
		// out.flush();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	close();
    }
    // ====================
    // GETTERS & SETTERS
    // ====================

}
