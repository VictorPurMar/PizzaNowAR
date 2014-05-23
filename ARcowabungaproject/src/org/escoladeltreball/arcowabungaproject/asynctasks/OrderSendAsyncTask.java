/*
 *  OrderSendAsyncTask.java
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
package org.escoladeltreball.arcowabungaproject.asynctasks;

import org.escoladeltreball.arcowabungaproject.activities.OrderSendedActivity;
import org.escoladeltreball.arcowabungaproject.model.Order;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

public class OrderSendAsyncTask extends AsyncTask<Void, Void, Boolean> {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    private Activity activity;
    private Order order;

    // ====================
    // CONSTRUCTORS
    // ====================

    public OrderSendAsyncTask(Activity activity, Order order) {
	super();
	this.activity = activity;
	this.order = order;
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

    @Override
    protected Boolean doInBackground(Void... params) {
	return order.send();
    }

    @Override
    protected void onPostExecute(Boolean result) {
	Intent intent = new Intent(activity, OrderSendedActivity.class);
	activity.startActivity(intent);
    }

    // ====================
    // GETTERS & SETTERS
    // ====================
}
