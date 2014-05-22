/*
 *  UpdatePanel.java
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;
import org.escoladeltreball.arcowabungaproject.server.dao.DAOPostgreSQL;
import org.escoladeltreball.arcowabungaproject.server.gui.ServerGUI;

public class UpdatePanel extends JPanel implements ItemListener,
	ListSelectionListener, TableModelListener, ActionListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    private JPanel jpDoUpdate;
    private JPanel jpShowResults;
    private JButton jbUpdate;
    private JComboBox<String> jcbTables;
    private JLabel jlChooseTable;
    private MyJTable jtTable;
    private JScrollPane jspScrollPane;
    private ListSelectionModel cellSelectionModel;

    private String[][] rowsToUpdate;

    // ====================
    // CONSTRUCTORS
    // ====================
    public UpdatePanel() {
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
	String[] items = { "", DAOFactory.TABLE_DRINKS,
		DAOFactory.TABLE_INGREDIENT, DAOFactory.TABLE_OFFERS,
		DAOFactory.TABLE_PIZZAS, DAOFactory.TABLE_PREFERENCES,
		DAOFactory.TABLE_RESOURCES };
	this.jlChooseTable = new JLabel("Choose Table");
	this.jcbTables = new JComboBox<>(items);
	this.jpDoUpdate = new JPanel();
	this.jpShowResults = new JPanel();

	this.jpDoUpdate.add(this.jlChooseTable);
	this.jpDoUpdate.add(this.jcbTables);
	this.add(this.jpDoUpdate, BorderLayout.NORTH);
	this.add(this.jpShowResults, BorderLayout.CENTER);
    }

    private void registListeners() {
	this.jcbTables.addItemListener(this);
    }

    /**
     * Show the tables depends on table selected in JComboBox
     * 
     * @param e
     *            the item event
     */
    private void showTables(ItemEvent e) {
	String item = (String) e.getItem();
	switch (item) {
	case DAOFactory.TABLE_INGREDIENT:
	    HashSet<Ingredient> ingredients = (HashSet<Ingredient>) DAOPostgreSQL
		    .getInstance().readIngredient();
	    String[][] rowData = new String[ingredients.size()][DAOFactory.COLUMNS_NAME_INGREDIENT.length];
	    int i = 0;
	    // Fill table with the results of query
	    for (Ingredient ingredient : ingredients) {
		rowData[i][0] = ingredient.getId() + "";
		rowData[i][1] = ingredient.getName();
		rowData[i][2] = ingredient.getPrice() + "";
		rowData[i][3] = ingredient.getModel() + "";
		rowData[i][4] = ingredient.getIcon() + "";
		rowData[i][5] = ingredient.getTexture() + "";
		i++;
	    }
	    this.jtTable = new MyJTable(rowData,
		    DAOFactory.COLUMNS_NAME_INGREDIENT);
	    this.rowsToUpdate = new String[this.jtTable.getRowCount()][DAOFactory.COLUMNS_NAME_INGREDIENT.length];
	    break;
	default:
	    break;
	}

	this.jspScrollPane = new JScrollPane(this.jtTable);
	this.jspScrollPane
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	this.jspScrollPane
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	this.jpShowResults.add(this.jspScrollPane);
	this.cellSelectionModel = this.jtTable.getSelectionModel();
	this.cellSelectionModel
		.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	this.jtTable.setPreferredScrollableViewportSize(this.jtTable
		.getPreferredSize());
	this.cellSelectionModel.addListSelectionListener(this);
	this.jtTable.getModel().addTableModelListener(this);
	this.jbUpdate = new JButton("Update");
	this.jbUpdate.addActionListener(this);
	this.jpShowResults.add(this.jbUpdate);
    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    @Override
    public void itemStateChanged(ItemEvent e) {
	if (e.getStateChange() == ItemEvent.DESELECTED) {
	    this.repaint();
	    this.jpShowResults.removeAll();
	}

	if (e.getStateChange() == ItemEvent.SELECTED) {
	    this.showTables(e);
	    // this.constraints.gridy = ++this.indexConstraintsY;
	    // this.jpDoDelete.add(this.jbDeleteQuery, constraints);
	    // this.indexConstraintsY = 0;
	}
	this.validate();
    }

    public void valueChanged(ListSelectionEvent e) {
	String selectedData = null;
	int[] selectedRow = this.jtTable.getSelectedRows();
	int[] selectedColumns = this.jtTable.getSelectedColumns();
	for (int i = 0; i < selectedRow.length; i++) {
	    for (int j = 0; j < selectedColumns.length; j++) {
		selectedData = (String) this.jtTable.getValueAt(selectedRow[i],
			selectedColumns[j]);
	    }
	}
	System.out.println("Selected: " + selectedData);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
	int firstRow = e.getFirstRow();
	int lastRow = e.getLastRow();
	int index = e.getColumn();
	switch (e.getType()) {
	case TableModelEvent.INSERT:
	    for (int i = firstRow; i <= lastRow; i++) {
		System.out.println(i);
	    }
	    break;
	case TableModelEvent.UPDATE:
	    if (firstRow == TableModelEvent.HEADER_ROW) {
		if (index == TableModelEvent.ALL_COLUMNS) {
		    System.out.println("A column was added");
		} else {
		    System.out.println(index + "in header changed");
		}
	    } else {
		for (int i = firstRow; i <= lastRow; i++) {
		    if (index == TableModelEvent.ALL_COLUMNS) {
			System.out.println("All columns have changed");
		    } else {
			this.rowsToUpdate[i][0] = (String) this.jtTable
				.getValueAt(i, 0);
			this.rowsToUpdate[i][index] = (String) this.jtTable
				.getValueAt(i, index);
			System.out.println(index + " , " + i);
			System.out.println(this.jtTable.getValueAt(i, index));
		    }
		}
	    }
	    break;
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	this.jtTable.editCellAt(-1, -1);
	this.jtTable.getSelectionModel().clearSelection();
	String ids = "";

	if (this.rowsToUpdate != null) {
	    for (int i = 0; i < this.rowsToUpdate.length; i++) {
		if (this.rowsToUpdate[i][0] != null) {
		    ids += this.rowsToUpdate[i][0] + ", ";
		    System.out.println(ids);
		}
	    }
	    ids = ids.substring(0, ids.length() - 2);
	    int n = JOptionPane.showConfirmDialog(ServerGUI.getInstance(),
		    "the following rows with id: " + ids + " will be updated\n"
			    + "Do you want to continue?", "An Inane Question",
		    JOptionPane.YES_NO_OPTION);

	    if (n == JOptionPane.YES_OPTION) {
		for (int i = 0; i < this.rowsToUpdate.length; i++) {
		    for (int j = 0; j < this.rowsToUpdate[i].length; j++) {
			if (this.rowsToUpdate[i][j] != null) {
			    System.out.println("row: " + i + " column: " + j
				    + "," + this.rowsToUpdate[i][j]);
			}
		    }

		}
	    } else if (n == JOptionPane.NO_OPTION) {
		System.out.println("No Update");
	    }
	}
    }

    // ====================
    // GETTERS & SETTERS
    // ====================
}
