/*
 *  MyDialogAddIngredient.java
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
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.server.dao.DAOPostgreSQL;

public class MyDialogAddIngredient extends JDialog implements ActionListener {

    // ====================
    // CONSTANTS
    // ====================

    /**
     * 
     */
    private static final long serialVersionUID = -830177032453490461L;

    // ====================
    // ATTRIBUTES
    // ====================

    private JPanel jpDialog;
    private JPanel jpDialogButton;
    private JPanel jpDialogIngredient;
    private JComboBox<String> jcbIds;
    private JComboBox<String> jcbNames;
    private JSpinner jsQuantity;
    private JButton jbAdd;
    private HashSet<String> diferentsIngredientsIds;

    // ====================
    // CONSTRUCTORS
    // ====================

    public MyDialogAddIngredient(Frame owner, String title, boolean modal,
	    HashSet<String> diferentsIngredientsIds) {
	super(owner, title, modal);
	this.diferentsIngredientsIds = diferentsIngredientsIds;
	this.initComponents();
    }

    // ====================
    // PUBLIC METHODS
    // ====================
    /**
     * Capture the results of the insert to put in the table
     * 
     * @return An array with the new ingredient
     */
    public String[] results() {
	String[] results = { (String) this.jcbIds.getSelectedItem(),
		(String) this.jcbNames.getSelectedItem(),
		this.jsQuantity.getValue() + "" };
	return results;
    }

    // ====================
    // PROTECTED METHODS
    // ====================

    // ====================
    // PRIVATE METHODS
    // ====================

    private void initComponents() {
	this.setTitle("Add Ingredient");
	jpDialog = new JPanel();
	jpDialog.setLayout(new BoxLayout(jpDialog, BoxLayout.Y_AXIS));

	jpDialogButton = new JPanel();

	jpDialogIngredient = new JPanel();

	jpDialogIngredient.setLayout(new GridLayout(3, 2));

	String[] ids = new String[this.diferentsIngredientsIds.size() + 1];
	ids[0] = "";
	int i = 1;
	// put the ingredients id into array
	for (String id : this.diferentsIngredientsIds) {
	    ids[i] = id;
	    i++;
	}
	String[] ingredients = new String[DAOPostgreSQL.getInstance()
		.readIngredient().size() + 1];
	ingredients[0] = "";
	i = 1;
	// put the eingredients name into array
	for (Ingredient ingredient : DAOPostgreSQL.getInstance()
		.readIngredient()) {
	    ingredients[i] = ingredient.getName();
	    i++;
	}

	jpDialogIngredient.add(new JLabel("id"));

	jcbIds = new JComboBox<String>(ids);
	jcbNames = new JComboBox<String>(ingredients);
	jsQuantity = new JSpinner();

	jpDialogIngredient.add(jcbIds);
	jpDialogIngredient.add(new JLabel("ingrdient"));
	jpDialogIngredient.add(jcbNames);
	jpDialogIngredient.add(new JLabel("quantity"));
	jpDialogIngredient.add(jsQuantity);

	jbAdd = new JButton("Add");
	jbAdd.addActionListener(this);
	jpDialogButton.add(jbAdd);

	jpDialog.add(jpDialogIngredient);
	jpDialog.add(jpDialogButton);

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

	this.dispose();
    }

    // ====================
    // GETTERS & SETTERS
    // ====================
}
