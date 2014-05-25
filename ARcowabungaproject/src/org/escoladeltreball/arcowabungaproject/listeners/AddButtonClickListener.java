/*
 *  AddButtonClickListener.java
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
package org.escoladeltreball.arcowabungaproject.listeners;

import org.escoladeltreball.arcowabungaproject.R;
import org.escoladeltreball.arcowabungaproject.model.Product;
import org.escoladeltreball.arcowabungaproject.model.ShoppingCart;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;
import org.escoladeltreball.arcowabungaproject.utils.CustomTextView;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddButtonClickListener implements
	android.view.View.OnClickListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================
    private Product product;
    private Activity activity;

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * Class constructor.
     * 
     * @param product
     *            a Product object
     * @param activity
     *            an Android activity
     */
    public AddButtonClickListener(Product product, Activity activity) {
	this.product = product;
	this.activity = activity;
    }

    // ====================
    // PUBLIC METHODS
    // ====================
    @Override
    public void onClick(View v) {
	ShoppingCart s = Pizzeria.getInstance().getShoppingCart();
	Pizzeria.getInstance().getShoppingCart().addProduct(product);
	TextView tv = (TextView) activity.findViewById(R.id.button_cart_text);
	CustomTextView.plusPriceOrder(tv);
	Toast.makeText(v.getContext(),
		"A product has been added to your cart.", Toast.LENGTH_SHORT)
		.show();
	if (s.getProducts().size() == 666) {
	    Toast.makeText(v.getContext(),
		    "Are you sure about this ? Think about it.",
		    Toast.LENGTH_LONG).show();
	}
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

    // ====================
    // GETTERS & SETTERS
    // ====================
}
