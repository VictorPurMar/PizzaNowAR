/*
 *  MakeYourOwnActivity.java
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
package org.escoladeltreball.arcowabungaproject.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.escoladeltreball.arcowabungaproject.R;
import org.escoladeltreball.arcowabungaproject.adapters.IngredientSetAdapter;
import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.ShoppingCart;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;
import org.escoladeltreball.arcowabungaproject.utils.CustomTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MakeYourOwnActivity extends Activity implements OnClickListener {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

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
	this.setContentView(R.layout.activity_makeyourpizza);

	ListView lv = (ListView) findViewById(R.id.listView);
	Set<Ingredient> ingredients = Pizzeria.getInstance().getIngredients();
	IngredientSetAdapter ingredientAdapter = new IngredientSetAdapter(this,
		ingredients);
	lv.setAdapter(ingredientAdapter);

	// Apply custom textview
	TextView tv = (TextView) findViewById(R.id.lastOrdersIntroText);
	CustomTextView.customTextView(this, tv);
	tv = (TextView) findViewById(R.id.textSelectIngredients);
	CustomTextView.customTextView(this, tv);
	tv = (TextView) findViewById(R.id.textTypePizza);
	CustomTextView.customTextView(this, tv);
	tv = (TextView) findViewById(R.id.button_menu_text);
	CustomTextView.customTextView(this, tv);
	tv = (TextView) findViewById(R.id.button_cart_text);
	CustomTextView.customTextView(this, tv);
	tv = (TextView) findViewById(R.id.textCustomPizza);
	CustomTextView.customTextView(this, tv);

	RadioButton rb_dough = (RadioButton) findViewById(R.id.radioDoughThin);
	CustomTextView.customTextView(this, rb_dough);
	rb_dough = (RadioButton) findViewById(R.id.radioDoughNormal);
	CustomTextView.customTextView(this, rb_dough);
	rb_dough = (RadioButton) findViewById(R.id.radioDoughThick);
	CustomTextView.customTextView(this, rb_dough);

	RadioButton rb_size = (RadioButton) findViewById(R.id.radioSizeSmall);
	CustomTextView.customTextView(this, rb_size);
	rb_size = (RadioButton) findViewById(R.id.radioSizeNormal);
	CustomTextView.customTextView(this, rb_size);
	rb_size = (RadioButton) findViewById(R.id.radioSizeBig);
	CustomTextView.customTextView(this, rb_size);

	// Add listeners
	LinearLayout ly = (LinearLayout) findViewById(R.id.bottom_menu);
	ly.setOnClickListener(this);
	ly = (LinearLayout) findViewById(R.id.button_cart);
	ly.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
	if (v.getId() == R.id.button_menu) {
	    finish();
	} else if (v.getId() == R.id.button_cart) {
	    EditText et = (EditText) findViewById(R.id.nameCustomPizza);
	    if (et.getText().toString().length() != 0) {
		ListView lv = (ListView) findViewById(R.id.listView);
		ShoppingCart sc = Pizzeria.getInstance().getShoppingCart();
		Pizzeria p = Pizzeria.getInstance();

		// Pizza Dough from radio button
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
		int rbId = rg.getCheckedRadioButtonId();

		String pizzaDough = null;
		if (rbId == R.id.radioDoughThin) {
		    pizzaDough = Pizza.MASSTYPE_THIN;
		} else if (rbId == R.id.radioDoughNormal) {
		    pizzaDough = Pizza.MASSTYPE_NORMAL;
		} else if (rbId == R.id.radioDoughThick) {
		    pizzaDough = Pizza.MASSTYPE_THICK;
		}

		// Pizza Size from radio button
		RadioGroup rg2 = (RadioGroup) findViewById(R.id.radioGroup);
		int rbId2 = rg.getCheckedRadioButtonId();

		int pizzaSize = Pizza.SIZE_MEDIUM;
		if (rbId2 == R.id.radioSizeSmall) {
		    pizzaSize = Pizza.SIZE_SMALL;
		} else if (rbId2 == R.id.radioSizeNormal) {
		    pizzaSize = Pizza.SIZE_MEDIUM;
		} else if (rbId2 == R.id.radioSizeBig) {
		    pizzaSize = Pizza.SIZE_LARGE;
		}

		TextView tvNamePizza = (TextView) findViewById(R.id.nameCustomPizza);
		int nextId = sc.getNextCustomId();

		// Make the pizza
		Pizza pizza = new Pizza(nextId, tvNamePizza.getText()
			.toString(), 0, 150, 0, pizzaDough,
			Pizza.TYPE_CUSTOM_SAVED, pizzaSize);

		int childCount = lv.getChildCount();
		ArrayList<Ingredient> ingredientsForPrice = new ArrayList<Ingredient>();
		for (int i = 0; i < childCount; i++) {
		    View view = lv.getChildAt(i);
		    ToggleButton tb = (ToggleButton) view
			    .findViewById(R.id.toggle_button_ingredient_item);
		    if (tb.isChecked()) {
			String name = tb.getText().toString();
			Set<Ingredient> ingredients = Pizzeria.getInstance()
				.getIngredients();
			Iterator<Ingredient> it = ingredients.iterator();
			while (it.hasNext()) {
			    Ingredient tempIng = it.next();
			    if (tempIng.getName().equals(name)) {
				ingredientsForPrice.add(tempIng);
				pizza.addIngredient(tempIng, 1);
			    }
			}
		    }
		}

		float customPrice = 0f;
		for (int i = 0; i < ingredientsForPrice.size(); i++) {
		    customPrice += ingredientsForPrice.get(i).getPrice();
		}

		customPrice += 5;
		pizza.setPrice(customPrice);
		p.addCustomSavedPizza(pizza);
		// DAOAndroid dao = DAOAndroid.getInstance();
		// dao.setPizzeria(p);

		Intent intent = new Intent(MakeYourOwnActivity.this,
			MenuActivity.class);
		startActivity(intent);
		finish();

	    } else {
		Toast.makeText(this, "Please check your pizza's name!!!",
			Toast.LENGTH_LONG).show();
	    }
	}
    }

    @Override
    public void onPause() {
	super.onPause();
	finish();
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

    // ====================
    // GETTERS & SETTERS
    // ====================
}
