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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;

public class DeletePanel extends JPanel {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    private JPanel jpDoDelete;
    private JTextField[] jtfList;
    private JLabel[] jlLists;
    private JLabel jlChooseTable;
    private JComboBox<String> jcbTables;
    private GridBagConstraints constraints;
    private JButton jbDeleteQuery;

    private JScrollPane sp;

    private int indexConstraintsX = 0;
    private int indexConstraintsY = 0;

    // ====================
    // CONSTRUCTORS
    // ====================
    public DeletePanel() {
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
    // ====================
    // OVERRIDE METHODS
    // ====================

    // ====================
    // GETTERS & SETTERS
    // ====================
}
