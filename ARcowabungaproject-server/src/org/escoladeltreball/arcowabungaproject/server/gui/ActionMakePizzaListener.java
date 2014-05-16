/*
 *  ActionMakePizzaListener.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Copyright 2014 	Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  			Marc Sabate Piñol <masapim@hotmail.com>
 *  			Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  			Joaquim Dalmau Torva <jdalmaut@gmail.com>
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
package org.escoladeltreball.arcowabungaproject.server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionMakePizzaListener implements ActionListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    OrderPanel jpOrder;

    // ====================
    // CONSTRUCTORS
    // ====================
    public ActionMakePizzaListener(OrderPanel jpOrder) {
	this.jpOrder = jpOrder;
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
    public void actionPerformed(ActionEvent e) {
	OrderManagerPanel omp = OrderManagerPanel.getInstance();
	omp.getJpWaitOrders().remove(this.jpOrder);
	this.jpOrder.getJbMakePizza().setText("Send Pizza!");
	omp.getJpMakingOrders().add(this.jpOrder);
	omp.repaint();
    }
    // ====================
    // GETTERS & SETTERS
    // ====================

}
