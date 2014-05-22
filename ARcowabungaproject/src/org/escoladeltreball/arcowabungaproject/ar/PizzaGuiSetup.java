package org.escoladeltreball.arcowabungaproject.ar;

import org.escoladeltreball.arcowabungaproject.R;

import system.TaskManager;
import util.Log;
import util.Wrapper;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import commands.Command;
import commands.logic.CommandSetWrapperToValue;
import commands.system.CommandDeviceVibrate;

public class PizzaGuiSetup {

    // ====================
    // CONSTANTS
    // ====================

    private static final String LOG_TAG = "GuiSetup";
    private static final long VIBRATION_DURATION_IN_MS = 20;
    private LinearLayout bottomOuter;
    private LinearLayout bottomView;
    private RelativeLayout main;
    private OwnMarkerRenderSetup mySetup;
    private boolean vibrationEnabled;
    private CommandDeviceVibrate vibrateCommand;

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
    public PizzaGuiSetup(OwnMarkerRenderSetup setup, View source) {

	mySetup = setup;
	Log.d(LOG_TAG, "GuiSetup init");
	setVibrationFeedbackEnabled(true);

	main = (RelativeLayout) source.findViewById(R.id.main_view);

	bottomOuter = (LinearLayout) source.findViewById(R.id.LLA_bottom);
	bottomView = (LinearLayout) source.findViewById(R.id.LinLay_bottom);

    }

    // ====================
    // PUBLIC METHODS
    // ====================

    public void addButtonToBottomView(Command a, String buttonText) {
	addButtonToView(bottomView, a, buttonText);
    }

    public void addImageButtonToView(LinearLayout target, final Command c,
	    int imageId) {
	if (target != null) {
	    ImageButton b = new ImageButton(target.getContext());
	    // b.setBackgroundResource(BUTTON_BACKGROUND);
	    b.setImageResource(imageId);
	    b.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		    if (isVibrationFeedbackEnabled() && vibrateCommand != null) {
			vibrateCommand.execute();
		    }
		    c.execute();
		}
	    });
	    target.addView(b);
	} else {
	    Log.e(LOG_TAG, "No target specified (was null) "
		    + "to add the image-button to.");
	}
    }

    /**
     * @param target
     * @param onClickCommand
     * @param buttonText
     */
    public void addButtonToView(LinearLayout target,
	    final Command onClickCommand, String buttonText) {
	if (target != null) {
	    Button b = new Button(target.getContext());
	    // b.setBackgroundResource(BUTTON_BACKGROUND);
	    // b.setTextColor(gl.Color.blackTransparent().toIntRGB());
	    b.setText(buttonText);
	    b.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		    if (isVibrationFeedbackEnabled() && vibrateCommand != null) {
			vibrateCommand.execute();
		    }
		    onClickCommand.execute();
		}

	    });
	    target.addView(b);
	}
    }

    private boolean isVibrationFeedbackEnabled() {
	return vibrationEnabled;
    }

    public void setVibrationFeedbackEnabled(boolean vibrate) {
	this.vibrationEnabled = vibrate;
	if (vibrate && vibrateCommand == null) {
	    try {
		Log.d(LOG_TAG,
			"Trying to enable vibration feedback for UI actions");
		vibrateCommand = new CommandDeviceVibrate(
			mySetup.myTargetActivity, VIBRATION_DURATION_IN_MS);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    public void addCheckBoxToView(LinearLayout v, String text,
	    boolean initFlag, final Command isCheckedCommand,
	    final Command isNotCheckedCommand) {
	CheckBox c = new CheckBox(v.getContext());

	c.setText(text);
	c.setChecked(initFlag);
	c.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	    @Override
	    public void onCheckedChanged(CompoundButton buttonView,
		    boolean isChecked) {
		if (isChecked) {
		    isCheckedCommand.execute();
		} else {
		    isNotCheckedCommand.execute();
		}

	    }
	});
	v.addView(c);
    }

    public void addCheckBoxToView(LinearLayout v, String string,
	    Wrapper wrapperWithTheBooleanToSwitchInside) {
	CommandSetWrapperToValue setTrue = new CommandSetWrapperToValue(
		wrapperWithTheBooleanToSwitchInside, true);
	CommandSetWrapperToValue setFalse = new CommandSetWrapperToValue(
		wrapperWithTheBooleanToSwitchInside, false);
	addCheckBoxToView(v, string,
		wrapperWithTheBooleanToSwitchInside.getBooleanValue(), setTrue,
		setFalse);
    }

    public EditText addSearchbarToView(LinearLayout v,
	    final Command commandOnSearch, String clearText) {
	final EditText t = new EditText(v.getContext());
	t.setHint(clearText);
	t.setHintTextColor(Color.GRAY);
	t.setMinimumWidth(200);
	t.setSingleLine();
	t.setSelectAllOnFocus(true);
	t.setOnKeyListener(new OnKeyListener() {
	    @Override
	    public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
		    String text = t.getText().toString();
		    if (text.length() > 0) {
			t.setText("");
			Log.d(LOG_TAG, "Gui-searchbar fiering text: '" + text
				+ "'(length=" + text.length() + ")!");
			return commandOnSearch.execute(text);
		    }
		}
		return false;
	    }
	});
	v.addView(t);
	return t;
    }

    public void addTaskmanagerToView(LinearLayout v) {
	addTaskmanagerToView(v, "", " <", "/", "> ");
    }

    public void addTaskmanagerToView(LinearLayout v, String idleText,
	    String workingPrefix, String workingMiddleText, String workingSuffix) {
	v.addView(TaskManager.getInstance().getProgressWheel(v.getContext()));
	v.addView(TaskManager.getInstance().getProgressTextView(v.getContext(),
		idleText, workingPrefix));
	v.addView(TaskManager.getInstance().getProgressSizeText(v.getContext(),
		idleText, workingMiddleText, workingSuffix));
    }

    public void addViewToBottom(View v) {
	bottomView.addView(v);
    }

    public View getMainContainerView() {
	return main;
    }

    public LinearLayout getBottomView() {
	return bottomView;
    }

    public void setBackroundColor(LinearLayout target, int color) {
	target.setBackgroundColor(color);
    }

    public void setBottomBackroundColor(int color) {
	setBackroundColor(bottomOuter, color);
    }

    public void setBottomMinimumHeight(int height) {
	setMinimumHeight(bottomOuter, height);
    }

    public void setBottomViewCentered() {
	bottomOuter.setGravity(Gravity.CENTER);
    }

    public void setMinimumHeight(LinearLayout target, int height) {
	target.setMinimumHeight(height);
    }

    public void setMinimumWidth(LinearLayout target, int width) {
	target.setMinimumWidth(width);
    }

    public void addItemToOptionsMenu(Command commandToAdd, String menuItemText) {
	mySetup.addItemToOptionsMenu(commandToAdd, menuItemText);
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

    // ====================
    // GETTERS & SETTERS
    // ====================

}
