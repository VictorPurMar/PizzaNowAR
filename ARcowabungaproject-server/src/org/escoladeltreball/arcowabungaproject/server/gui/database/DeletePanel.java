/*
 *  DeletePanel.java
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
import org.escoladeltreball.arcowabungaproject.server.dao.DAOPostgreSQL;

public class DeletePanel extends JPanel implements ActionListener, ItemListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    private JPanel jpDoDelete;
    private JTextField jtfList;
    private JLabel jlLists;
    private JLabel jlChooseTable;
    private JComboBox<String> jcbTables;
    private GridBagConstraints constraints;
    private JButton jbDeleteQuery;
    private JPanel jpResult;
    private JScrollPane sp;

    private int indexConstraintsX = 0;
    private int indexConstraintsY = 0;

    // ====================
    // CONSTRUCTORS
    // ====================
    public DeletePanel() {
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
	this.jpDoDelete = new JPanel();
	this.jpDoDelete.setLayout(new GridBagLayout());

	this.jbDeleteQuery = new JButton("Delete!");

	this.sp = new JScrollPane();
	String[] items = { "", DAOFactory.TABLE_DRINKS,
		DAOFactory.TABLE_INGREDIENT, DAOFactory.TABLE_OFFERS,
		DAOFactory.TABLE_PIZZAS, DAOFactory.TABLE_PREFERENCES,
		DAOFactory.TABLE_RESOURCES };
	this.jlChooseTable = new JLabel("Choose Table");
	this.jcbTables = new JComboBox<>(items);
	this.constraints = new GridBagConstraints();
	this.constraints.gridx = this.indexConstraintsX;
	this.constraints.gridy = this.indexConstraintsY;
	this.jpDoDelete.add(jlChooseTable, this.constraints);
	this.constraints.gridx = ++this.indexConstraintsX;
	this.jpDoDelete.add(jcbTables, this.constraints);
	this.add(this.jpDoDelete, BorderLayout.NORTH);
    }

    private void registListeners() {
	this.jcbTables.addItemListener(this);
	this.jbDeleteQuery.addActionListener(this);
    }

    // ====================
    // OVERRIDE METHODS
    // ====================
    /**
     * Show the text fields of the tables depends on table selected in JComboBox
     * 
     * @param e
     *            the item event
     */
    private void showTextFields(ItemEvent e) {
	String item = (String) e.getItem();
	this.jtfList = new JTextField();
	switch (item) {
	case DAOFactory.TABLE_INGREDIENT:
	    this.jlLists = new JLabel(DAOFactory.COLUMNS_NAME_INGREDIENT[0]);
	    break;
	case DAOFactory.TABLE_DRINKS:
	    this.jlLists = new JLabel(DAOFactory.COLUMNS_NAME_DRINKS[0]);
	    break;
	case DAOFactory.TABLE_OFFERS:
	    this.jlLists = new JLabel(DAOFactory.COLUMNS_NAME_OFFERS[0]);
	    break;
	case DAOFactory.TABLE_PIZZAS:
	    this.jlLists = new JLabel(DAOFactory.COLUMNS_NAME_PIZZAS[0]);
	    break;
	case DAOFactory.TABLE_PREFERENCES:
	    this.jlLists = new JLabel(DAOFactory.COLUMNS_NAME_PREFERENCES[0]);
	    break;
	case DAOFactory.TABLE_RESOURCES:
	    this.jlLists = new JLabel(DAOFactory.COLUMNS_NAME_RESOURCES[0]);
	    break;
	default:
	    break;
	}
	this.constraints.gridx = 0;
	this.constraints.gridy = ++this.indexConstraintsY;
	this.constraints.fill = GridBagConstraints.HORIZONTAL;
	this.jpDoDelete.add(this.jlLists, this.constraints);
	this.constraints.gridx = 1;
	this.jpDoDelete.add(this.jtfList, this.constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (this.jtfList.getText().isEmpty()) {
	    this.jpResult
		    .add(new JLabel(
			    "The deletion was not done. You have not inserted any data"));
	} else {
	    String item = (String) this.jcbTables.getSelectedItem();
	    switch (item) {
	    case DAOFactory.TABLE_INGREDIENT:
		DAOPostgreSQL.getInstance().deleteIngredientById(
			Integer.parseInt(this.jtfList.getText()));
		break;
	    case DAOFactory.TABLE_DRINKS:
		DAOPostgreSQL.getInstance().deleteDrinkById(
			Integer.parseInt(this.jtfList.getText()));
		break;
	    case DAOFactory.TABLE_OFFERS:
		DAOPostgreSQL.getInstance().deleteOfferById(
			Integer.parseInt(this.jtfList.getText()));
		break;
	    case DAOFactory.TABLE_PIZZAS:
		DAOPostgreSQL.getInstance().deletePizzaById(
			Integer.parseInt(this.jtfList.getText()));
		break;
	    case DAOFactory.TABLE_PREFERENCES:
		DAOPostgreSQL.getInstance().deletePreferencesById(
			Integer.parseInt(this.jtfList.getText()));
		break;
	    case DAOFactory.TABLE_RESOURCES:
		DAOPostgreSQL.getInstance().deleteResourcesById(
			Integer.parseInt(this.jtfList.getText()));
		break;
	    default:
		break;
	    }
	}
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
	if (e.getStateChange() == ItemEvent.DESELECTED) {
	    this.repaint();
	    if (this.jlLists != null) {
		this.jpDoDelete.remove(this.jlLists);
		this.jpDoDelete.remove(this.jtfList);
	    }
	}

	if (e.getStateChange() == ItemEvent.SELECTED) {
	    showTextFields(e);
	    this.jpDoDelete.add(this.jbDeleteQuery, constraints);
	    this.indexConstraintsY = 0;
	}
	this.validate();

    }

    // ====================
    // GETTERS & SETTERS
    // ====================
}
