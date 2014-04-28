package org.escoladeltreball.arcowabungaproject.model;

import java.util.HashMap;
import java.util.Set;

public class Ingredients extends HashMap<Ingredient, Integer> {

    // ====================
    // CONSTANTS
    // ====================

    public static final int MIN_INGREDIENTS = 3;
    public static final int MAX_INGREDIENTS = 16;
    private static final long serialVersionUID = 2916673702284211535L;

    // ====================
    // ATTRIBUTES
    // ====================

    private int id;

    // ====================
    // CONSTRUCTORS
    // ====================

    public Ingredients(int id) {
	super();
	this.id = id;
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    public boolean add(Ingredient ingredient) {
	return add(ingredient, 1);
    }

    public boolean add(Ingredient ingredient, int value) {
	if (getTotalIngredients() + value <= MAX_INGREDIENTS
		|| get(ingredient) + value >= 0) {
	    if (containsKey(ingredient)) {
		value = get(ingredient) + value;
	    }
	    put(ingredient, value);
	    return true;
	} else {
	    return false;
	}
    }

    public int getTotalIngredients() {
	int total = 0;
	for (int v : this.values()) {
	    total += v;
	}
	return total;
    }

    public Set<Ingredient> getIngredients() {
	return keySet();
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
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + id;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Ingredients other = (Ingredients) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Ingredients [id=" + id + ", " + super.toString() + "]";
    }

    public void print() {
	System.out.println(toString());
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

}