/*
 *  OrderActivity.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *
 *  This activity shows all the custom shopping cart content to the user:
 *  This content is showed as a ListView with all the Pizzas, Drinks and Offers
 *  added to the Cart by the user
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

package org.escoladeltreball.arcowabungaproject.activities;

import org.escoladeltreball.arcowabungaproject.R;
import org.escoladeltreball.arcowabungaproject.adapters.ShoppingCartAdapter;
import org.escoladeltreball.arcowabungaproject.model.ShoppingCart;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;
import org.escoladeltreball.arcowabungaproject.utils.CustomTextView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OrderActivity extends Activity implements OnClickListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    static final int PICK_CONTACT_REQUEST = 1;

    // ====================
    // CONSTRUCTORS
    // ====================

    // ====================
    // PUBLIC METHODS
    // ====================

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
    protected void onCreate(Bundle savedInstanceState) {
	// Remove title bar
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	// Remove notification bar
	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);

	super.onCreate(savedInstanceState);
	// set content view AFTER ABOVE sequence (to avoid crash)
	this.setContentView(R.layout.shooping_cart_layout);

	Pizzeria p = Pizzeria.getInstance();
	ShoppingCart customShoppingCart = p.getShoppingCart();

	// Inflate the content of the ListView using SoppingCartAdapter
	ListView listView = (ListView) findViewById(R.id.product_list);
	ShoppingCartAdapter adapter = new ShoppingCartAdapter(this,
		customShoppingCart);
	listView.setAdapter(adapter);

	// SET CUSTOM TEXT

	// Bottom buttons
	TextView tv = (TextView) findViewById(R.id.button_cart_text);
	CustomTextView.customTextView(this, tv);
	tv = (TextView) findViewById(R.id.button_menu_text);
	CustomTextView.customTextView(this, tv);
	tv = (TextView) findViewById(R.id.button_cart_text);
	CustomTextView.customTextView(this, tv);
	CustomTextView.plusPriceOrder(tv);

	// Top text
	tv = (TextView) findViewById(R.id.remember_advertice_title);
	CustomTextView.customTextView(this, tv);
	tv = (TextView) findViewById(R.id.remember_text);
	CustomTextView.customTextView(this, tv, Typeface.ITALIC);

	// SET CLICK LISTENERS
	LinearLayout ly = (LinearLayout) findViewById(R.id.button_menu);
	ly.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
	if (v.getId() == R.id.button_menu) {
	    finish();
	}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (requestCode == PICK_CONTACT_REQUEST) {
	    if (resultCode == 0) {
		finish();
	    }
	}
    }
    // ====================
    // GETTERS & SETTERS
    // ====================
}
