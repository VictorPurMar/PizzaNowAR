/*
 *  Offer.java
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

public class Offer extends Product {

    private static final long serialVersionUID = -422646089915638752L;

    // ====================
    // CONSTANTS
    // ====================

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
     * @param name
     *            String
     * @param price
     *            float value
     * @param icon
     *            integer value
     * @param discount
     *            float value
     */
    public Offer(int id, String name, float price, int icon, float discount) {
	super(id, name, price, icon, discount);
	this.productList = new ArrayList<Product>();
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    /**
     * Add product to list.
     * 
     * @param product
     *            a Product object
     * @return true is is done, false if is not
     */
    public boolean addProduct(Product product) {
	return this.productList.add(product);
    }

    /**
     * Remove product of the list.
     * 
     * @param product
     *            a Product object
     * @return true if is done, false if is not
     */
    public boolean removeProduct(Product product) {
	return this.productList.remove(product);
    }

    /**
     * Get the list of product.
     * 
     * @return a List of Product
     */
    public List<Product> getProduct() {
	return this.productList;
    }

    /**
     * Check is a product is in the list.
     * 
     * @param product
     *            a Product object
     * @return true if it exists
     */
    public boolean hasInProducts(Product product) {
	return this.productList.contains(product);
    }

    /**
     * Return a iterator of the ingredient list.
     * 
     * @return an Iterator of Product list
     */
    public Iterator<Product> iteratorProducts() {
	return this.productList.iterator();
    }

    /**
     * Return the size of the product list.
     * 
     * @return an integer value, represents the size of the list
     */
    public int sizeProducts() {
	return this.productList.size();
    }

    /**
     * Remove all the data in the list.
     * 
     * @return true if it is done, false if it is not
     */
    public boolean removeAll() {
	this.productList.clear();
	if (this.productList.size() == 0) {
	    return true;
	}
	return false;
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
	return "Offer [productList=" + productList + "]";
    }

    public void print() {
	System.out.println(toString());
    }

    public void printProductList() {
	System.out.println(this.productList.toString());
    }

    // ====================
    // ====================
    // GETTERS & SETTERS

    public List<Product> getProductList() {
	return productList;
    }

    public void setProductList(List<Product> productList) {
	this.productList = productList;
    }
}
