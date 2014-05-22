package org.escoladeltreball.arcowabungaproject.ar;

import java.io.IOException;
import java.util.Locale;

import org.escoladeltreball.arcowabungaproject.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PizzaGuiSetup implements OnClickListener {

    // ====================
    // CONSTANTS
    // ====================

    private static final String LOG_TAG = "GuiSetup";
    private static final long VIBRATION_DURATION_IN_MS = 20;
    private static final int BUTTON_COLOR = R.color.background_red_order;
    private static final int TEXT_COLOR = Color.WHITE;
    private static final String FONT_TYPE = "fonts/gnuolane.ttf";
    public RelativeLayout main;
    private PizzaMarkerRenderSetup mySetup;
    private Activity baseActivity;
    private View source;
    private boolean vibrationEnabled = true;

    // ====================
    // ATTRIBUTES
    // ====================

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * @param setup
     * @param source
     *            the xml layout converted into a view
     */
    public PizzaGuiSetup(PizzaMarkerRenderSetup setup, View source) {

	this.mySetup = setup;
	this.source = source;
	this.main = (RelativeLayout) source.findViewById(R.id.main_view);

    }

    // ====================
    // PUBLIC METHODS
    // ====================

    public void run() {

	// Add the Objects to GUI
	setTheGuiObjects(source);
    }

    // public void setVibrationFeedbackEnabled(boolean vibrate) {
    // if (vibrate && vibrateCommand == null) {
    // try {
    // Log.d(LOG_TAG,
    // "Trying to enable vibration feedback for UI actions");
    // vibrateCommand = new CommandDeviceVibrate(
    // mySetup.getActivity(), VIBRATION_DURATION_IN_MS);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // }

    // ====================
    // PROTECTED METHODS
    // ====================

    // ====================
    // PRIVATE METHODS
    // ====================

    private void setTheGuiObjects(View source) {

	// Set image Logo in the top left screen
	ImageView logo = (ImageView) source.findViewById(R.id.ar_title);
	Drawable drawable = null;
	try {
	    drawable = Drawable.createFromStream(this.main.getContext()
		    .getAssets().open("data/pizzaNow.png"), null);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	logo.setImageDrawable(drawable);

	// Set the pizza name
	TextView pizzaName = (TextView) source
		.findViewById(R.id.LLA_pizza_name);
	pizzaName.setText(PizzaModelMapper.getPizzaName().toUpperCase(
		new Locale("EN")));
	Typeface tf = Typeface.createFromAsset(this.main.getContext()
		.getAssets(), FONT_TYPE);
	pizzaName.setTypeface(tf);
	pizzaName.setTextColor(TEXT_COLOR);

	// Add Button Back
	Button butonBack = (Button) source.findViewById(R.id.ar_back_button);
	butonBack.setText("BACK");
	butonBack.setTypeface(tf);
	butonBack.setTextColor(TEXT_COLOR);
	butonBack.setBackgroundResource(BUTTON_COLOR);

	// Add Listeners to Button Back
	butonBack.setOnClickListener(this);
    }

    private boolean isVibrationFeedbackEnabled() {
	return vibrationEnabled;
    }

    @Override
    public void onClick(View v) {

	if (vibrationEnabled) {
	    Vibrator vibr = (Vibrator) source.getContext().getSystemService(
		    Context.VIBRATOR_SERVICE);
	    // Vibrate for 500 milliseconds
	    vibr.vibrate(500);
	}
	// Intent i = new Intent(mySetup.getActivity(), MenuActivity.class);
	// i.putExtra("COMMING_FROM", "FROM_3D");
	// mySetup.getActivity().startActivity(i);
	// mySetup.getActivity().finish();
    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    // ====================
    // GETTERS & SETTERS
    // ====================

}
