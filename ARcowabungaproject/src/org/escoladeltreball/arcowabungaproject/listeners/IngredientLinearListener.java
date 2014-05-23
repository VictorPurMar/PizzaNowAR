/*
 *  ingredientLinearListener.java
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

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ingredientLinearListener implements OnClickListener {

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
    public void onClick(View v) {
	LinearLayout ly = (LinearLayout) v;

	if (ly.isSelected()) {
	    TextView tv = (TextView) ly.findViewById(R.id.text_item_ingredient);
	    ImageView iv = (ImageView) ly
		    .findViewById(R.id.image_item_ingredient);
	    tv.setTextColor(Color.WHITE);
	} else {
	    TextView tv = (TextView) ly.findViewById(R.id.text_item_ingredient);
	    ImageView iv = (ImageView) ly
		    .findViewById(R.id.image_item_ingredient);
	    // TODO
	}
    }
    // ====================
    // GETTERS & SETTERS
    // ====================
}
