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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.escoladeltreball.arcowabungaproject.model.Drink;
import org.escoladeltreball.arcowabungaproject.model.IdObject;
import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.model.Ingredients;
import org.escoladeltreball.arcowabungaproject.model.Offer;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.Product;
import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;
import org.escoladeltreball.arcowabungaproject.server.dao.DAOPostgreSQL;
import org.escoladeltreball.arcowabungaproject.server.gui.ServerGUI;

public class InsertPanel extends JPanel implements ActionListener, ItemListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    private JPanel jpDoInsert;
    private JPanel jpResult;
    private JButton jbInserData;
    private JLabel jlChooseTable;
    private GridBagConstraints constraints;
    private JLabel[] jlLists;
    private JTextField[] jtfList;
    private JLabel[] jlIngredients;
    private int[] idIngredients;
    private JSpinner[] jsQuantityIng;
    private JLabel[] jlPizzas;
    private int[] idPizzas;
    private JSpinner[] jsQuantityPizza;
    private JLabel[] jlDrinks;
    private int[] idDrinks;
    private JSpinner[] jsQuantityDrinks;
    private JLabel[] jlInfoTable;
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
	this.jpResult = new JPanel();

	this.add(this.jpDoInsert, BorderLayout.CENTER);
	this.add(this.jpResult, BorderLayout.SOUTH);
    }

    private void registListeners() {
	this.jcbTables.addItemListener(this);
	this.jbInserData.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	this.jpResult.removeAll();
	boolean textFieldsIsEmpty = true;
	int i = 1;
	while (i < jtfList.length && textFieldsIsEmpty) {
	    if (!jtfList[i].getText().isEmpty()) {
		textFieldsIsEmpty = false;
	    }
	    i++;
	}
	if (textFieldsIsEmpty) {
	    this.jpResult
		    .add(new JLabel(
			    "The insertion was not done. You have not inserted any data"));
	} else {
	    String item = (String) this.jcbTables.getSelectedItem();
	    switch (item) {
	    case DAOFactory.TABLE_INGREDIENT:
		int id = IdObject.nextId();
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
		try {
		    DAOPostgreSQL.getInstance().writeIngredients(ingredients);
		    this.jpResult
			    .add(new JLabel("The insertion has been done"));
		} catch (Exception exception) {
		    JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			    "There are a problem with the insertion \n"
				    + exception.getMessage(), "Insert error",
			    JOptionPane.ERROR_MESSAGE);

		}
		break;
	    case DAOFactory.TABLE_DRINKS:
		id = IdObject.nextId();
		name = null;
		price = null;
		icon = null;
		Float discount = null;
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
		    size = Integer.parseInt(this.jtfList[4].getText());
		}

		Drink drink = new Drink(id, name, price, icon, discount, size);
		HashSet<Drink> drinks = new HashSet<Drink>();
		drinks.add(drink);
		try {
		    DAOPostgreSQL.getInstance().writeDrinks(drinks);
		    this.jpResult
			    .add(new JLabel("The insertion has been done"));
		} catch (Exception exception) {
		    JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			    "There are a problem with the insertion \n"
				    + exception.getMessage(), "Insert error",
			    JOptionPane.ERROR_MESSAGE);

		}
		break;
	    case DAOFactory.TABLE_PIZZAS:
		id = IdObject.nextId();
		name = null;
		price = null;
		icon = null;
		discount = null;
		String massType = null;
		String type = null;
		size = null;
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
		for (i = 0; i < this.jsQuantityIng.length; i++) {
		    int quantity = (Integer) this.jsQuantityIng[i].getValue();
		    if (quantity > 0) {
			Ingredient ing = new Ingredient(this.idIngredients[i]);
			ingredientsMap.put(ing, quantity);
		    }
		}
		pizza.setIngredients(ingredientsMap);
		HashSet<Pizza> pizzas = new HashSet<Pizza>();
		pizzas.add(pizza);
		try {
		    DAOPostgreSQL.getInstance().writePizzas(pizzas);
		    this.jpResult
			    .add(new JLabel("The insertion has been done"));
		} catch (Exception exception) {
		    JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			    "There are a problem with the insertion \n"
				    + exception.getMessage(), "Insert error",
			    JOptionPane.ERROR_MESSAGE);

		}
		break;
	    case DAOFactory.TABLE_OFFERS:
		id = IdObject.nextId();
		name = null;
		price = null;
		icon = null;
		discount = null;
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
		Offer offer = new Offer(id, name, price, icon, discount);
		ArrayList<Product> productList = new ArrayList<Product>();
		for (i = 0; i < this.jsQuantityPizza.length; i++) {
		    int quantity = (Integer) this.jsQuantityPizza[i].getValue();
		    if (quantity > 0) {
			Pizza p = new Pizza(this.idPizzas[i]);
			productList.add(p);
		    }
		}
		for (i = 0; i < this.jsQuantityDrinks.length; i++) {
		    int quantity = (Integer) this.jsQuantityDrinks[i]
			    .getValue();
		    if (quantity > 0) {
			Drink d = new Drink(this.idDrinks[i]);
			productList.add(d);
		    }
		}
		offer.setProductList(productList);
		HashSet<Offer> offers = new HashSet<Offer>();
		offers.add(offer);
		try {
		    DAOPostgreSQL.getInstance().writeOffers(offers);
		    this.jpResult
			    .add(new JLabel("The insertion has been done"));
		} catch (Exception exception) {
		    JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			    "There are a problem with the insertion \n"
				    + exception.getMessage(), "Insert error",
			    JOptionPane.ERROR_MESSAGE);

		}
		break;
	    case DAOFactory.TABLE_PREFERENCES:
		String key = null;
		String value = null;
		if (!this.jtfList[0].getText().isEmpty()) {
		    key = this.jtfList[0].getText();
		}
		if (!this.jtfList[1].getText().isEmpty()) {
		    value = this.jtfList[1].getText();
		}

		Map<String, String> preferences = new HashMap<String, String>();
		preferences.put(key, value);
		try {
		    DAOPostgreSQL.getInstance().writePreferences(preferences);
		    this.jpResult
			    .add(new JLabel("The insertion has been done"));
		} catch (Exception exception) {
		    JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			    "There are a problem with the insertion \n"
				    + exception.getMessage(), "Insert error",
			    JOptionPane.ERROR_MESSAGE);

		}
		break;
	    case DAOFactory.TABLE_RESOURCES:
		id = IdObject.nextId();
		value = null;
		if (!this.jtfList[0].getText().isEmpty()) {
		    value = this.jtfList[0].getText();
		}
		Map<Integer, String> resources = new HashMap<Integer, String>();
		resources.put(id, value);
		try {
		    DAOPostgreSQL.getInstance().writeResources(resources);
		    this.jpResult
			    .add(new JLabel("The insertion has been done"));
		} catch (Exception exception) {
		    JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			    "There are a problem with the insertion \n"
				    + exception.getMessage(), "Insert error",
			    JOptionPane.ERROR_MESSAGE);
		}
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

	    this.jlInfoTable = new JLabel[1];
	    this.jlInfoTable[0] = new JLabel("Select ingredients to insert:");
	    this.jlInfoTable[0].setHorizontalTextPosition(JLabel.CENTER);
	    this.indexConstrainstY = 0;
	    this.constraints.gridx = 3;
	    this.constraints.gridy = this.indexConstrainstY;
	    this.jpDoInsert.add(this.jlInfoTable[0], this.constraints);
	    this.jlIngredients = new JLabel[DAOPostgreSQL.getInstance()
		    .readIngredient().size()];
	    this.idIngredients = new int[DAOPostgreSQL.getInstance()
		    .readIngredient().size()];
	    this.jsQuantityIng = new JSpinner[DAOPostgreSQL.getInstance()
		    .readIngredient().size()];
	    int i = 0;
	    for (Ingredient ingredient : DAOPostgreSQL.getInstance()
		    .readIngredient()) {
		this.idIngredients[i] = ingredient.getId();
		this.jlIngredients[i] = new JLabel(ingredient.getName());
		this.jlIngredients[i].setHorizontalTextPosition(JLabel.CENTER);
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 5, 1);
		this.jsQuantityIng[i] = new JSpinner(sm);
		this.constraints.gridx = 2;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(jlIngredients[i], this.constraints);
		this.constraints.gridx = 3;
		this.jpDoInsert.add(jsQuantityIng[i], this.constraints);
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
	    this.jlInfoTable = new JLabel[2];
	    this.jlInfoTable[0] = new JLabel(
		    "Select pizza to insert in the offer:");
	    this.indexConstrainstY = 0;
	    this.constraints.gridx = 3;
	    this.constraints.gridy = this.indexConstrainstY;
	    this.constraints.fill = GridBagConstraints.HORIZONTAL;
	    this.jpDoInsert.add(this.jlInfoTable[0], this.constraints);
	    this.jlPizzas = new JLabel[DAOPostgreSQL.getInstance().readPizza()
		    .size()];
	    this.idPizzas = new int[DAOPostgreSQL.getInstance().readPizza()
		    .size()];
	    this.jsQuantityPizza = new JSpinner[DAOPostgreSQL.getInstance()
		    .readPizza().size()];
	    i = 0;
	    for (Pizza pizza : DAOPostgreSQL.getInstance().readPizza()) {
		this.idPizzas[i] = pizza.getId();
		this.jlPizzas[i] = new JLabel(pizza.getName());
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 5, 1);
		this.jsQuantityPizza[i] = new JSpinner(sm);
		this.constraints.gridx = 2;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(jlPizzas[i], this.constraints);
		this.constraints.gridx = 3;
		this.jpDoInsert.add(jsQuantityPizza[i], this.constraints);
		i++;
	    }
	    this.jlInfoTable[1] = new JLabel(
		    "Select drink to insert in the offer:");
	    this.indexConstrainstY = 0;
	    this.constraints.gridx = 5;
	    this.constraints.gridy = this.indexConstrainstY;
	    this.constraints.fill = GridBagConstraints.HORIZONTAL;
	    this.jpDoInsert.add(this.jlInfoTable[1], this.constraints);
	    this.jlDrinks = new JLabel[DAOPostgreSQL.getInstance().readDrink()
		    .size()];
	    this.idDrinks = new int[DAOPostgreSQL.getInstance().readDrink()
		    .size()];
	    this.jsQuantityDrinks = new JSpinner[DAOPostgreSQL.getInstance()
		    .readDrink().size()];
	    i = 0;
	    for (Drink drink : DAOPostgreSQL.getInstance().readDrink()) {
		this.idDrinks[i] = drink.getId();
		this.jlDrinks[i] = new JLabel(drink.getName());
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 5, 1);
		this.jsQuantityDrinks[i] = new JSpinner(sm);
		this.constraints.gridx = 4;
		this.constraints.gridy = ++this.indexConstrainstY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoInsert.add(jlDrinks[i], this.constraints);
		this.constraints.gridx = 5;
		this.jpDoInsert.add(jsQuantityDrinks[i], this.constraints);
		i++;
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
	    // remove all components from jpDoInsert excepts the JComboBox when
	    // the item change
	    if (this.jlLists != null) {
		for (int i = 0; i < this.jlLists.length; i++) {
		    this.jpDoInsert.remove(this.jlLists[i]);
		    this.jpDoInsert.remove(this.jtfList[i]);
		}
		this.jpDoInsert.remove(this.jbInserData);
	    }
	    if (this.jlDrinks != null) {
		for (int i = 0; i < this.jlDrinks.length; i++) {
		    this.jpDoInsert.remove(this.jlDrinks[i]);
		    this.jpDoInsert.remove(this.jsQuantityDrinks[i]);
		}
	    }
	    if (this.jlIngredients != null) {
		for (int i = 0; i < this.jlIngredients.length; i++) {
		    this.jpDoInsert.remove(this.jlIngredients[i]);
		    this.jpDoInsert.remove(this.jsQuantityIng[i]);
		}
	    }
	    if (this.jlPizzas != null) {
		for (int i = 0; i < this.jlPizzas.length; i++) {
		    this.jpDoInsert.remove(this.jlPizzas[i]);
		    this.jpDoInsert.remove(this.jsQuantityPizza[i]);
		}
	    }
	    if (this.jlInfoTable != null) {
		for (int i = 0; i < this.jlInfoTable.length; i++) {
		    this.jpDoInsert.remove(this.jlInfoTable[i]);
		}
	    }
	}
	if (e.getStateChange() == ItemEvent.SELECTED) {
	    // Show specific data
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
