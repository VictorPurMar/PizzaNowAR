/*
 *  ShoppingCartAdapter.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *
 *  ShoppingCartAdapter extends BaseAdapter to display a list of the
 *  content from the SoppingCartClass. Shows to user, by a list, all the
 *  elements (Pizzas, Drinks and Offers) added to the actual shoppping cart.
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

package org.escoladeltreball.arcowabungaproject.adapters;

import java.util.ArrayList;
import java.util.List;

import org.escoladeltreball.arcowabungaproject.R;
import org.escoladeltreball.arcowabungaproject.activities.MenuActivity;
import org.escoladeltreball.arcowabungaproject.activities.SendOrderActivity;
import org.escoladeltreball.arcowabungaproject.dao.DAOAndroid;
import org.escoladeltreball.arcowabungaproject.model.Product;
import org.escoladeltreball.arcowabungaproject.model.ShoppingCart;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;
import org.escoladeltreball.arcowabungaproject.utils.CustomTextView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShoppingCartAdapter extends BaseAdapter implements OnClickListener {

    // ====================
    // CONSTANTS
    // ====================

    /**
     * List of Products (Pizzas, Drinks and Offers contained in the
     * ShoppingCart)
     */
    private List<Product> products = new ArrayList<Product>();
    public LayoutInflater inflater;
    /**
     * The activity that displays the list
     */
    public Activity activity;
    /**
     * Will contain the custom shopping cart
     */
    private ShoppingCart shoppingCart;

    // ====================
    // ATTRIBUTES
    // ====================

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * Class constructor.
     * 
     * @param activity
     *            an Android activity.
     * @param customShoppingCart
     *            a ShoppingCart
     */
    public ShoppingCartAdapter(Activity activity,
	    ShoppingCart customShoppingCart) {
	super();
	this.activity = activity;
	this.products = customShoppingCart.getProducts();
	this.shoppingCart = customShoppingCart;
	inflater = activity.getLayoutInflater();
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

    // ====================
    // OVERRIDE METHODS
    // ====================

    /**
     * Number of different element type of the list
     */
    @Override
    public int getViewTypeCount() {
	return 3;
    }

    /**
     * Get the different list element type (1 ,2 or 3)
     */
    @Override
    public int getItemViewType(int position) {
	if (position == 0) {
	    return 1;
	} else if (position == products.size() + 1) {
	    return 2;
	}
	return 0;
    }

    @Override
    public int getCount() {

	return (products.size() + 2);
    }

    @Override
    public Object getItem(int position) {
	if (position == 0 || position == products.size() + 1) {
	    return null;
	}
	return products.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	ProductViewHolder holder = null;

	if (position == 0) {
	    convertView = inflater.inflate(
		    R.layout.listitem_product_intro_layout, null);
	    TextView tv = (TextView) convertView
		    .findViewById(R.id.productIntroText);
	    CustomTextView.customTextView(activity, tv);
	} else if (position == products.size() + 1) {

	    // Fills the proper text prices in listitem_product_final_layout
	    convertView = inflater.inflate(
		    R.layout.listitem_product_final_layout, null);

	    // Find the TextViews by id and set prices
	    TextView subtotalPrice = (TextView) convertView
		    .findViewById(R.id.order_subtotal_price);
	    TextView finalPrice = (TextView) convertView
		    .findViewById(R.id.order_price_total);
	    TextView tax = (TextView) convertView
		    .findViewById(R.id.order_withtax_price);
	    Float taxPrice = Product.TAX_PERCENT * this.shoppingCart.getPrice();
	    tax.setText(String.format("%.2f€", taxPrice));

	    subtotalPrice.setText(this.shoppingCart.getFormatedPrice());
	    finalPrice.setText(this.shoppingCart.getFormatedPriceWithTax());

	    // Text final list
	    CustomTextView.customTextView(activity, finalPrice);
	    CustomTextView.customTextView(activity, tax);
	    TextView tv = (TextView) convertView
		    .findViewById(R.id.order_shipping_cost);
	    CustomTextView.customTextView(activity, tv);
	    tv = (TextView) convertView
		    .findViewById(R.id.order_shipping_cost_text);
	    CustomTextView.customTextView(activity, tv);
	    CustomTextView.customTextView(activity, subtotalPrice);
	    tv = (TextView) convertView.findViewById(R.id.order_taxes_text);
	    CustomTextView.customTextView(activity, tv);
	    tv = (TextView) convertView.findViewById(R.id.order_text_subtotal);
	    CustomTextView.customTextView(activity, tv);
	    tv = (TextView) convertView.findViewById(R.id.order_text_total);
	    CustomTextView.customTextView(activity, tv);
	    tv = (TextView) convertView.findViewById(R.id.order_withtax_price);
	    CustomTextView.customTextView(activity, tv);
	    tv = (TextView) convertView
		    .findViewById(R.id.shipping_leftlinear_text);
	    CustomTextView.customTextView(activity, tv);
	    tv = (TextView) convertView
		    .findViewById(R.id.shipping_rightlinear_text);
	    CustomTextView.customTextView(activity, tv);
	    // Set listener
	    LinearLayout ly = (LinearLayout) convertView
		    .findViewById(R.id.shipping_leftlinear_button);
	    ly.setOnClickListener(this);
	    ly = (LinearLayout) convertView
		    .findViewById(R.id.shipping_rightlinear_button);
	    ly.setOnClickListener(this);
	} else {
	    if (convertView == null) {
		convertView = this.inflater.inflate(
			R.layout.listitem_product_layout, parent, false);

		holder = new ProductViewHolder();

		holder.productImage = (ImageView) convertView
			.findViewById(R.id.imageInProductItem);
		holder.productTitle = (TextView) convertView
			.findViewById(R.id.titleTextInProductItem);
		holder.trashIcon = (ImageButton) convertView
			.findViewById(R.id.trashIcon);
		holder.productPrice = (TextView) convertView
			.findViewById(R.id.priceTextInProductItem);
		// Is necesary to develope this element to inflate the content
		// of this LinearLayout Maybe it has another Adapter?
		holder.extraIngrentsLayout = (LinearLayout) convertView
			.findViewById(R.id.extraIngredientLayoutInProductItem);

		// Apply custom textview
		CustomTextView.customTextView(activity, holder.productTitle);
		CustomTextView.customTextView(activity, holder.productPrice);
		TextView tv = (TextView) convertView
			.findViewById(R.id.extraIngredientIntro);
		CustomTextView.customTextView(activity, tv);

		// Fix problem focusable on imagebutton
		holder.trashIcon.setFocusable(false);

		// Set listener
		holder.trashIcon.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			View vp = (View) v.getParent();
			TextView tv = (TextView) vp
				.findViewById(R.id.titleTextInProductItem);
			List<Product> products = Pizzeria.getInstance()
				.getShoppingCart().getProducts();
			for (int i = 0; i < products.size(); i++) {
			    if (products.get(i).getName()
				    .equals(tv.getText().toString())) {
				shoppingCart.removeProduct(products.get(i));
				break;
			    }
			}
			notifyDataSetChanged();

			TextView textV = (TextView) activity
				.findViewById(R.id.button_cart_text);
			CustomTextView.plusPriceOrder(textV);
		    }
		});
		convertView.setTag(holder);

	    } else {
		holder = (ProductViewHolder) convertView.getTag();
	    }

	    Product product = this.products.get(position - 1);

	    DAOAndroid dao = DAOAndroid.getInstance();
	    Drawable icon = dao.getDrawableFromAssets(activity,
		    product.getIcon());
	    holder.productImage.setImageDrawable(icon);
	    holder.productTitle.setText(product.getName());
	    holder.productPrice.setText(product.getFormatedPriceWithTax());

	}

	return convertView;
    }

    /**
     * The Holder class helps to improve the efficiency of the list display by
     * saving the getElementById functionality
     * 
     * You can see an example in:
     * http://developer.android.com/training/improving
     * -layouts/smooth-scrolling.html
     */
    static class ProductViewHolder {
	/**
	 * Common product attributes
	 */
	ImageView productImage;
	TextView productTitle;
	ImageButton trashIcon;
	TextView productPrice;
	LinearLayout extraIngrentsLayout;
    }

    @Override
    public void onClick(View v) {
	// Return to menu
	if (v.getId() == R.id.shipping_leftlinear_button) {
	    Intent intent = new Intent(activity.getApplicationContext(),
		    MenuActivity.class);
	    activity.startActivity(intent);
	    activity.finish();
	    // Start for result SendOrderActivity
	} else {
	    Intent intent = new Intent(activity.getApplicationContext(),
		    SendOrderActivity.class);
	    activity.startActivityForResult(intent, 1);
	}
    }

    // ====================
    // GETTERS & SETTERS
    // ====================
}
