/*
 *  PizzaGUISetup.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Copyright 2014 	Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  			Marc Sabate Piñol <masapim@hotmail.com>
 *  			Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  			Joaquim Dalmau Torva <jdalmaut@gmail.com>
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
package org.escoladeltreball.arcowabungaproject.ar;

import gui.GuiSetup;
import system.Setup;
import util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import de.rwth.R;

/**
 * @author victor
 * 
 */
public class PizzaGuiSetup extends GuiSetup {

    private static final String LOG_TAG = "GuiSetup";
    private static final long VIBRATION_DURATION_IN_MS = 20;
    private LinearLayout bottomView;
    private RelativeLayout main;
    private Setup mySetup;

    /**
     * @param setup
     * @param source
     */
    public PizzaGuiSetup(Setup setup, View source) {
	super(setup, source);

	mySetup = setup;
	Log.d(LOG_TAG, "GuiSetup init");
	setVibrationFeedbackEnabled(true);
	main = (RelativeLayout) source.findViewById(R.id.main_view);
	// bottomView = (LinearLayout) source.findViewById(R.id.LinLay_bottom);
	bottomView = (LinearLayout) source.findViewById(R.id.Button01);

    }

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

    // ====================
    // GETTERS & SETTERS
    // ====================
}
