package org.escoladeltreball.arcowabungaproject.ar;

import java.io.IOException;
import java.util.Locale;

import org.escoladeltreball.arcowabungaproject.R;
import org.escoladeltreball.arcowabungaproject.activities.MenuActivity;

import android.content.Context;
import android.content.Intent;
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

    private boolean ON_CLICK_VIBRATE = true;
    private static final long VIBRATION_DURATION_IN_MS = 20;
    private static final int BUTTON_COLOR = R.color.background_red_order;
    private static final int TEXT_COLOR = Color.WHITE;
    private static final String FONT_TYPE = "fonts/gnuolane.ttf";
    public RelativeLayout main;
    private PizzaMarkerRenderSetup mySetup;
    private View source;
    Vibrator vibrator;

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
	this.vibrator = (Vibrator) source.getContext().getSystemService(
		Context.VIBRATOR_SERVICE);

    }

    // ====================
    // PUBLIC METHODS
    // ====================

    public void run() {

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
	TextView pizzaName = (TextView) source.findViewById(R.id.ar_pizza_name);
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

	if (ON_CLICK_VIBRATE) {
	    vibrator.vibrate(VIBRATION_DURATION_IN_MS);
	}
	Intent i = new Intent(mySetup.getActivity(), MenuActivity.class);
	i.putExtra("COMMING_FROM", "FROM_3D");
	mySetup.getActivity().startActivity(i);
	mySetup.getActivity().finish();
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

}
