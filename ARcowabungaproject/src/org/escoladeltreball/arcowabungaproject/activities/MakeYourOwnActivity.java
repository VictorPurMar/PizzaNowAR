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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
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

	RadioButton rb = (RadioButton) findViewById(R.id.radioDoughFine);
	CustomTextView.customTextView(this, rb);
	rb = (RadioButton) findViewById(R.id.radioDoughNormal);
	CustomTextView.customTextView(this, rb);
	rb = (RadioButton) findViewById(R.id.radioDoughBig);
	CustomTextView.customTextView(this, rb);

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
	    ListView lv = (ListView) findViewById(R.id.listView);

	    ShoppingCart sc = Pizzeria.getInstance().getShoppingCart();
	    Pizzeria p = Pizzeria.getInstance();
	    RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
	    int rbId = rg.getCheckedRadioButtonId();
	    String pizzaValue = null;
	    if (rbId == R.id.radioDoughFine) {
		pizzaValue = Pizza.MASSTYPE_THIN;
	    } else if (rbId == R.id.radioDoughNormal) {
		pizzaValue = Pizza.MASSTYPE_NORMAL;
	    } else if (rbId == R.id.radioDoughBig) {
		pizzaValue = Pizza.MASSTYPE_THICK;
	    }
	    TextView tvNamePizza = (TextView) findViewById(R.id.nameCustomPizza);
	    int nextId = sc.getNextCustomId();
	    Pizza pizza = new Pizza(nextId, tvNamePizza.getText().toString(),
		    11, 150, 0, pizzaValue, Pizza.TYPE_CUSTOM_SAVED,
		    Pizza.SIZE_SMALL);

	    int childCount = lv.getChildCount();
	    for (int i = 0; i < childCount; i++) {
		View view = lv.getChildAt(i);
		ToggleButton tb = (ToggleButton) view
			.findViewById(R.id.toggle_button_ingredient_item);
		if (tb.isChecked()) {
		    String name = tb.getText().toString();

		    Toast.makeText(this, name, Toast.LENGTH_LONG).show();
		    Set<Ingredient> ingredients = Pizzeria.getInstance()
			    .getIngredients();
		    Iterator it = ingredients.iterator();
		    while (it.hasNext()) {
			Ingredient tempIng = (Ingredient) it.next();
			Toast.makeText(this, tempIng.getName(),
				Toast.LENGTH_LONG).show();
			if (tempIng.getName().equals(name)) {
			    pizza.addIngredient(tempIng, 1);
			}
		    }
		}
	    }
	    p.addCustomSavedPizza(pizza);

	    finish();
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
