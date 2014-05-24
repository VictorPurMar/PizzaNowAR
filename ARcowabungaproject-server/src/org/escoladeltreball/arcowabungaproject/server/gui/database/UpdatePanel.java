/*
 *  UpdatePanel.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.escoladeltreball.arcowabungaproject.model.Drink;
import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.model.Offer;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.Product;
import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;
import org.escoladeltreball.arcowabungaproject.server.dao.DAOPostgreSQL;
import org.escoladeltreball.arcowabungaproject.server.gui.ServerGUI;

public class UpdatePanel extends JPanel implements ItemListener,
	TableModelListener, ActionListener {

    // ListSelectionListener,

    // ====================
    // CONSTANTS
    // ====================

    /**
     * 
     */
    private static final long serialVersionUID = -448077471004356643L;

    // ====================
    // ATTRIBUTES
    // ====================

    private JPanel jpDoUpdate;
    private JPanel jpShowResults;
    private JButton jbUpdate;
    private JButton jbAddIngredient;
    private JButton jbAddProduct;
    private JComboBox<String> jcbTables;
    private JLabel jlChooseTable;
    private MyJTable jtTable;
    private JTable jtTableIngredients;
    private JTable jtTableProducts;
    private JScrollPane jspScrollPane;
    // private ListSelectionModel cellSelectionModel;
    private GridBagConstraints constraints;

    private HashSet<String> diferentsIngredientsIds;
    private String item;
    private String[][] rowsToUpdate;
    private String[][] rowsToUpdateIngredients;
    private String[][] rowsToUpdateProducts;

    private int indexConstraintsY = 0;

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
	this.jpShowResults.setLayout(new GridBagLayout());

	this.constraints = new GridBagConstraints();
	this.indexConstraintsY = 0;
	this.constraints.gridx = 0;
	this.constraints.gridy = this.indexConstraintsY;

	this.jspScrollPane = new JScrollPane();
	this.jspScrollPane
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	this.jspScrollPane
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
    private void showTables() {
	this.indexConstraintsY = 0;
	this.constraints.gridy = this.indexConstraintsY;

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
	    this.jspScrollPane = new JScrollPane(this.jtTable);

	    this.jpShowResults.add(this.jspScrollPane, this.constraints);

	    break;
	case DAOFactory.TABLE_DRINKS:
	    HashSet<Drink> drinks = (HashSet<Drink>) DAOPostgreSQL
		    .getInstance().readDrink();
	    rowData = new String[drinks.size()][DAOFactory.COLUMNS_NAME_DRINKS.length];
	    i = 0;
	    // Fill table with the results of query
	    for (Drink drink : drinks) {
		rowData[i][0] = drink.getId() + "";
		rowData[i][1] = drink.getName();
		rowData[i][2] = drink.getPrice() + "";
		rowData[i][3] = drink.getIcon() + "";
		rowData[i][4] = drink.getDiscount() + "";
		rowData[i][5] = drink.getSize() + "";
		i++;
	    }
	    this.jtTable = new MyJTable(rowData, DAOFactory.COLUMNS_NAME_DRINKS);
	    this.rowsToUpdate = new String[this.jtTable.getRowCount()][DAOFactory.COLUMNS_NAME_DRINKS.length];
	    this.jspScrollPane = new JScrollPane(this.jtTable);
	    this.jpShowResults.add(this.jspScrollPane, this.constraints);

	    break;
	case DAOFactory.TABLE_PIZZAS:
	    HashSet<Pizza> pizzas = (HashSet<Pizza>) DAOPostgreSQL
		    .getInstance().readPizza();
	    rowData = new String[pizzas.size()][DAOFactory.COLUMNS_NAME_PIZZAS.length];
	    i = 0;
	    int ingredientsTableSize = 0;
	    // Fill table with the results of query
	    for (Pizza pizza : pizzas) {
		rowData[i][0] = pizza.getId() + "";
		rowData[i][1] = pizza.getName();
		rowData[i][2] = pizza.getPrice() + "";
		rowData[i][3] = pizza.getIcon() + "";
		rowData[i][4] = pizza.getMassType();
		rowData[i][5] = pizza.getType();
		rowData[i][6] = pizza.getSize() + "";
		rowData[i][7] = pizza.getDiscount() + "";
		rowData[i][8] = pizza.getIngredients().getId() + "";
		ingredientsTableSize += pizza.getIngredients().size();
		i++;
	    }

	    // Show List of ingredients table associated to pizza ingredients
	    // id's
	    Object[][] rowDataIngredients = new String[ingredientsTableSize][DAOFactory.COLUMNS_NAME_INGREDIENTS.length + 1];
	    i = 0;
	    this.diferentsIngredientsIds = new HashSet<String>();
	    for (Pizza pizza : pizzas) {
		for (Map.Entry<Ingredient, Integer> entry : pizza
			.getIngredients().entrySet()) {
		    rowDataIngredients[i][0] = pizza.getIngredients().getId()
			    + "";
		    rowDataIngredients[i][1] = entry.getKey().getName() + "";
		    rowDataIngredients[i][2] = entry.getValue() + "";
		    rowDataIngredients[i][3] = "Delete";

		    // Get the differents ids to show it if user wants to add a
		    // new ingredient in to the pizza.
		    this.diferentsIngredientsIds.add(pizza.getIngredients()
			    .getId() + "");
		    i++;

		}
	    }

	    // Create table Ingredients, with button to delete ingredients

	    DefaultTableModel dm = new DefaultTableModel();
	    dm.setDataVector(rowDataIngredients, new Object[] {
		    "id_ingredients", "ingredient", "num_ingredient", "" });

	    this.jtTableIngredients = new JTable(dm);
	    // define the action of delete button in jtable
	    Action delete = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
		    JTable table = (JTable) e.getSource();
		    int modelRow = Integer.valueOf(e.getActionCommand());
		    if (Integer
			    .parseInt((String) table.getValueAt(modelRow, 2)) == 0) {
			((DefaultTableModel) table.getModel())
				.removeRow(modelRow);
		    } else {
			int quantity = Integer.parseInt((String) table
				.getValueAt(modelRow, 2)) - 1;
			table.setValueAt(quantity + "", modelRow, 2);
		    }
		}
	    };
	    // render the button
	    ButtonColumn buttonColumn = new ButtonColumn(
		    this.jtTableIngredients, delete, 3);

	    // Get the change values of ingredients table in array 2d
	    this.rowsToUpdateIngredients = new String[this.jtTableIngredients
		    .getRowCount()][DAOFactory.COLUMNS_NAME_INGREDIENTS.length + 1];

	    this.jtTable = new MyJTable(rowData, DAOFactory.COLUMNS_NAME_PIZZAS);
	    this.rowsToUpdate = new String[this.jtTable.getRowCount()][DAOFactory.COLUMNS_NAME_PIZZAS.length];

	    this.jspScrollPane = new JScrollPane(this.jtTable);

	    this.jpShowResults.add(this.jspScrollPane, this.constraints);

	    this.jspScrollPane = new JScrollPane(this.jtTableIngredients);

	    this.constraints.gridy = ++this.indexConstraintsY;
	    this.jpShowResults.add(this.jspScrollPane, this.constraints);

	    this.jtTableIngredients
		    .setPreferredScrollableViewportSize(this.jtTableIngredients
			    .getPreferredSize());

	    // Button to add ingredient
	    this.jbAddIngredient = new JButton("Add Ingredient");
	    this.jbAddIngredient.addActionListener(this);

	    this.constraints.gridy = ++this.indexConstraintsY;
	    this.jpShowResults.add(this.jbAddIngredient, this.constraints);

	    break;
	case DAOFactory.TABLE_OFFERS:
	    HashSet<Offer> offers = (HashSet<Offer>) DAOPostgreSQL
		    .getInstance().readOffer();
	    rowData = new String[offers.size()][DAOFactory.COLUMNS_NAME_OFFERS.length];
	    // Fill offers table with the results of query
	    i = 0;
	    int offersProductsTableSize = 0;
	    for (Offer offer : offers) {
		rowData[i][0] = offer.getId() + "";
		rowData[i][1] = offer.getName();
		rowData[i][2] = offer.getPrice() + "";
		rowData[i][3] = offer.getIcon() + "";
		rowData[i][4] = offer.getDiscount() + "";
		offersProductsTableSize += offer.getProductList().size();
		i++;
	    }

	    // Show List of products table associated to offer id's
	    String[][] rowDataProducts = new String[offersProductsTableSize][DAOFactory.COLUMNS_NAME_OFFERS_PRODUCTS.length + 1];
	    i = 0;
	    for (Offer offer : offers) {
		for (Product product : offer.getProductList()) {
		    rowDataProducts[i][0] = offer.getId() + "";
		    rowDataProducts[i][1] = product.getName() + "";
		    rowDataProducts[i][2] = "Delete";
		    i++;
		}
	    }
	    dm = new DefaultTableModel();
	    dm.setDataVector(rowDataProducts, new Object[] { "offer",
		    "Product", "" });

	    this.jtTableProducts = new JTable(dm);
	    // define the action of delete button in jtable
	    delete = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
		    JTable table = (JTable) e.getSource();
		    int modelRow = Integer.valueOf(e.getActionCommand());
		    ((DefaultTableModel) table.getModel()).removeRow(modelRow);
		}
	    };
	    // render the button
	    buttonColumn = new ButtonColumn(this.jtTableProducts, delete, 2);
	    this.jtTableProducts
		    .setPreferredScrollableViewportSize(this.jtTableProducts
			    .getPreferredSize());

	    this.jtTable = new MyJTable(rowData, DAOFactory.COLUMNS_NAME_OFFERS);
	    this.rowsToUpdate = new String[this.jtTable.getRowCount()][DAOFactory.COLUMNS_NAME_OFFERS.length];

	    this.jspScrollPane = new JScrollPane(this.jtTable);
	    this.jpShowResults.add(this.jspScrollPane, this.constraints);

	    this.jspScrollPane = new JScrollPane(this.jtTableProducts);

	    this.constraints.gridy = ++this.indexConstraintsY;
	    this.jpShowResults.add(this.jspScrollPane, this.constraints);

	    // Button to add Product
	    this.jbAddProduct = new JButton("Add Product");
	    this.jbAddProduct.addActionListener(this);

	    this.constraints.gridy = ++this.indexConstraintsY;
	    this.jpShowResults.add(this.jbAddProduct, this.constraints);

	    break;
	case DAOFactory.TABLE_PREFERENCES:
	    // Select preferences with concrete data
	    HashMap<String, String> preferences = (HashMap<String, String>) DAOPostgreSQL
		    .getInstance().readPreferences();
	    rowData = new String[preferences.size()][DAOFactory.COLUMNS_NAME_PREFERENCES.length];
	    i = 0;
	    // Fill table with the result of query
	    for (Map.Entry<String, String> entry : preferences.entrySet()) {
		rowData[i][0] = entry.getKey();
		rowData[i][1] = entry.getValue();
		i++;
	    }
	    this.jtTable = new MyJTable(rowData,
		    DAOFactory.COLUMNS_NAME_PREFERENCES);
	    this.rowsToUpdate = new String[this.jtTable.getRowCount()][DAOFactory.COLUMNS_NAME_PREFERENCES.length];
	    this.jspScrollPane = new JScrollPane(this.jtTable);

	    this.constraints.gridy = ++this.indexConstraintsY;
	    this.jpShowResults.add(this.jspScrollPane, this.constraints);

	    break;

	case DAOFactory.TABLE_RESOURCES:
	    // Select resources with concrete data
	    HashMap<Integer, String> resources = (HashMap<Integer, String>) DAOPostgreSQL
		    .getInstance().readResources();
	    rowData = new String[resources.size()][DAOFactory.COLUMNS_NAME_RESOURCES.length];
	    i = 0;
	    // Fill table with the result of query
	    for (Map.Entry<Integer, String> entry : resources.entrySet()) {
		rowData[i][0] = entry.getKey() + "";
		rowData[i][1] = entry.getValue();
		i++;
	    }
	    this.jtTable = new MyJTable(rowData,
		    DAOFactory.COLUMNS_NAME_RESOURCES);
	    this.rowsToUpdate = new String[this.jtTable.getRowCount()][DAOFactory.COLUMNS_NAME_RESOURCES.length];
	    this.jspScrollPane = new JScrollPane(this.jtTable);

	    this.constraints.gridy = ++this.indexConstraintsY;
	    this.jpShowResults.add(this.jspScrollPane, this.constraints);

	    break;
	default:
	    break;
	}
	this.jtTable.setPreferredScrollableViewportSize(this.jtTable
		.getPreferredSize());
	this.jtTable.getModel().addTableModelListener(this);
	this.jbUpdate = new JButton("Update");
	this.jbUpdate.addActionListener(this);
	this.constraints.gridx = 0;
	this.constraints.gridy = ++this.indexConstraintsY;
	this.jpShowResults.add(this.jbUpdate, this.constraints);
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
	    item = (String) e.getItem();
	    this.showTables();
	}
	this.validate();
    }

    @Override
    public void tableChanged(TableModelEvent e) {
	int firstRow = e.getFirstRow();
	int lastRow = e.getLastRow();
	int index = e.getColumn();

	if (e.getType() == TableModelEvent.UPDATE) {
	    if (firstRow != TableModelEvent.HEADER_ROW) {
		for (int i = firstRow; i <= lastRow; i++) {
		    if (index != TableModelEvent.ALL_COLUMNS) {
			this.rowsToUpdate[i][0] = (String) this.jtTable
				.getValueAt(i, 0);
			this.rowsToUpdate[i][index] = (String) this.jtTable
				.getValueAt(i, index);
		    }
		}
	    }
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == this.jbAddProduct) {
	    MyDialogAddProduct dial = new MyDialogAddProduct(
		    ServerGUI.getInstance(), "test", true);
	    String[] getResults = dial.results();
	    String[] results = { getResults[0], getResults[1], "Delete" };
	    if (results[0] == null || results[0].isEmpty()) {
		JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			"Id is Emtpy");
	    } else if (results[1] == null || results[1].isEmpty()) {
		JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			"Product Name is Emtpy");
	    } else {
		((DefaultTableModel) this.jtTableProducts.getModel())
			.addRow(results);
	    }
	}

	if (e.getSource() == this.jbAddIngredient) {
	    MyDialogAddIngredient dial = new MyDialogAddIngredient(
		    ServerGUI.getInstance(), "test", true,
		    this.diferentsIngredientsIds);
	    String[] results = { dial.results()[0], dial.results()[1],
		    dial.results()[2], "Delete" };
	    // Check i f new row already exists. If not exists added to the
	    // table
	    boolean isRowInTable = false;
	    for (int i = 0; i < this.jtTableIngredients.getRowCount(); i++) {
		if (results[0].equals(this.jtTableIngredients.getValueAt(i, 0))
			&& results[1].equals(this.jtTableIngredients
				.getValueAt(i, 1))) {
		    isRowInTable = true;
		}
	    }
	    if (isRowInTable) {
		JOptionPane
			.showMessageDialog(ServerGUI.getInstance(),
				"This row is already exist,\n pleas if you want change quantity edit cell");
	    } else if (results[0].isEmpty()) {
		JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			"Id is Emtpy");
	    } else if (results[1].isEmpty()) {
		JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			"Ingredient Name is Emtpy");
	    } else if (results[2].equals("0")) {
		JOptionPane.showMessageDialog(ServerGUI.getInstance(),
			"Quantity is 0");
	    } else {

		((DefaultTableModel) this.jtTableIngredients.getModel())
			.addRow(results);
	    }
	}
	if (e.getSource() == this.jbUpdate) {
	    this.jtTable.editCellAt(-1, -1);
	    this.jtTable.getSelectionModel().clearSelection();
	    String ids = "";

	    if (this.rowsToUpdate != null) {
		for (int i = 0; i < this.rowsToUpdate.length; i++) {
		    if (this.rowsToUpdate[i][0] != null) {
			ids += this.rowsToUpdate[i][0] + ", ";
		    }
		}
		String extraMessage = "";
		if (!ids.isEmpty()) {
		    ids = ids.substring(0, ids.length() - 2);
		}
		if (item.equals(DAOFactory.TABLE_PIZZAS)) {
		    extraMessage = " and Ingredients Talbe";
		}
		if (item.equals(DAOFactory.TABLE_OFFERS)) {
		    extraMessage = " and Offer Products Talbe";
		}
		// Show dialog message depends of the table selected
		int n = JOptionPane.showConfirmDialog(ServerGUI.getInstance(),
			"the following rows with id: " + ids + "\n"
				+ extraMessage + " will be updated\n"
				+ "Do you want to continue?",
			"An Inane Question", JOptionPane.YES_NO_OPTION);

		if (n == JOptionPane.YES_OPTION) {
		    for (int i = 0; i < this.rowsToUpdate.length; i++) {
			String set = "";
			switch (item) {
			case DAOFactory.TABLE_INGREDIENT:
			    for (int j = 0; j < this.rowsToUpdate[i].length; j++) {
				if (this.rowsToUpdate[i][j] != null && j != 0) {
				    if (DAOFactory.COLUMNS_TYPE_INGREDIENT[j] == "VARCHAR") {
					set += DAOFactory.COLUMNS_NAME_INGREDIENT[j]
						+ "='"
						+ this.rowsToUpdate[i][j]
						+ "', ";
				    } else {
					set += DAOFactory.COLUMNS_NAME_INGREDIENT[j]
						+ "="
						+ this.rowsToUpdate[i][j]
						+ ", ";
				    }
				}
			    }
			    if (this.rowsToUpdate[i][0] != null) {
				set = set.substring(0, set.length() - 2);
				DAOPostgreSQL.getInstance()
					.updateIngredientById(
						this.rowsToUpdate[i][0], set);
			    }
			    break;
			case DAOFactory.TABLE_DRINKS:
			    for (int j = 0; j < this.rowsToUpdate[i].length; j++) {
				if (this.rowsToUpdate[i][j] != null && j != 0) {
				    if (DAOFactory.COLUMNS_TYPE_DRINKS[j] == "VARCHAR") {
					set += DAOFactory.COLUMNS_NAME_DRINKS[j]
						+ "='"
						+ this.rowsToUpdate[i][j]
						+ "', ";
				    } else {
					set += DAOFactory.COLUMNS_NAME_DRINKS[j]
						+ "="
						+ this.rowsToUpdate[i][j]
						+ ", ";
				    }
				}
			    }
			    if (this.rowsToUpdate[i][0] != null) {
				set = set.substring(0, set.length() - 2);
				DAOPostgreSQL.getInstance().updateDrinkById(
					this.rowsToUpdate[i][0], set);
			    }
			    break;
			case DAOFactory.TABLE_PIZZAS:
			    DAOPostgreSQL.getInstance().initIngredients();
			    for (int j = 0; j < this.jtTableIngredients
				    .getRowCount(); j++) {

				// Get ingredient by it name. If it id equals id
				// of table save id in idIngredient
				int idIngredient = -1;
				for (Ingredient ingredient : DAOPostgreSQL
					.getInstance().readIngredient()) {
				    if (this.jtTableIngredients
					    .getValueAt(j, 1).equals(
						    ingredient.getName())) {
					idIngredient = ingredient.getId();
				    }
				}
				int id = Integer
					.parseInt((String) this.jtTableIngredients
						.getValueAt(j, 0));
				int quantity = Integer
					.parseInt((String) this.jtTableIngredients
						.getValueAt(j, 2));
				DAOPostgreSQL.getInstance()
					.writeIngredientsMapRow(id,
						idIngredient, quantity);
			    }
			    for (int j = 0; j < this.rowsToUpdate[i].length; j++) {
				if (this.rowsToUpdate[i][j] != null && j != 0) {
				    if (DAOFactory.COLUMNS_TYPE_PIZZAS[j] == "VARCHAR") {
					set += DAOFactory.COLUMNS_NAME_PIZZAS[j]
						+ "='"
						+ this.rowsToUpdate[i][j]
						+ "', ";
				    } else {
					set += DAOFactory.COLUMNS_NAME_PIZZAS[j]
						+ "="
						+ this.rowsToUpdate[i][j]
						+ ", ";
				    }
				}
			    }
			    if (this.rowsToUpdate[i][0] != null) {
				set = set.substring(0, set.length() - 2);
				DAOPostgreSQL.getInstance().updatePizzaById(
					this.rowsToUpdate[i][0], set);
			    }
			    break;
			case DAOFactory.TABLE_OFFERS:
			    DAOPostgreSQL.getInstance().initOffersProducts();
			    for (int j = 0; j < this.jtTableProducts
				    .getRowCount(); j++) {
				int idProduct = -1;
				// Get product by it name. If it id equals id of
				// table save id in idProduct
				for (Pizza pizza : DAOPostgreSQL.getInstance()
					.readPizza()) {
				    if (this.jtTableProducts.getValueAt(j, 1)
					    .equals(pizza.getName())) {
					idProduct = pizza.getId();
				    }
				}
				for (Drink drink : DAOPostgreSQL.getInstance()
					.readDrink()) {
				    if (this.jtTableProducts.getValueAt(j, 1)
					    .equals(drink.getName())) {
					idProduct = drink.getId();
				    }
				}

				int idOffer = Integer
					.parseInt((String) this.jtTableProducts
						.getValueAt(j, 0));
				DAOPostgreSQL.getInstance().writeOfferProducts(
					idOffer, idProduct);
			    }
			    for (int j = 0; j < this.rowsToUpdate[i].length; j++) {
				if (this.rowsToUpdate[i][j] != null && j != 0) {
				    if (DAOFactory.COLUMNS_TYPE_OFFERS[j] == "VARCHAR") {
					set += DAOFactory.COLUMNS_NAME_OFFERS_PRODUCTS[j]
						+ "='"
						+ this.rowsToUpdate[i][j]
						+ "', ";
				    } else {
					set += DAOFactory.COLUMNS_NAME_OFFERS_PRODUCTS[j]
						+ "="
						+ this.rowsToUpdate[i][j]
						+ ", ";
				    }
				}
			    }

			    if (this.rowsToUpdate[i][0] != null) {
				set = set.substring(0, set.length() - 2);
				DAOPostgreSQL.getInstance().updateOfferById(
					this.rowsToUpdate[i][0], set);
			    }
			    break;
			case DAOFactory.TABLE_PREFERENCES:
			    for (int j = 0; j < this.rowsToUpdate[i].length; j++) {
				if (this.rowsToUpdate[i][j] != null && j != 0) {
				    if (DAOFactory.COLUMNS_TYPE_PREFERENCES[j] == "VARCHAR") {
					set += DAOFactory.COLUMNS_NAME_PREFERENCES[j]
						+ "='"
						+ this.rowsToUpdate[i][j]
						+ "', ";
				    } else {
					set += DAOFactory.COLUMNS_NAME_PREFERENCES[j]
						+ "="
						+ this.rowsToUpdate[i][j]
						+ ", ";
				    }
				}
			    }
			    if (this.rowsToUpdate[i][0] != null) {
				set = set.substring(0, set.length() - 2);
				DAOPostgreSQL.getInstance()
					.updatePreferencesById(
						this.rowsToUpdate[i][0], set);
			    }
			    break;
			case DAOFactory.TABLE_RESOURCES:
			    for (int j = 0; j < this.rowsToUpdate[i].length; j++) {
				if (this.rowsToUpdate[i][j] != null && j != 0) {
				    if (DAOFactory.COLUMNS_TYPE_RESOURCES[j] == "VARCHAR") {
					set += DAOFactory.COLUMNS_NAME_RESOURCES[j]
						+ "='"
						+ this.rowsToUpdate[i][j]
						+ "', ";
				    } else {
					set += DAOFactory.COLUMNS_NAME_RESOURCES[j]
						+ "="
						+ this.rowsToUpdate[i][j]
						+ ", ";
				    }
				}
			    }
			    if (this.rowsToUpdate[i][0] != null) {
				set = set.substring(0, set.length() - 2);
				DAOPostgreSQL.getInstance()
					.updateResourcesById(
						this.rowsToUpdate[i][0], set);
			    }
			    break;
			default:
			    break;

			}
		    }
		} else if (n == JOptionPane.NO_OPTION) {
		    this.showTables();
		    this.validate();
		}
	    }
	}
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

}
