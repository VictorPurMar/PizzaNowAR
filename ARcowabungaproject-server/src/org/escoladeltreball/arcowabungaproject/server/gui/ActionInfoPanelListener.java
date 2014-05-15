package org.escoladeltreball.arcowabungaproject.server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.escoladeltreball.arcowabungaproject.model.Order;

/*
 *  ActionInfoPanelListener.java
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
 *   along with ARcowabungaprojstaticect.  If not, see <http://www.gnu.org/licenses/>. 
 */

public class ActionInfoPanelListener implements ActionListener {

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
    public ActionInfoPanelListener(Order order) {
	this.order = order;
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
	JPanel jpInfo = new OrderInfoPanel(this.order,
		this.order.numOfDifferentsProductsInOrder());
	OrderManagerPanel.getInstance().setJpInfo(jpInfo);

    }
    // ====================
    // GETTERS & SETTERS
    // ====================
}
