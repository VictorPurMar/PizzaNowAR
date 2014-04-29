package org.escoladeltreball.layouttest;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends Activity{

    // Set the duration of the splash screen
    //La duracion se ha de corresponder con el tiempo necesario de carga
    //(Hasta que no acabe de cargar no comnzara la Main Activity)
    private static final long SPLASH_SCREEN_DELAY = 6000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notification bar
     	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
     		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
 
        TimerTask task = new TimerTask() {
           
            @Override
            public void run() {
 
                // Start the next activity
//        	Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
                Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
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
