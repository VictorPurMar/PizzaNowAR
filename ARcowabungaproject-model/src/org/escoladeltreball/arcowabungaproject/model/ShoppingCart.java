/*
 *  ShoppingCart.java
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

package org.escoladeltreball.arcowabungaproject.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart extends IdObject {

    // ====================
    // CONSTANTS
    // ====================

    private static final long serialVersionUID = 1966476365496691321L;

    // ====================
    // ATTRIBUTES
    // ====================

    private List<Product> productList;

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * Class constructor.
     * 
     * @param id
     *            an integer value
     */
    public ShoppingCart(int id) {
	super(id);
	productList = new ArrayList<Product>();
    }

    /**
     * Class constructor.
     * 
     * @param id
     *            an integer value
     * @param shoppingCart
     *            a ShoppingCart object
     */
    public ShoppingCart(int id, ShoppingCart shoppingCart) {
	super(id);
	productList = new ArrayList<Product>();
	for (Product product : shoppingCart.getProducts()) {
	    if (product instanceof Pizza) {
		Pizza pizza = (Pizza) product;
		if (!pizza.getType().equals(Pizza.TYPE_PREDEFINED)) {
		    product = new Pizza(IdObject.nextCustomId(), pizza);
		}
	    }
	    productList.add(product);
	}
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    /**
     * Add a product to the actual product list.
     * 
     * @param product
     *            a Product object
     * @return true if it has been added to the list, false if it is not
     */
    public boolean addProduct(final Product product) {
	if (product != null) {
	    if (productList == null) {
		productList = new ArrayList<Product>();
	    }
	    return productList.add(product);
	}
	return false;
    }

    /**
     * Remove a product from the actual product list
     * 
     * @param product
     *            a Product object
     * @return true if it has been deleted from the list, false if it is not
     */
    public boolean removeProduct(final Product product) {
	if (product != null) {
	    Iterator<Product> it = iteratorProducts();
	    while (it.hasNext()) {
		Product p = (Product) it.next();
		if (p.equals(product)) {
		    productList.remove(product);
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * Get the price from actual list without tax.
     * 
     * @return float the actual price value from the list
     */
    public float getPrice() {
	float price = 0.0f;
	for (Product product : productList) {
	    price += product.getPrice();
	}
	return price;
    }

    /**
     * Get the price from actual list without tax.
     * 
     * @return float the actual price value from the list with tax
     */
    public float getPriceWithTax() {
	float price = 0.0f;
	for (Product product : productList) {
	    price += product.getPrice();
	}
	return price * (Product.TAX_PERCENT + 1);
    }

    /**
     * Get the actual price from the list formated without tax.
     * 
     * @return String price formated without tax
     */
    public String getFormatedPrice() {
	return String.format("%.2f€", getPrice());
    }

    /**
     * Get the actual price from the list formated wit tax.
     * 
     * @return String price formated with tax
     */
    public String getFormatedPriceWithTax() {
	return String.format("%.2f€", getPriceWithTax());
    }

    /**
     * Get the actual list of products.
     * 
     * @return List<Product> a list of products
     */
    public List<Product> getProducts() {
	return productList;
    }

    /**
     * Set the list of product.
     * 
     * @param productsList
     *            a List of Product objects
     */
    public void setProducts(List<Product> productsList) {
	productList = productsList;
    }

    /**
     * Check if in the actual list exists a product object.
     * 
     * @param product
     *            a Product object
     * @return true if it exists
     */
    public boolean hasInProducts(Product product) {
	return productList.contains(product);
    }

    public Iterator<Product> iteratorProducts() {
	return productList.iterator();
    }

    /**
     * Return the size of the actual list.
     * 
     * @return an integer value
     */
    public int sizeProducts() {
	return productList.size();
    }

    /**
     * Remove all the actual list content.
     * 
     * @return true if it has been deleted, false if it is not
     */
    public boolean removeAll() {
	if (productList != null) {
	    productList.clear();
	    return true;
	}
	return false;
    }

    /**
     * Remove the relation with the actual list.
     */
    public void removeYou() {
	productList = null;
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

    @Override
    public String toString() {
	return "ShoppingCart [Id=" + id + "productList=" + productList + "]";
    }

    public void print() {
	System.out.println(toString());
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

}
