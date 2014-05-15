/*
 *  OrderInfoPanel.java
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

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.escoladeltreball.arcowabungaproject.model.Order;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.Product;

public class OrderInfoPanel extends JPanel {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    private Order order;
    private JLabel jlIdOrder;
    private JPanel jpContacInfo;
    private JPanel[] jpPizzas;
    private JPanel[] jpDrinks;
    private JPanel[] jpOffers;
    private int numOfPizzas;
    private int numOfDrinks;
    private int numOfOffers;

    // ====================
    // CONSTRUCTORS
    // ====================
    public OrderInfoPanel(Order order, int[] numOfProducts) {
	this.order = order;
	this.numOfPizzas = numOfProducts[0];
	this.numOfDrinks = numOfProducts[1];
	this.numOfOffers = numOfProducts[2];
	this.initComponents();
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
    private void initComponents() {
	this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	this.jlIdOrder = new JLabel("Order Id: " + this.order.getId());
	this.jpContacInfo = new ContactInfoPanel(this.order);
	addProductsInfo();
	this.add(jlIdOrder);
	this.add(jpContacInfo);
	this.add(new JSeparator(JSeparator.HORIZONTAL));
	for (int i = 0; i < jpPizzas.length; i++) {
	    this.add(jpPizzas[i]);
	}
    }

    private void addProductsInfo() {
	this.jpPizzas = new JPanel[this.numOfPizzas];
	int indexPizzas = 0;
	ArrayList<Product> products = (ArrayList<Product>) this.order
		.getShoppingCart().getProducts();
	for (Product product : products) {
	    if (product instanceof Pizza) {
		Pizza pizza = (Pizza) product;
		this.jpPizzas[indexPizzas] = new PizzaPanel(pizza);
		indexPizzas++;
	    }
	    // Drinks
	    // Offers
	}
    }
    // ====================
    // OVERRIDE METHODS
    // ====================

    // ====================
    // GETTERS & SETTERS
    // ====================
}