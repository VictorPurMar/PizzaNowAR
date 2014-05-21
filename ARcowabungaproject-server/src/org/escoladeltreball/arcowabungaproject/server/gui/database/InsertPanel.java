/*
 *  InsertPanel.java
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

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.escoladeltreball.arcowabungaproject.model.IdObject;
import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.model.Ingredients;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;
import org.escoladeltreball.arcowabungaproject.server.dao.DAOPostgreSQL;

public class InsertPanel extends JPanel implements ActionListener, ItemListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    private JPanel jpDoInsert;
    private JPanel jpShowTables;
    private JButton jbInserData;
    private JLabel jlChooseTable;
    private GridBagConstraints constraints;
    private JLabel[] jlLists;
    private JTextField[] jtfList;
    private JLabel[] jlIngredients;
    private int[] idIngredients;
    private JSpinner[] jsQuantity;
    private int indexConstrainstX = 0;
    private int indexConstrainstY = 0;
    private JComboBox<String> jcbTables;

    // ====================
    // CONSTRUCTORS
    // ====================
    public InsertPanel() {
	this.initComponents();
	this.registListeners();
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
	this.jpDoInsert = new JPanel();
	this.jpDoInsert.setLayout(new GridBagLayout());
	this.jbInserData = new JButton("Insert Data");
	String[] items = { "", DAOFactory.TABLE_DRINKS,
		DAOFactory.TABLE_INGREDIENT, DAOFactory.TABLE_OFFERS,
		DAOFactory.TABLE_PIZZAS, DAOFactory.TABLE_PREFERENCES,
		DAOFactory.TABLE_RESOURCES };
	this.jlChooseTable = new JLabel("Choose Table");
	this.jcbTables = new JComboBox<>(items);

	this.constraints = new GridBagConstraints();
	this.constraints.gridx = this.indexConstrainstX;
	this.constraints.gridy = this.indexConstrainstY;
	this.jpDoInsert.add(jlChooseTable, this.constraints);
	this.constraints.gridx = ++this.indexConstrainstX;
	this.jpDoInsert.add(jcbTables, this.constraints);

	this.setLayout(new BorderLayout());
	this.jpShowTables = new JPanel();

	this.add(this.jpDoInsert, BorderLayout.WEST);
	this.add(this.jpShowTables, BorderLayout.CENTER);
    }

    private void registListeners() {
	this.jcbTables.addItemListener(this);
	this.jbInserData.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	this.jpShowTables.removeAll();
	boolean textFieldsIsEmpty = true;
	int i = 1;
	while (i < jtfList.length && textFieldsIsEmpty) {
	    if (!jtfList[i].getText().isEmpty()) {
		textFieldsIsEmpty = false;
	    }
	    i++;
	}
	if (textFieldsIsEmpty) {
	    this.jpShowTables
		    .add(new JLabel(
			    "The insertion was not done. You have not inserted any data"));
	} else {
	    String item = (String) this.jcbTables.getSelectedItem();
	    switch (item) {
	    case DAOFactory.TABLE_INGREDIENT:
		int id = Integer.parseInt(this.jtfList[0].getText());
		String name = null;
		Float price = null;
		Integer icon = null;
		Integer model = null;
		Integer texture = null;
		if (!this.jtfList[0].getText().isEmpty()) {
		    name = this.jtfList[0].getText();
		}
		if (!this.jtfList[1].getText().isEmpty()) {
		    price = Float.parseFloat(this.jtfList[1].getText());
		}
		if (!this.jtfList[2].getText().isEmpty()) {
		    model = Integer.parseInt(this.jtfList[2].getText());
		}
		if (!this.jtfList[3].getText().isEmpty()) {
		    icon = Integer.parseInt(this.jtfList[3].getText());
		}
		if (!this.jtfList[4].getText().isEmpty()) {
		    texture = Integer.parseInt(this.jtfList[4].getText());
		}
		Ingredient ingredient = new Ingredient(id, name, price, model,
			icon, texture);
		HashSet<Ingredient> ingredients = new HashSet<Ingredient>();
		ingredients.add(ingredient);
		DAOPostgreSQL.getInstance().writeIngredients(ingredients);

		break;
	    case DAOFactory.TABLE_PIZZAS:
		id = IdObject.nextId();
		name = null;
		price = null;
		icon = null;
		Float discount = null;
		String massType = null;
		String type = null;
		Integer size = null;
		if (!this.jtfList[0].getText().isEmpty()) {
		    name = this.jtfList[0].getText();
		}
		if (!this.jtfList[1].getText().isEmpty()) {
		    price = Float.parseFloat(this.jtfList[1].getText());
		}
		if (!this.jtfList[2].getText().isEmpty()) {
		    icon = Integer.parseInt(this.jtfList[2].getText());
		}
		if (!this.jtfList[3].getText().isEmpty()) {
		    discount = Float.parseFloat(this.jtfList[3].getText());
		}
		if (!this.jtfList[4].getText().isEmpty()) {
		    massType = this.jtfList[4].getText();
		}
		if (!this.jtfList[5].getText().isEmpty()) {
		    type = this.jtfList[5].getText();
		}
		if (!this.jtfList[6].getText().isEmpty()) {
		    size = Integer.parseInt(this.jtfList[6].getText());
		}
		Pizza pizza = new Pizza(id, name, price, icon, discount,
			massType, type, size);
		Ingredients ingredientsMap = new Ingredients(IdObject.nextId());
		for (i = 0; i < this.jsQuantity.length; i++) {
		    int quantity = (Integer) this.jsQuantity[i].getValue();
		    if (quantity > 0) {
			Ingredient ing = new Ingredient(this.idIngredients[i]);
			ingredientsMap.put(ing, quantity);
		    }
		}
		pizza.setIngredients(ingredientsMap);
		HashSet<Pizza> pizzas = new HashSet<Pizza>();
		pizzas.add(pizza);
		DAOPostgreSQL.getInstance().writePizzas(pizzas);
		break;
	    default:
		break;
	    }
	}
	this.validate();
    }

    /**
     * Show the text fields of the tables depends on table selected in JComboBox
     * 
     * @param e
     *            the item event
     */
    private void showTextFields(ItemEvent e) {
	String item = (String) e.getItem();
	switch (item) {
	case DAOFactory.TABLE_DRINKS:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_DRINKS.length - 1];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_DRINKS.length - 1];
	    for (int i = 1; i < DAOFactory.COLUMNS_NAME_DRINKS.length; i++) {
		this.jlLists[i - 1] = new JLabel(
			DAOFactory.COLUMNS_NAME_DRINKS[i]);
		this.jtfList[i - 1] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(this.jlLists[i - 1], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoInsert.add(this.jtfList[i - 1], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_INGREDIENT:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_INGREDIENT.length - 1];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_INGREDIENT.length - 1];
	    for (int i = 1; i < DAOFactory.COLUMNS_NAME_INGREDIENT.length; i++) {
		this.jlLists[i - 1] = new JLabel(
			DAOFactory.COLUMNS_NAME_INGREDIENT[i]);
		this.jtfList[i - 1] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(this.jlLists[i - 1], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoInsert.add(this.jtfList[i - 1], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_PIZZAS:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_PIZZAS.length - 1];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_PIZZAS.length - 1];
	    for (int i = 1; i < DAOFactory.COLUMNS_NAME_PIZZAS.length; i++) {
		this.jlLists[i - 1] = new JLabel(
			DAOFactory.COLUMNS_NAME_PIZZAS[i]);
		this.jtfList[i - 1] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(this.jlLists[i - 1], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoInsert.add(this.jtfList[i - 1], this.constraints);
	    }

	    JLabel jlIngredientTable = new JLabel(
		    "Select ingredients to insert:");
	    this.constraints.gridx = 0;
	    this.constraints.gridy = ++this.indexConstrainstY;
	    this.constraints.fill = GridBagConstraints.HORIZONTAL;
	    this.jpDoInsert.add(jlIngredientTable, this.constraints);
	    this.jlIngredients = new JLabel[DAOPostgreSQL.getInstance()
		    .readIngredient().size()];
	    this.idIngredients = new int[DAOPostgreSQL.getInstance()
		    .readIngredient().size()];
	    this.jsQuantity = new JSpinner[DAOPostgreSQL.getInstance()
		    .readIngredient().size()];
	    int i = 0;
	    for (Ingredient ingredient : DAOPostgreSQL.getInstance()
		    .readIngredient()) {
		this.idIngredients[i] = ingredient.getId();
		this.jlIngredients[i] = new JLabel(ingredient.getName());
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 5, 1);
		this.jsQuantity[i] = new JSpinner(sm);
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(jlIngredients[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoInsert.add(jsQuantity[i], this.constraints);
		i++;
	    }
	    break;
	case DAOFactory.TABLE_OFFERS:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_OFFERS.length - 1];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_OFFERS.length - 1];
	    for (i = 1; i < DAOFactory.COLUMNS_NAME_OFFERS.length; i++) {
		this.jlLists[i - 1] = new JLabel(
			DAOFactory.COLUMNS_NAME_OFFERS[i]);
		this.jtfList[i - 1] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(this.jlLists[i - 1], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoInsert.add(this.jtfList[i - 1], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_PREFERENCES:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_PREFERENCES.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_PREFERENCES.length];
	    for (i = 0; i < DAOFactory.COLUMNS_NAME_PREFERENCES.length; i++) {
		this.jlLists[i] = new JLabel(
			DAOFactory.COLUMNS_NAME_PREFERENCES[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoInsert.add(this.jtfList[i], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_RESOURCES:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_RESOURCES.length - 1];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_RESOURCES.length - 1];
	    for (i = 1; i < DAOFactory.COLUMNS_NAME_RESOURCES.length; i++) {
		this.jlLists[i - 1] = new JLabel(
			DAOFactory.COLUMNS_NAME_RESOURCES[i]);
		this.jtfList[i - 1] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(this.jlLists[i - 1], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoInsert.add(this.jtfList[i - 1], this.constraints);
	    }
	    break;
	default:
	    break;
	}
	this.constraints.gridy = ++this.indexConstrainstY;
    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    @Override
    public void itemStateChanged(ItemEvent e) {
	if (e.getStateChange() == ItemEvent.DESELECTED) {
	    if (this.jlLists != null) {
		for (int i = 0; i < this.jlLists.length; i++) {
		    this.jpDoInsert.remove(this.jlLists[i]);
		    this.jpDoInsert.remove(this.jtfList[i]);
		}
		this.jpDoInsert.remove(this.jbInserData);
	    }
	}
	if (e.getStateChange() == ItemEvent.SELECTED) {
	    showTextFields(e);
	    this.jpDoInsert.add(this.jbInserData, constraints);
	    this.indexConstrainstY = 0;
	}
	this.validate();
    }

    // ====================
    // GETTERS & SETTERS
    // ====================
}