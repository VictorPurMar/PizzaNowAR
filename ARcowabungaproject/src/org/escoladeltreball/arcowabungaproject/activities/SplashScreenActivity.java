/*
 *  SplashScreenActivity.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  		
 *  This class try to contact with the server to check the data base version.
 *  If this data is not equal to apk database, get the last database from server,
 *  to override the apk one.
 *  
 *  After that process the activity launch the MainMenuActivity	
 *
 *  ARcowabungaproject is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ARcowabungaproject is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ARcowabungaproject.  If not, see <http://www.gnu.org/licenses/>. 
 */
package org.escoladeltreball.arcowabungaproject.activities;

import java.util.Timer;
import java.util.TimerTask;

import org.escoladeltreball.arcowabungaproject.R;
import org.escoladeltreball.arcowabungaproject.asynctasks.InitialLoadAsyncTask;
import org.escoladeltreball.arcowabungaproject.dao.DAOAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends Activity {

    // ====================
    // CONSTANTS
    // ====================

    private static final long SPLASH_SCREEN_DELAY = 1000;
    public static final boolean CONNECT_TO_SERVER = false;

    // ====================
    // ATTRIBUTES
    // ====================

    // ====================
    // CONSTRUCTORS
    // ====================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// Hide the title and remove the notification bar
	setTheWindow();

	super.onCreate(savedInstanceState);
	setContentView(R.layout.splash_screen);

	// Check the database
	checkDataBase();

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

    /**
     * This method hides the title bar of the app.
     */
    private void setTheWindow() {
	// Hide title bar
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	// Remove notification bar
	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * This method check the connection with the DB before start the app.
     */
    private void checkDataBase() {

	if (CONNECT_TO_SERVER) {
	    InitialLoadAsyncTask loadTask = new InitialLoadAsyncTask(this);
	    loadTask.execute();

	} else {

	    // Set up the system
	    DAOAndroid daoA = DAOAndroid.getInstance(this
		    .getApplicationContext());
	    daoA.loadDemo();
	    TimerTask task = new TimerTask() {
		@Override
		public void run() {
		    // Start the next activity
		    Intent mainIntent = new Intent().setClass(
			    SplashScreenActivity.this, MainMenuActivity.class);
		    startActivity(mainIntent);
		    // Close the activity so the user won't able to go back this
		    // activity pressing Back button
		    finish();
		}
	    };

	    // Simulate a long loading process on application startup.
	    Timer timer = new Timer();
	    timer.schedule(task, SPLASH_SCREEN_DELAY);
	}
    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    // ====================
    // GETTERS & SETTERS
    // ====================
}
