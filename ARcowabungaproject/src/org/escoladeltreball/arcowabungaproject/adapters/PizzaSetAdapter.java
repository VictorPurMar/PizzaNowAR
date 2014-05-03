package org.escoladeltreball.arcowabungaproject.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.escoladeltreball.arcowabungaproject.R;
import org.escoladeltreball.arcowabungaproject.dao.DAOAndroid;
import org.escoladeltreball.arcowabungaproject.model.Pizza;
import org.escoladeltreball.arcowabungaproject.model.Product;
import org.escoladeltreball.arcowabungaproject.model.system.Pizzeria;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PizzaSetAdapter extends BaseExpandableListAdapter {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    private final List<Pizza> pizzas = new ArrayList<Pizza>();
    public LayoutInflater inflater;
    public Activity activity;

    // ====================
    // CONSTRUCTORS
    // ====================

    public PizzaSetAdapter(Activity activity, Set<Pizza> pizzas) {
	this.activity = activity;
	for (Pizza pizza : pizzas) {
	    this.pizzas.add(pizza);
	}
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

    @Override
    public Object getChild(int groupPosition, int childPosition) {
	return pizzas.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
	return 0;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
	    boolean isLastChild, View convertView, ViewGroup parent) {
	final Pizza children = (Pizza) getChild(groupPosition, childPosition);
	TextView tvDesc;
	ImageView ivIcon;
	LinearLayout llButton;
	if (convertView == null) {
	    convertView = inflater
		    .inflate(R.layout.expanded_pizza_layout, null);
	}
	tvDesc = (TextView) convertView.findViewById(R.id.textInSubItem);
	ivIcon = (ImageView) convertView.findViewById(R.id.imageInSubItem);
	llButton = (LinearLayout) convertView
		.findViewById(R.id.pizzaButtonInSubItem);

	tvDesc.setText(children.getIngedientsDescription());

	DAOAndroid dao = DAOAndroid.getInstance();
	Drawable icon = dao.getDrawableFromAssets(activity, children.getIcon());
	ivIcon.setBackgroundDrawable(icon);

	llButton.setOnClickListener(new ARButtonClickListener(groupPosition));

	return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
	return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
	return pizzas.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
	return pizzas.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
	return pizzas.get(groupPosition).getId();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
	    View convertView, ViewGroup parent) {
	Pizza group = (Pizza) getGroup(groupPosition);
	ImageView ivIcon;
	TextView tvTitle;
	TextView tvPrice;
	TextView tvDesc;
	ImageButton ibAdd;
	if (convertView == null) {
	    convertView = inflater
		    .inflate(R.layout.listitem_pizza_layout, null);
	}
	ivIcon = (ImageView) convertView.findViewById(R.id.imageInItem);
	tvTitle = (TextView) convertView.findViewById(R.id.titleTextInItem);
	tvPrice = (TextView) convertView.findViewById(R.id.priceTextInItem);
	tvDesc = (TextView) convertView.findViewById(R.id.descTextInItem);
	ibAdd = (ImageButton) convertView.findViewById(R.id.imageButtonInItem);
	DAOAndroid dao = DAOAndroid.getInstance();
	Drawable icon = dao.getDrawableFromAssets(activity, group.getIcon());
	ivIcon.setBackgroundDrawable(icon);

	tvTitle.setText(group.getName());
	tvPrice.setText(group.getFormatedPrice());
	String desc = group.getIngedientsDescription();
	if (desc.length() > 20) {
	    desc = desc.substring(0, 17) + "...";
	}
	String showMore = "<font color='#FF0000'>"
		+ activity.getResources().getString(R.string.showMore)
		+ "</font>";
	tvDesc.setText(Html.fromHtml(desc + showMore));

	ibAdd.setOnClickListener(new AddButtonClickListener(group));

	return convertView;
    }

    @Override
    public boolean hasStableIds() {
	return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
	return false;
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

    public class ARButtonClickListener implements OnClickListener {

	private int index;

	public ARButtonClickListener(int index) {
	    this.index = index;
	}

	@Override
	public void onClick(View v) {
	    // Intent i = new Intent(activity, ARViewActivity.class);
	    // Pasarle pizza
	    // i.putExtra("pizza", pizzas.get(index));
	    // activity.startActivity(i);
	    pizzas.get(index);
	}
    }

    public class AddButtonClickListener implements OnClickListener {

	private Product product;

	public AddButtonClickListener(Product product) {
	    this.product = product;
	}

	@Override
	public void onClick(View v) {
	    Pizzeria.getInstance().getShoppingCart().addProduct(product);
	    Toast.makeText(activity,
		    "S'ha afegit una producte al carro de la compra",
		    Toast.LENGTH_SHORT).show();
	}

    }
}
