/*
 *  DAOAndroid.java
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

package org.escoladeltreball.arcowabungaproject.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.escoladeltreball.arcowabungaproject.model.Address;
import org.escoladeltreball.arcowabungaproject.model.Drink;
import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.model.Ingredients;
import org.escoladeltreball.arcowabungaproject.model.Offer;
import org.escoladeltreball.arcowabungaproject.model.Order;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.Product;
import org.escoladeltreball.arcowabungaproject.model.ShoppingCart;
import org.escoladeltreball.arcowabungaproject.model.dao.DAOFactory;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;
import org.joda.time.DateTime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

@SuppressLint("UseSparseArrays")
public class DAOAndroid extends DAOFactory {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    /**
     * SQLiteDatabase and DataBaseHelper is the principal database connection
     * from Android app.
     */
    private SQLiteDatabase database;
    private DataBaseHelper dbHepler;
    /**
     * Resources from database objects.
     */
    private Map<Integer, String> resources;
    private Map<Integer, Drawable> drawables;

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * Class constructor.
     * 
     * @param context
     *            an Android context.
     */
    protected DAOAndroid(Context context) {
	super(Pizzeria.ROLE_CLIENT);
	dbHepler = new DataBaseHelper(context);
	resources = new HashMap<Integer, String>();
	drawables = new HashMap<Integer, Drawable>();
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    /**
     * Get a drawable from assets by its path.
     * 
     * @param activity
     *            an Android activity.
     * @param path
     *            an asset's path.
     * @return a Drawable object.
     */
    public Drawable getDrawableFromAssets(Activity activity, String path) {
	Drawable drawable = null;
	try {
	    drawable = Drawable.createFromStream(activity.getAssets()
		    .open(path), null);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return drawable;
    }

    /**
     * Get a drawable from assets by its resourceId.
     * 
     * @param activity
     *            an Android activity.
     * @param resourceId
     *            an asset's path.
     * @returna Drawable object.
     */
    public Drawable getDrawableFromAssets(Activity activity, int resourceId) {
	Drawable drawable = drawables.get(resourceId);
	if (drawable == null) {
	    drawable = getDrawableFromAssets(activity,
		    getResourcePath(resourceId));
	    if (drawable != null) {
		drawables.put(resourceId, drawable);
	    }
	}
	return drawable;
    }

    /**
     * Get path from a resource's id.
     * 
     * @param resourceId
     *            an integer value.
     * @return String path.
     */
    public String getResourcePath(int resourceId) {
	return resources.get(resourceId);
    }

    /**
     * Get the instance from DAOAndroid.
     * 
     * @return DAOAndroid instance.
     */
    public static DAOAndroid getInstance() {
	return (DAOAndroid) instance;
    }

    /**
     * Get instance from a context.
     * 
     * @param context
     *            an Android context.
     * @return DAOAndroid instance.
     */
    public static DAOAndroid getInstance(Context context) {
	if (instance == null) {
	    instance = new DAOAndroid(context);
	}
	return (DAOAndroid) instance;
    }

    /**
     * Open and prepare the database for connection.
     * 
     * @throws SQLException
     */
    public void open() throws SQLException {
	database = dbHepler.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
	dbHepler.close();
    }

    // ====================
    // PROTECTED METHODS
    // ====================

    // ====================
    // PRIVATE METHODS
    // ====================

    // ====================
    // OVERRIDE METHODS
    // ====================

    /**
     * Load data.
     */
    @Override
    public boolean loadDemo() {
	resources = new HashMap<Integer, String>();
	// Activity basic images
	resources.put(150, "images/pizzita.png");
	resources.put(151, "images/pizzita.png");
	resources.put(152, "images/pizzita.png");
	resources.put(153, "images/pizzita.png");
	resources.put(154, "images/pizzita.png");

	// AR ingredient textures
	resources.put(200, "data/models/ingredients/mushroom_texture.png");
	resources.put(201, "data/models/ingredients/redpepper_texture.png");
	resources.put(202, "data/models/ingredients/greenpepper_texture.png");
	resources.put(204, "data/models/ingredients/ham_texture.png");

	// resources.put(203, "data/models/ingredients/tuna_texture.png");
	// resources.put(205, "data/models/ingredients/bacon_texture.png");
	// resources.put(206, "data/models/ingredients/bluecheese_texture.png");
	// resources.put(207, "data/models/ingredients/gouda_texture.png");
	// resources.put(208, "data/models/ingredients/gorgonzola_texture.png");
	// resources.put(209, "data/models/ingredients/emental_texture.png");
	// resources.put(210, "data/models/ingredients/parmesan_texture.png");
	// resources.put(211, "data/models/ingredients/onion_texture.png");
	// resources.put(212, "data/models/ingredients/bearsjena_texture.png");

	resources.put(213, "data/models/ingredients/corn_texture.png");
	// resources.put(214, "data/models/ingredients/chicken_texture.png");
	// resources.put(215, "data/models/ingredients/mince_texture.png");

	resources.put(216, "data/models/ingredients/egg_texture.png");
	// resources.put(217, "data/models/ingredients/marjoram_texture.png");
	// resources.put(218, "data/models/ingredients/artichoke_texture.png");

	resources.put(219, "data/models/ingredients/goatcheese_texture.png");

	// provisional ingredient textures
	resources.put(203, "data/models/ingredients/mushroom_texture.png");
	resources.put(205, "data/models/ingredients/mushroom_texture.png");
	resources.put(206, "data/models/ingredients/mushroom_texture.png");
	resources.put(207, "data/models/ingredients/mushroom_texture.png");
	resources.put(208, "data/models/ingredients/mushroom_texture.png");
	resources.put(209, "data/models/ingredients/mushroom_texture.png");
	resources.put(210, "data/models/ingredients/mushroom_texture.png");
	resources.put(211, "data/models/ingredients/mushroom_texture.png");
	resources.put(212, "data/models/ingredients/mushroom_texture.png");
	resources.put(214, "data/models/ingredients/mushroom_texture.png");
	resources.put(215, "data/models/ingredients/mushroom_texture.png");
	resources.put(217, "data/models/ingredients/mushroom_texture.png");
	resources.put(218, "data/models/ingredients/mushroom_texture.png");

	return super.loadDemo();
    }

    /**
     * Return an Ingredient object (Map) by id value.
     * 
     * @return Ingredients object.
     */
    @Override
    protected Ingredients selectIngredientsById(int id) {
	Ingredients ingredients = new Ingredients(id);
	/*
	 * Select all rows with the same id_ingredients from ingredients table
	 */
	Cursor cIngredients = database.query(DAOFactory.TABLE_INGREDIENTS,
		DAOFactory.COLUMNS_NAME_INGREDIENTS,
		DAOFactory.COLUMNS_NAME_INGREDIENTS[0] + "=" + id, null, null,
		null, null);
	int i = 0;
	while (i < cIngredients.getCount()) {
	    cIngredients.move(i);
	    /*
	     * Select all rows with the same id_ingredient from ingredient table
	     * and put in the map
	     */
	    Cursor cIngredient = database.query(
		    DAOFactory.TABLE_INGREDIENT,
		    COLUMNS_NAME_INGREDIENT,
		    DAOFactory.COLUMNS_NAME_INGREDIENT[0] + "="
			    + cIngredients.getInt(1), null, null, null, null,
		    null);
	    int j = 0;
	    while (j < cIngredient.getCount()) {
		cIngredient.move(j);
		Ingredient ingredient = new Ingredient(cIngredient.getInt(0),
			cIngredient.getString(1), cIngredient.getInt(2),
			cIngredient.getInt(3), cIngredient.getInt(4),
			cIngredient.getInt(5));
		ingredients.put(ingredient, cIngredient.getInt(2));
		j++;
	    }
	    cIngredient.close();
	    i++;
	}
	cIngredients.close();
	return ingredients;
    }

    /**
     * Return a List of Product -Offer- by id value.
     * 
     * @return List<Product> list of Product.
     */
    @Override
    protected List<Product> selectProductsOffersById(int id) {
	List<Product> productList = new ArrayList<Product>();
	// Select from offers products table the offers with the same id
	Cursor cOffersProduct = database.query(
		DAOFactory.TABLE_OFFERS_PRODUCTS,
		DAOFactory.COLUMNS_NAME_OFFERS_PRODUCTS,
		DAOFactory.COLUMNS_NAME_OFFERS_PRODUCTS[0] + "=" + id, null,
		null, null, null);
	int i = 0;
	while (i < cOffersProduct.getCount()) {
	    cOffersProduct.move(i);
	    // Product can be a pizza product or drink product
	    // Select pizza with the same id as product.
	    Cursor cPizza = database.query(
		    DAOFactory.TABLE_PIZZAS,
		    DAOFactory.COLUMNS_NAME_PIZZAS,
		    DAOFactory.COLUMNS_NAME_PIZZAS[0] + " = "
			    + cOffersProduct.getInt(1), null, null, null, null);
	    if (cPizza != null) {
		cPizza.moveToFirst();
		Pizza pizza = new Pizza(cPizza.getInt(0), cPizza.getString(1),
			cPizza.getFloat(2), cPizza.getInt(3),
			cPizza.getFloat(4), cPizza.getString(5),
			cPizza.getString(6), cPizza.getInt(7));
		Ingredients ingredients = selectIngredientsById(cPizza
			.getInt(8));
		pizza.setIngredients(ingredients);
		productList.add(pizza);
	    }
	    // Select drink with the same id as product.
	    Cursor cDrink = database.query(
		    DAOFactory.TABLE_DRINKS,
		    DAOFactory.COLUMNS_NAME_DRINKS,
		    DAOFactory.COLUMNS_NAME_DRINKS[0] + " = "
			    + cOffersProduct.getInt(1), null, null, null, null);
	    if (cDrink != null) {
		cDrink.moveToFirst();
		Drink drink = new Drink(cDrink.getInt(0), cDrink.getString(1),
			cDrink.getFloat(2), cDrink.getInt(3),
			cDrink.getFloat(4), cDrink.getInt(5));
		productList.add(drink);
	    }
	    i++;
	    if (cDrink != null) {
		cDrink.close();
	    }
	    if (cPizza != null) {
		cPizza.close();
	    }
	}
	cOffersProduct.close();
	return productList;
    }

    /**
     * Select a list of Product -ShoppingCart- by id value.
     * 
     * @return List<Product> a Product list.
     */
    @Override
    protected List<Product> selectShoppingCartProductsById(int id) {
	List<Product> productsList = new ArrayList<Product>();
	// Select all rows
	Cursor cShoppingCartsProducts = database.query(
		DAOFactory.TABLE_SHOPPINGCART_PRODUCTS,
		DAOFactory.COLUMNS_NAME_SHOPPINGCART_PRODUCTS,
		DAOFactory.COLUMNS_NAME_SHOPPINGCART_PRODUCTS[0] + "=" + id,
		null, null, null, null);
	int i = 0;
	while (i < cShoppingCartsProducts.getCount()) {
	    cShoppingCartsProducts.move(i);
	    // Product can be a pizza product, drink or offer
	    // Select pizza with the same id as product.
	    Cursor cPizza = database.query(DAOFactory.TABLE_PIZZAS,
		    DAOFactory.COLUMNS_NAME_PIZZAS,
		    DAOFactory.COLUMNS_NAME_PIZZAS[0] + " = "
			    + cShoppingCartsProducts.getInt(1), null, null,
		    null, null);
	    if (cPizza != null) {
		cPizza.moveToFirst();
		Pizza pizza = new Pizza(cPizza.getInt(0), cPizza.getString(1),
			cPizza.getFloat(2), cPizza.getInt(3),
			cPizza.getFloat(4), cPizza.getString(5),
			cPizza.getString(6), cPizza.getInt(7));
		productsList.add(pizza);
	    }
	    // Select drink with the same id as product.
	    Cursor cDrink = database.query(DAOFactory.TABLE_DRINKS,
		    DAOFactory.COLUMNS_NAME_DRINKS,
		    DAOFactory.COLUMNS_NAME_DRINKS[0] + " = "
			    + cShoppingCartsProducts.getInt(1), null, null,
		    null, null);
	    if (cDrink != null) {
		cDrink.moveToFirst();
		Drink drink = new Drink(cDrink.getInt(0), cDrink.getString(1),
			cDrink.getFloat(2), cDrink.getInt(3),
			cDrink.getFloat(4), cDrink.getInt(5));
		productsList.add(drink);

	    }
	    // Select offer with the same id as product.
	    Cursor cOffer = database.query(DAOFactory.TABLE_OFFERS,
		    DAOFactory.COLUMNS_NAME_OFFERS,
		    DAOFactory.COLUMNS_NAME_OFFERS[0] + " = "
			    + cShoppingCartsProducts.getInt(1), null, null,
		    null, null);
	    if (cOffer != null) {
		cOffer.moveToFirst();
		Offer offer = new Offer(cOffer.getInt(0), cOffer.getString(1),
			cOffer.getFloat(2), cOffer.getInt(3),
			cOffer.getFloat(4));
		List<Product> productOfferList = selectProductsOffersById(cOffer
			.getInt(0));
		offer.setProductList(productOfferList);
		productsList.add(offer);
	    }
	    if (cOffer != null) {
		cOffer.close();
	    }
	    if (cDrink != null) {
		cDrink.close();
	    }
	    if (cPizza != null) {
		cPizza.close();
	    }
	    i++;
	}
	cShoppingCartsProducts.close();
	return productsList;
    }

    /**
     * Return all the ingredients in database.
     * 
     * @return Set<Ingredient> a Set of Ingredient.
     */
    @Override
    protected Set<Ingredient> readIngredient() {
	Set<Ingredient> ingredients = new HashSet<Ingredient>();
	Cursor cIngredient = database.query(DAOFactory.TABLE_INGREDIENT,
		DAOFactory.COLUMNS_NAME_INGREDIENT, null, null, null, null,
		null);
	int i = 0;
	while (i < cIngredient.getCount()) {
	    cIngredient.move(i);
	    Ingredient ingredient = new Ingredient(cIngredient.getInt(0),
		    cIngredient.getString(1), cIngredient.getInt(2),
		    cIngredient.getInt(3), cIngredient.getInt(4),
		    cIngredient.getInt(5));
	    ingredients.add(ingredient);
	    i++;
	}
	cIngredient.close();
	return ingredients;
    }

    /**
     * Return all the pizzas in database.
     * 
     * @return Set<Pizza> a Set of Pizza.
     */
    @Override
    protected Set<Pizza> readPizza() {
	Set<Pizza> pizzas = new HashSet<Pizza>();
	/* Select all rows from pizzas table */
	Cursor cPizzas = database.query(DAOFactory.TABLE_PIZZAS,
		DAOFactory.COLUMNS_NAME_PIZZAS, null, null, null, null, null);
	int i = 0;
	while (i < cPizzas.getCount()) {
	    cPizzas.move(i);
	    Pizza pizza = new Pizza(cPizzas.getInt(0), cPizzas.getString(1),
		    cPizzas.getFloat(2), cPizzas.getInt(3),
		    cPizzas.getFloat(4), cPizzas.getString(5),
		    cPizzas.getString(6), cPizzas.getInt(7));
	    Ingredients ingredients = selectIngredientsById(cPizzas.getInt(8));
	    pizza.setIngredients(ingredients);
	    pizzas.add(pizza);
	    i++;
	}
	cPizzas.close();
	return pizzas;
    }

    /**
     * Return all the offers in database.
     * 
     * @return Set<Offer> a Set of Offer.
     */
    @Override
    protected Set<Offer> readOffer() {
	Set<Offer> offers = new HashSet<Offer>();
	Cursor cOffer = database.query(DAOFactory.TABLE_OFFERS,
		DAOFactory.COLUMNS_NAME_OFFERS, null, null, null, null, null);
	int i = 0;
	while (i < cOffer.getCount()) {
	    cOffer.move(i);
	    Offer offer = new Offer(cOffer.getInt(0), cOffer.getString(1),
		    cOffer.getFloat(2), cOffer.getInt(3), cOffer.getFloat(4));
	    List<Product> productList = selectProductsOffersById(cOffer
		    .getInt(5));
	    offer.setProductList(productList);
	    i++;
	}
	cOffer.close();
	return offers;
    }

    /**
     * Return all the drinks in database.
     * 
     * @return Set<Drink> a Set of Drink.
     */
    @Override
    protected Set<Drink> readDrink() {
	Set<Drink> drinks = new HashSet<Drink>();
	Cursor cDrinks = database.query(DAOFactory.TABLE_DRINKS,
		DAOFactory.COLUMNS_NAME_DRINKS, null, null, null, null, null);
	int i = 0;
	while (i < cDrinks.getCount()) {
	    cDrinks.move(i);
	    Drink drink = new Drink(cDrinks.getInt(0), cDrinks.getString(1),
		    cDrinks.getFloat(2), cDrinks.getInt(3),
		    cDrinks.getFloat(4), cDrinks.getInt(5));
	    drinks.add(drink);
	    i++;
	}
	cDrinks.close();
	return drinks;
    }

    /**
     * Return a shopping cart by id value.
     * 
     * @return ShoppingCart object.
     */
    @Override
    protected ShoppingCart readShoppingCart(int idShoppingCart) {
	Cursor cShoppingCarts = database
		.query(DAOFactory.TABLE_SHOPPINGCARTS,
			DAOFactory.COLUMNS_NAME_SHOPPINGCARTS,
			DAOFactory.COLUMNS_NAME_SHOPPINGCARTS[0] + "="
				+ idShoppingCart, null, null, null, null);
	ShoppingCart shoppingCart = null;

	if (cShoppingCarts != null) {
	    cShoppingCarts.moveToFirst();
	    shoppingCart = new ShoppingCart(cShoppingCarts.getInt(0));
	    List<Product> productsList = selectShoppingCartProductsById(cShoppingCarts
		    .getInt(1));
	    shoppingCart.setProducts(productsList);
	}
	cShoppingCarts.close();
	return shoppingCart;
    }

    /**
     * Return all the orders in database.
     * 
     * @return Set<Order> a Set of Order.
     */
    @Override
    protected Set<Order> readOrder() {
	Set<Order> orders = new HashSet<Order>();
	Cursor cOrder = database.query(DAOFactory.TABLE_ORDERS,
		DAOFactory.COLUMNS_NAME_ORDERS, null, null, null, null, null);
	int i = 0;
	while (i < cOrder.getCount()) {
	    cOrder.move(i);
	    DateTime dateTime = DateTime.parse(cOrder.getString(3));
	    Address address = readAddressById(cOrder.getInt(5));
	    ShoppingCart shoppingCart = readShoppingCart(cOrder.getInt(6));
	    Order order = new Order(cOrder.getInt(0), cOrder.getString(1),
		    cOrder.getString(2), dateTime, cOrder.getString(4),
		    address, shoppingCart);
	    orders.add(order);
	    i++;
	}
	cOrder.close();
	return orders;
    }

    /**
     * Return an Address object by id value.
     * 
     * @Return Address
     */
    @Override
    protected Address readAddressById(int idAddress) {
	Cursor cAddress = database.query(DAOFactory.TABLE_ADDRESS,
		DAOFactory.COLUMNS_NAME_ADDRESS, null, null, null, null, null);
	Address address = null;
	int i = 0;
	while (i < cAddress.getCount()) {
	    cAddress.move(i);
	    if (cAddress.getInt(0) == idAddress) {
		address = new Address(cAddress.getInt(0),
			cAddress.getString(1), cAddress.getString(2),
			cAddress.getString(3), cAddress.getString(4),
			cAddress.getString(5), cAddress.getString(6));
		i = cAddress.getCount();
	    }
	    i++;
	}
	cAddress.close();
	return address;
    }

    /**
     * Return all the preferences from database.
     * 
     * @return Map<String, String> a Map of Preferences.
     */
    @Override
    protected Map<String, String> readPreferences() {
	Map<String, String> preferences = new HashMap<String, String>();
	Cursor cPreferences = database.query(DAOFactory.TABLE_PREFERENCES,
		DAOFactory.COLUMNS_NAME_PREFERENCES, null, null, null, null,
		null);
	int i = 0;
	while (i < cPreferences.getCount()) {
	    cPreferences.move(i);
	    preferences.put(cPreferences.getString(0),
		    cPreferences.getString(1));
	    i++;
	}
	cPreferences.close();
	return preferences;
    }

    /**
     * Return all the resources in database.
     * 
     * @return Map<Integer, String> a Map of Resources.
     */
    @Override
    protected Map<Integer, String> readResources() {
	Map<Integer, String> resources = new HashMap<Integer, String>();
	Cursor cResources = database
		.query(DAOFactory.TABLE_RESOURCES,
			DAOFactory.COLUMNS_NAME_RESOURCES, null, null, null,
			null, null);
	int i = 0;
	while (i < cResources.getCount()) {
	    cResources.move(i);
	    resources.put(cResources.getInt(0), cResources.getString(1));
	    i++;
	}
	cResources.close();
	return resources;
    }

    /**
     * Write in database ingredients values.
     * 
     * @param ingredients
     *            a Set of Ingredient
     */
    @Override
    protected void writeIngredients(Set<Ingredient> ingredients) {

	for (Ingredient ingredient : ingredients) {
	    ContentValues values = new ContentValues();

	    values.put(DAOFactory.COLUMNS_NAME_INGREDIENT[0],
		    ingredient.getId());
	    values.put(DAOFactory.COLUMNS_NAME_INGREDIENT[1],
		    ingredient.getName());
	    values.put(DAOFactory.COLUMNS_NAME_INGREDIENT[2],
		    ingredient.getIcon());
	    values.put(DAOFactory.COLUMNS_NAME_INGREDIENT[3],
		    ingredient.getModel());
	    values.put(DAOFactory.COLUMNS_NAME_INGREDIENT[4],
		    ingredient.getPrice());
	    values.put(DAOFactory.COLUMNS_NAME_INGREDIENT[5],
		    ingredient.getTexture());

	    database.insert(DAOFactory.TABLE_INGREDIENT, null, values);
	}
    }

    /**
     * Write in database a pizzas values.
     * 
     * @param pizzas
     *            a Set of Pizza
     */
    @Override
    protected void writePizzas(Set<Pizza> pizzas) {
	for (Pizza pizza : pizzas) {
	    ContentValues values = new ContentValues();
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[0], pizza.getId());
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[1], pizza.getName());
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[2], pizza.getPrice());
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[3], pizza.getIcon());
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[4], pizza.getMassType());
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[5], pizza.getType());
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[6], pizza.getSize());
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[7], pizza.getDiscount());
	    values.put(DAOFactory.COLUMNS_NAME_PIZZAS[8], pizza
		    .getIngredients().getId());

	    database.insert(DAOFactory.TABLE_PIZZAS, null, values);
	    writeProduct(pizza.getId());
	    values.clear();
	    for (Ingredient ingredient : pizza.getIngredientsSet()) {
		values.put(DAOFactory.COLUMNS_NAME_INGREDIENTS[0], pizza
			.getIngredients().getId());
		values.put(DAOFactory.COLUMNS_NAME_INGREDIENTS[1],
			ingredient.getId());
		values.put(DAOFactory.COLUMNS_NAME_INGREDIENTS[2], pizza
			.getIngredients().get(ingredient));
		database.insert(DAOFactory.TABLE_INGREDIENTS, null, values);
	    }
	}

    }

    /**
     * Write in database a offers values.
     * 
     * @param offers
     *            a Set of Offer.
     */
    @Override
    protected void writeOffers(Set<Offer> offers) {
	for (Offer offer : offers) {
	    ContentValues values = new ContentValues();
	    values.put(DAOFactory.COLUMNS_NAME_OFFERS[0], offer.getId());
	    values.put(DAOFactory.COLUMNS_NAME_OFFERS[1], offer.getName());
	    values.put(DAOFactory.COLUMNS_NAME_OFFERS[2], offer.getPrice());
	    values.put(DAOFactory.COLUMNS_NAME_OFFERS[3], offer.getIcon());
	    values.put(DAOFactory.COLUMNS_NAME_OFFERS[4], offer.getDiscount());

	    database.insert(DAOFactory.TABLE_OFFERS, null, values);
	    writeProduct(offer.getId());
	    values.clear();
	    for (Product product : offer.getProduct()) {
		values.put(DAOFactory.COLUMNS_NAME_OFFERS_PRODUCTS[0],
			offer.getId());
		values.put(DAOFactory.COLUMNS_NAME_OFFERS_PRODUCTS[1],
			product.getId());

		database.insert(DAOFactory.TABLE_OFFERS_PRODUCTS, null, values);
	    }
	}

    }

    /**
     * Write in database a drinks values.
     * 
     * @param drinks
     *            a Set of Drink.
     */
    @Override
    protected void writeDrinks(Set<Drink> drinks) {
	for (Drink drink : drinks) {
	    ContentValues values = new ContentValues();
	    values.put(DAOFactory.COLUMNS_NAME_DRINKS[0], drink.getId());
	    values.put(DAOFactory.COLUMNS_NAME_DRINKS[1], drink.getName());
	    values.put(DAOFactory.COLUMNS_NAME_DRINKS[2], drink.getPrice());
	    values.put(DAOFactory.COLUMNS_NAME_DRINKS[3], drink.getIcon());
	    values.put(DAOFactory.COLUMNS_NAME_DRINKS[4], drink.getDiscount());
	    values.put(DAOFactory.COLUMNS_NAME_DRINKS[5], drink.getSize());

	    database.insert(DAOFactory.TABLE_DRINKS, null, values);
	    writeProduct(drink.getId());

	}
    }

    /**
     * Write in database a shopping carts values.
     * 
     * @param ShoppingCart
     *            object.
     */
    @Override
    protected void writeShoppingCarts(ShoppingCart shoppingCart) {
	ContentValues values = new ContentValues();
	values.put(DAOFactory.COLUMNS_NAME_SHOPPINGCARTS[0],
		shoppingCart.getId());
	database.insert(DAOFactory.TABLE_SHOPPINGCARTS, null, values);
	values.clear();
	for (Product product : shoppingCart.getProducts()) {
	    if (product instanceof Pizza) {
		Pizza p = (Pizza) product;
		if (p.getType().equals(Pizza.TYPE_CUSTOM_TEMPORARY)) {
		    Set<Pizza> pSet = new HashSet<Pizza>();
		    pSet.add(p);
		    writePizzas(pSet);
		}
	    }
	    values.put(DAOFactory.COLUMNS_NAME_SHOPPINGCART_PRODUCTS[0],
		    shoppingCart.getId());
	    values.put(COLUMNS_NAME_SHOPPINGCART_PRODUCTS[1], product.getId());
	    database.insert(DAOFactory.TABLE_SHOPPINGCART_PRODUCTS, null,
		    values);
	}
    }

    /**
     * Write in database an orders.
     * 
     * @param orders
     *            a Set of Order
     */
    @Override
    protected void writeOrders(Set<Order> orders) {
	for (Order order : orders) {
	    ContentValues values = new ContentValues();
	    values.put(DAOFactory.COLUMNS_NAME_ORDERS[0], order.getId());
	    values.put(DAOFactory.COLUMNS_NAME_ORDERS[1], order.getEmail());
	    values.put(DAOFactory.COLUMNS_NAME_ORDERS[2], order.getPhone());
	    SimpleDateFormat dateFormat = new SimpleDateFormat(
		    "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
	    values.put(DAOFactory.COLUMNS_NAME_ORDERS[3],
		    dateFormat.format(order.getDateTime()));
	    values.put(DAOFactory.COLUMNS_NAME_ORDERS[4],
		    order.getPaymentMethod());
	    values.put(DAOFactory.COLUMNS_NAME_ORDERS[5], order.getAddress()
		    .getId());
	    values.put(DAOFactory.COLUMNS_NAME_ORDERS[6], order
		    .getShoppingCart().getId());
	    writeShoppingCarts(order.getShoppingCart());
	    writeAddresses(order.getAddress());
	    database.insert(DAOFactory.TABLE_ORDERS, null, values);
	}

    }

    /**
     * Write in database an address.
     * 
     * @param address
     *            an Address object.
     */
    @Override
    protected void writeAddresses(Address address) {
	ContentValues values = new ContentValues();
	values.put(DAOFactory.COLUMNS_NAME_ADDRESS[0], address.getId());
	values.put(DAOFactory.COLUMNS_NAME_ADDRESS[1], address.getStreet());
	values.put(DAOFactory.COLUMNS_NAME_ADDRESS[2], address.getNumber());
	values.put(DAOFactory.COLUMNS_NAME_ADDRESS[3], address.getPostCode());
	values.put(DAOFactory.COLUMNS_NAME_ADDRESS[4], address.getFloor());
	values.put(DAOFactory.COLUMNS_NAME_ADDRESS[5], address.getStair());
	values.put(DAOFactory.COLUMNS_NAME_ADDRESS[6], address.getFloor());

	database.insert(DAOFactory.TABLE_ADDRESS, null, values);
    }

    /**
     * Write in database a preferences.
     * 
     * @param preferences
     *            a Map.
     */
    @Override
    protected void writePreferences(Map<String, String> preferences) {
	for (Map.Entry<String, String> entry : preferences.entrySet()) {
	    ContentValues values = new ContentValues();
	    values.put(DAOFactory.COLUMNS_NAME_PREFERENCES[0], entry.getKey());
	    values.put(DAOFactory.COLUMNS_NAME_PREFERENCES[1], entry.getValue());
	    database.insert(DAOFactory.TABLE_PREFERENCES, null, values);
	}
    }

    /**
     * Write in database a product.
     * 
     * @param idProduct
     *            an integer value.
     */
    @Override
    protected void writeProduct(int idProduct) {
	ContentValues values = new ContentValues();
	values.put(DAOFactory.COLUMNS_NAME_PRODUCTS[0], idProduct);
	database.insert(DAOFactory.CREATE_TABLE_PRODUCTS, null, values);

    }

    /**
     * Write in database a resources.
     * 
     * @param resources
     *            a Map
     */
    @Override
    protected void writeResources(Map<Integer, String> resources) {
	for (Map.Entry<Integer, String> entry : resources.entrySet()) {
	    ContentValues values = new ContentValues();
	    values.put(DAOFactory.COLUMNS_NAME_RESOURCES[0], entry.getKey());
	    values.put(DAOFactory.COLUMNS_NAME_RESOURCES[1], entry.getValue());
	    database.insert(DAOFactory.TABLE_RESOURCES, null, values);
	}

    }

    /**
     * Reset the database.
     */
    @Override
    protected void resetTables() {
	dbHepler.onUpgrade(database, 0, 0);
    }

    // ====================
    // GETTERS & SETTERS
    // ====================
}
