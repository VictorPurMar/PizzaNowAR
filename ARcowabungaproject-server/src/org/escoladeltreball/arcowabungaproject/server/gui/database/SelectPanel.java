/*
 *  SelectPanel.java
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;

public class SelectPanel extends JPanel implements ActionListener, ItemListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    private JPanel jpDoSelect;
    private ShowTablesPanel jpShowTable;
    private JTextField[] jtfList;
    private JLabel[] jlLists;
    private JLabel jlChooseTable;
    private JComboBox<String> jcbTables;
    private GridBagConstraints constraints;
    private JButton jbExecuteQuery;

    private JScrollPane sp;

    private int indexConstraintsX = 0;
    private int indexConstraintsY = 0;

    // ====================
    // CONSTRUCTORS
    // ====================
    public SelectPanel() {
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
	this.setLayout(new BorderLayout());
	this.jpDoSelect = new JPanel();
	this.jpDoSelect.setLayout(new GridBagLayout());

	this.jbExecuteQuery = new JButton("Execute Query!");

	this.sp = new JScrollPane();
	String[] items = { "", DAOFactory.TABLE_ADDRESS,
		DAOFactory.TABLE_DRINKS, DAOFactory.TABLE_INGREDIENT,
		DAOFactory.TABLE_OFFERS, DAOFactory.TABLE_ORDERS,
		DAOFactory.TABLE_PIZZAS, DAOFactory.TABLE_PREFERENCES,
		DAOFactory.TABLE_RESOURCES };
	this.jlChooseTable = new JLabel("Choose Table");
	this.jcbTables = new JComboBox<>(items);
	this.constraints = new GridBagConstraints();
	this.constraints.gridx = this.indexConstraintsX;
	this.constraints.gridy = this.indexConstraintsY;
	this.jpDoSelect.add(jlChooseTable, this.constraints);
	this.constraints.gridx = ++this.indexConstraintsX;
	this.jpDoSelect.add(jcbTables, this.constraints);
	this.add(this.jpDoSelect, BorderLayout.NORTH);
    }

    private void registListeners() {
	this.jcbTables.addItemListener(this);
	this.jbExecuteQuery.addActionListener(this);
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
	case DAOFactory.TABLE_ADDRESS:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_ADDRESS.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_ADDRESS.length];
	    for (int i = 0; i < DAOFactory.COLUMNS_NAME_ADDRESS.length; i++) {
		this.jlLists[i] = new JLabel(DAOFactory.COLUMNS_NAME_ADDRESS[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstraintsY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoSelect.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoSelect.add(this.jtfList[i], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_DRINKS:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_DRINKS.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_DRINKS.length];
	    for (int i = 0; i < DAOFactory.COLUMNS_NAME_DRINKS.length; i++) {
		this.jlLists[i] = new JLabel(DAOFactory.COLUMNS_NAME_DRINKS[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstraintsY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoSelect.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoSelect.add(this.jtfList[i], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_INGREDIENT:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_INGREDIENT.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_INGREDIENT.length];
	    for (int i = 0; i < DAOFactory.COLUMNS_NAME_INGREDIENT.length; i++) {
		this.jlLists[i] = new JLabel(
			DAOFactory.COLUMNS_NAME_INGREDIENT[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstraintsY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoSelect.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoSelect.add(this.jtfList[i], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_PIZZAS:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_PIZZAS.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_PIZZAS.length];
	    for (int i = 0; i < DAOFactory.COLUMNS_NAME_PIZZAS.length; i++) {
		this.jlLists[i] = new JLabel(DAOFactory.COLUMNS_NAME_PIZZAS[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstraintsY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoSelect.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoSelect.add(this.jtfList[i], this.constraints);
	    }

	    break;
	case DAOFactory.TABLE_OFFERS:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_OFFERS.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_OFFERS.length];
	    for (int i = 0; i < DAOFactory.COLUMNS_NAME_OFFERS.length; i++) {
		this.jlLists[i] = new JLabel(DAOFactory.COLUMNS_NAME_OFFERS[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstraintsY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoSelect.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoSelect.add(this.jtfList[i], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_ORDERS:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_ORDERS.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_ORDERS.length];
	    for (int i = 0; i < DAOFactory.COLUMNS_NAME_ORDERS.length; i++) {
		this.jlLists[i] = new JLabel(DAOFactory.COLUMNS_NAME_ORDERS[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstraintsY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoSelect.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoSelect.add(this.jtfList[i], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_PREFERENCES:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_PREFERENCES.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_PREFERENCES.length];
	    for (int i = 0; i < DAOFactory.COLUMNS_NAME_PREFERENCES.length; i++) {
		this.jlLists[i] = new JLabel(
			DAOFactory.COLUMNS_NAME_PREFERENCES[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstraintsY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoSelect.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoSelect.add(this.jtfList[i], this.constraints);
	    }
	    break;
	case DAOFactory.TABLE_RESOURCES:
	    this.jlLists = new JLabel[DAOFactory.COLUMNS_NAME_RESOURCES.length];
	    this.jtfList = new JTextField[DAOFactory.COLUMNS_NAME_RESOURCES.length];
	    for (int i = 0; i < DAOFactory.COLUMNS_NAME_RESOURCES.length; i++) {
		this.jlLists[i] = new JLabel(
			DAOFactory.COLUMNS_NAME_RESOURCES[i]);
		this.jtfList[i] = new JTextField();
		this.constraints.gridx = 0;
		this.constraints.gridy = ++this.indexConstraintsY;
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.jpDoSelect.add(this.jlLists[i], this.constraints);
		this.constraints.gridx = 1;
		this.jpDoSelect.add(this.jtfList[i], this.constraints);
	    }
	    break;
	default:
	    break;
	}
	this.constraints.gridy = ++this.indexConstraintsY;
    }

    // ====================
    // OVERRIDE METHODS
    // ====================
    @Override
    public void itemStateChanged(ItemEvent e) {
	if (e.getStateChange() == ItemEvent.DESELECTED) {
	    this.repaint();
	    if (this.jlLists != null) {
		for (int i = 0; i < this.jlLists.length; i++) {
		    this.jpDoSelect.remove(this.jlLists[i]);
		    this.jpDoSelect.remove(this.jtfList[i]);
		}
		this.jpDoSelect.remove(this.jbExecuteQuery);
	    }
	}
	if (e.getStateChange() == ItemEvent.SELECTED) {
	    showTextFields(e);
	    this.jpDoSelect.add(this.jbExecuteQuery, constraints);
	    this.indexConstraintsY = 0;
	}
	this.validate();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (this.jpShowTable != null) {
	    this.remove(this.jpShowTable);
	}
	this.repaint();
	if (this.jbExecuteQuery != null) {
	    if (this.jtfList != null) {
		this.jpShowTable = new ShowTablesPanel(this.jcbTables,
			this.jtfList);
		this.add(this.jpShowTable, BorderLayout.CENTER);
	    }
	}
	this.validate();
    }
    // ====================
    // GETTERS & SETTERS
    // ====================

}
