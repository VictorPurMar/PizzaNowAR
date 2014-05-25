/*
 *  PizzaModelMapper.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *
 *  This class read and display in static attributes all the info from an
 *  Pizza input to be used by PizzaMarkerRenderSetup
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.escoladeltreball.arcowabungaproject.dao.DAOAndroid;
import org.escoladeltreball.arcowabungaproject.model.Ingredient;
import org.escoladeltreball.arcowabungaproject.model.Pizza;

public class PizzaModelMapper {

    // ====================
    // CONSTANTS
    // ====================

    /**
     * PIZZA
     */
    public final static String BASIC_PIZZA_MODEL = "data/models/pizza_V5.obj";
    public final static String BASIC_PIZZA_TEXTURE = "data/models/arpizza_texture.jpg";

    /**
     * INGREDIENTS
     */
    public final static String INGREDIENT_MODEL = "data/models/ingredient_V5.obj";
    public final static String INGREDIENT_ALPHA_TEXTURE = "data/models/alpha_texture.png";

    // ====================
    // ATTRIBUTES
    // ====================

    /**
     * List with the ingredient path textures
     */
    private static ArrayList<String> modelIngredientTextures;
    /**
     * Set of Ingredients
     */
    private static Set<Ingredient> ingredients;
    /**
     * The Pizza scale
     */
    private static int pizzaScale;
    /**
     * The Pizza mass thickness
     */
    private static float pizzaMassType;
    /**
     * The pizza name
     */
    private static String pizzaName;
    /**
     * String with all Pizza ingredient names
     */
    private static String ingredientDescription;

    // ====================
    // CONSTRUCTORS
    // ====================

    // ====================
    // PUBLIC METHODS
    // ====================

    /**
     * Fills correctly the static attributes
     * 
     * @param pizza
     *            Pizza
     */
    public static void run(Pizza pizza) {
	ingredients = pizza.getIngredientsSet();
	pizzaScale = pizza.getSize();
	pizzaName = pizza.getName();
	ingredientDescription = pizza.getIngedientsDescription();
	modelIngredientTextures = new ArrayList<String>();
	makeTheModelIngredientTextureList();
	pizzaMassTypeFloatTranslator(pizza.getMassType());
    }

    // ====================
    // PROTECTED METHODS
    // ====================

    // ====================
    // PRIVATE METHODS
    // ====================

    /**
     * Fills modelIngredientTextures adding the ingredient texture path
     */
    private static void makeTheModelIngredientTextureList() {
	DAOAndroid daoA = DAOAndroid.getInstance();
	for (Ingredient ingredient : ingredients) {
	    modelIngredientTextures.add(daoA.getResourcePath(ingredient
		    .getTexture()));
	}
    }

    /**
     * Transform the pizzaMassType to a float
     * 
     * @param pizzaMassTypeIncome
     */
    private static void pizzaMassTypeFloatTranslator(String pizzaMassTypeIncome) {
	if (pizzaMassTypeIncome.equals(Pizza.MASSTYPE_THIN)) {
	    pizzaMassType = 0.3f;
	} else if (pizzaMassTypeIncome.equals(Pizza.MASSTYPE_NORMAL)) {
	    pizzaMassType = 1f;
	} else if (pizzaMassTypeIncome.equals(Pizza.MASSTYPE_THIN)) {
	    pizzaMassType = 2.5f;
	}

    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    // ====================
    // GETTERS & SETTERS
    // ====================

    /**
     * @return the modelIngredientTextures
     */
    public static List<String> getModelIngredientTextures() {
	return modelIngredientTextures;
    }

    /**
     * @param modelIngredientTextures
     *            the modelIngredientTextures to set
     */
    public static void setModelIngredientTextures(
	    ArrayList<String> modelIngredientTextures) {
	PizzaModelMapper.modelIngredientTextures = modelIngredientTextures;
    }

    /**
     * @return the pizzaScale
     */
    public static int getPizzaScale() {
	return pizzaScale;
    }

    /**
     * @param pizzaScale
     *            the pizzaScale to set
     */
    public static void setPizzaScale(int pizzaScale) {
	PizzaModelMapper.pizzaScale = pizzaScale;
    }

    /**
     * @return the pizzaMassType
     */
    public static float getPizzaMassType() {
	return pizzaMassType;
    }

    /**
     * @param pizzaMassType
     *            the pizzaMassType to set
     */
    public static void setPizzaMassType(float pizzaMassType) {
	PizzaModelMapper.pizzaMassType = pizzaMassType;
    }

    /**
     * @return pizzaName
     */
    public static String getPizzaName() {
	return pizzaName;
    }

    /**
     * Set the pizzaName
     * 
     * @param pizzaName
     */
    public static void setPizzaName(String pizzaName) {
	PizzaModelMapper.pizzaName = pizzaName;
    }

    /**
     * @return ingredientDescription
     */
    public static String getIngredientDescription() {
	return ingredientDescription;
    }

    /**
     * Set the ingredientDescription
     * 
     * @param ingredientDescription
     */
    public static void setIngredientDescription(String ingredientDescription) {
	PizzaModelMapper.ingredientDescription = ingredientDescription;
    }

}
