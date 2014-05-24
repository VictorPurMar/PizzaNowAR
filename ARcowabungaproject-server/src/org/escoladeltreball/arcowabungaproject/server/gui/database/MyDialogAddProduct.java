/*
 *  MyDialogAddProduct.java
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
package org.escoladeltreball.arcowabungaproject.server.gui.database;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.escoladeltreball.arcowabungaproject.model.Drink;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;
import org.escoladeltreball.arcowabungaproject.server.dao.DAOPostgreSQL;

public class MyDialogAddProduct extends JDialog implements ActionListener,
	ItemListener {

    // ====================
    // CONSTANTS
    // ====================

    /**
     * 
     */
    private static final long serialVersionUID = 6911635406314358171L;

    // ====================
    // ATTRIBUTES
    // ====================

    private JPanel jpDialog;
    private JPanel jpChooseProduct;
    private JPanel jpDialogButton;
    private JPanel jpDialogProduct;
    private JComboBox<String> jcbProduct;
    private JComboBox<String> jcbIds;
    private JComboBox<String> jcbNames;
    private JSpinner jsQuantity;
    private JButton jbAdd;

    // ====================
    // CONSTRUCTORS
    // ====================

    public MyDialogAddProduct(Frame owner, String title, boolean modal) {
	super(owner, title, modal);

	this.initComponents();
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    public String[] results() {
	String[] results = new String[2];
	if (this.jcbIds.getSelectedItem() == null
		&& this.jcbNames.getSelectedItem() == null) {
	    results[0] = "";
	    results[1] = "";
	} else {
	    results[0] = (String) this.jcbIds.getSelectedItem();
	    results[1] = (String) this.jcbNames.getSelectedItem();
	}
	return results;
    }

    // ====================
    // PROTECTED METHODS
    // ====================

    // ====================
    // PRIVATE METHODS
    // ====================

    private void initComponents() {

	jpDialog = new JPanel();
	jpDialog.setLayout(new BoxLayout(jpDialog, BoxLayout.Y_AXIS));

	jpChooseProduct = new JPanel();

	String[] products = { "", DAOFactory.TABLE_DRINKS,
		DAOFactory.TABLE_PIZZAS };

	this.jcbProduct = new JComboBox<String>(products);
	this.jcbProduct.addItemListener(this);

	this.jpChooseProduct.add(new JLabel("Choose Product : "));
	this.jpChooseProduct.add(this.jcbProduct);

	String[] empty = { "", "" };

	jcbIds = new JComboBox<String>(empty);
	jcbNames = new JComboBox<String>(empty);

	jpDialogButton = new JPanel();

	jpDialogProduct = new JPanel();

	jpDialogProduct.setLayout(new GridLayout(2, 2));

	this.jpDialog.add(this.jpChooseProduct);
	this.getContentPane().add(jpDialog);
	this.setSize(300, 200);

	this.setVisible(true);
	this.pack();
    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    @Override
    public void actionPerformed(ActionEvent e) {
	// Close
	this.dispose();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
	this.jpDialogProduct.removeAll();
	this.jpDialogButton.removeAll();
	if (e.getItem().equals(DAOFactory.TABLE_DRINKS)) {
	    String[] ids = new String[DAOPostgreSQL.getInstance().readDrink()
		    .size() + 1];
	    String[] drinks = new String[DAOPostgreSQL.getInstance()
		    .readDrink().size() + 1];
	    drinks[0] = "";
	    int i = 1;
	    // put th eingredients name into array
	    for (Drink drink : DAOPostgreSQL.getInstance().readDrink()) {
		ids[i] = drink.getId() + "";
		drinks[i] = drink.getName();
		i++;
	    }

	    jpDialogProduct.add(new JLabel("id"));

	    jcbIds = new JComboBox<String>(ids);
	    jcbNames = new JComboBox<String>(drinks);

	    jpDialogProduct.add(jcbIds);
	    jpDialogProduct.add(new JLabel("drink"));
	    jpDialogProduct.add(jcbNames);

	    jbAdd = new JButton("Add");
	    jbAdd.addActionListener(this);
	    jpDialogButton.add(jbAdd);

	    jpDialog.add(jpDialogProduct);

	} else if (e.getItem().equals(DAOFactory.TABLE_PIZZAS)) {
	    String[] ids = new String[DAOPostgreSQL.getInstance().readPizza()
		    .size() + 1];
	    String[] pizzas = new String[DAOPostgreSQL.getInstance()
		    .readPizza().size() + 1];
	    pizzas[0] = "";
	    int i = 1;
	    // put th eingredients name into array
	    for (Pizza pizza : DAOPostgreSQL.getInstance().readPizza()) {
		ids[i] = pizza.getId() + "";
		pizzas[i] = pizza.getName();
		i++;
	    }

	    jpDialogProduct.add(new JLabel("id"));

	    jcbIds = new JComboBox<String>(ids);
	    jcbNames = new JComboBox<String>(pizzas);

	    jpDialogProduct.add(jcbIds);
	    jpDialogProduct.add(new JLabel("Pizza"));
	    jpDialogProduct.add(jcbNames);

	    jbAdd = new JButton("Add");
	    jbAdd.addActionListener(this);
	    jpDialogButton.add(jbAdd);

	    jpDialog.add(jpDialogProduct);
	}
	jpDialog.add(jpDialogButton);
	this.validate();
    }

    // ====================
    // GETTERS & SETTERS
    // ====================
}