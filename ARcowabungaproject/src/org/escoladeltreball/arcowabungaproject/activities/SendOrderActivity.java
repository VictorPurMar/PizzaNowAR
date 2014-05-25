/*
 *  SendOrderActivity.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  This activity allow user to add personal information to be sent to server
 *  to complete the order information.
 *  
 *  It will start after accepted order and it sends all the Order info to server
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
import org.escoladeltreball.arcowabungaproject.asynctasks.OrderSendAsyncTask;
import org.escoladeltreball.arcowabungaproject.model.Address;
import org.escoladeltreball.arcowabungaproject.model.IdObject;
import org.escoladeltreball.arcowabungaproject.model.Order;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;
import org.escoladeltreball.arcowabungaproject.utils.CustomTextView;
import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author local
 * 
 */
public class SendOrderActivity extends Activity implements OnClickListener {

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
	this.setContentView(R.layout.activity_send_order);

	// Apply custom textviews
	TextView tv = (TextView) findViewById(R.id.contact_text_menu_bottom);
	CustomTextView.customTextView(this.getApplicationContext(), tv);
	tv = (TextView) findViewById(R.id.send_order_confirm_text);
	CustomTextView.customTextView(this.getApplicationContext(), tv);
	tv = (TextView) findViewById(R.id.send_order_last_step_title);
	CustomTextView.customTextView(this.getApplicationContext(), tv);

	// Apply listeners
	LinearLayout ly = (LinearLayout) findViewById(R.id.sendOrderConfirm);
	ly.setOnClickListener(this);
	ly = (LinearLayout) findViewById(R.id.send_order_linear_bottom);
	ly.setOnClickListener(this);
    }

    @Override
    public void onPause() {
	super.onPause();
	finish();
    }

    @Override
    public void onClick(View v) {
	if (v.getId() == R.id.send_order_linear_bottom) {
	    Intent returnIntent = new Intent();
	    setResult(1, returnIntent);
	    finish();
	} else {
	    Intent returnIntent = new Intent();
	    setResult(0, returnIntent);

	    EditText etPhone = (EditText) findViewById(R.id.etPhone);
	    EditText etEmail = (EditText) findViewById(R.id.etEmail);
	    EditText etStreet = (EditText) findViewById(R.id.etStreet);
	    EditText etStreetNumber = (EditText) findViewById(R.id.etStreetNumber);
	    EditText etPostalCode = (EditText) findViewById(R.id.etPostalCode);
	    EditText etFloor = (EditText) findViewById(R.id.etFloor);
	    EditText etStair = (EditText) findViewById(R.id.etStair);
	    EditText etDoor = (EditText) findViewById(R.id.etDoor);

	    String phone = etPhone.getText().toString();
	    String email = etEmail.getText().toString();
	    String street = etStreet.getText().toString();
	    String streetNumber = etStreetNumber.getText().toString();
	    String postalCode = etPostalCode.getText().toString();
	    String floor = etFloor.getText().toString();
	    String stair = etStair.getText().toString();
	    String door = etDoor.getText().toString();

	    Address address = new Address(IdObject.nextCustomId(), street,
		    streetNumber, postalCode, floor, stair, door);

	    Pizzeria pizzeria = Pizzeria.getInstance();
	    Order order = new Order(IdObject.nextCustomId(), phone, email,
		    DateTime.now(), "", address, pizzeria.getShoppingCart());
	    pizzeria.addOrderSaved(order);

	    if (SplashScreenActivity.CONNECT_TO_SERVER) {
		OrderSendAsyncTask sendTask = new OrderSendAsyncTask(this,
			order);
		sendTask.execute();
	    } else {
		Intent intent = new Intent(this, OrderSendedActivity.class);
		startActivity(intent);
	    }
	    finish();
	}
    }
    // ====================
    // GETTERS & SETTERS
    // ====================
}
