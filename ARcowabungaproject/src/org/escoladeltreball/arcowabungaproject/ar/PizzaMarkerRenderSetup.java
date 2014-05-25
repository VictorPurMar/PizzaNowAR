/*
 *  PizzaMarkerRenderSetup.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  			
 *  This class extends from MarkerRenderSetup, and that one from Setup. 
 *  It will render the models, add they to world in a assigned camera position
 *  (marker position), and set the Gui PizzaGuiSetup.
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

package org.escoladeltreball.arcowabungaproject.ar;

import geo.GeoObj;
import gl.CustomGLSurfaceView;
import gl.GL1Renderer;
import gl.GLCamera;
import gl.GLFactory;
import gl.GLRenderer;
import gui.GuiSetup;

import java.util.ArrayList;

import markerDetection.DetectionThread;
import markerDetection.MarkerDetectionSetup;
import markerDetection.MarkerObjectMap;
import markerDetection.UnrecognizedMarkerListener;

import org.escoladeltreball.arcowabungaproject.R;

import preview.Preview;
import system.EventManager;
import util.Vec;
import worldData.Obj;
import worldData.SystemUpdater;
import actions.ActionBufferedCameraAR;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import commands.Command;

import de.rwth.GDXConnection;
import de.rwth.GDXMesh;

public class PizzaMarkerRenderSetup extends MarkerDetectionSetup {
    // ====================
    // CONSTANTS
    // ====================

    // Initial render dispose test
    // private static final Vec INIT_POSITION_VECTOR = new Vec(0, 0, -14);
    // private static final Vec INIT_ROTATION_VECTOR = new Vec(-55, 0, 0);

    /**
     * Initial model on create position
     */
    private static final Vec INIT_POSITION_VECTOR = new Vec(0, 0, 0);
    /**
     * Initial model on create rotation
     */
    private static final Vec INIT_ROTATION_VECTOR = new Vec(0, 0, 0);

    // ====================
    // ATTRIBUTES
    // ====================

    /**
     * Continuosly detecting the marker position
     */
    private DetectionThread myThread;
    /**
     * To reload the cammera parametters on restart
     */
    private Preview cameraPreview;
    /**
     * GL in world camera
     */
    private GLCamera camera;
    /**
     * Object 3d rendered
     */
    private GL1Renderer renderer;
    /**
     * This attribute comes from PizzaModelMaper Represents the Size and
     * thickness of the pizza
     */
    private Vec pizzaSizeAndMassVector;
    /**
     * The Setup of GUI overposed to camera
     */
    private PizzaGuiSetup pizzaGuiSetup;
    /**
     * The world container of renderers and camera
     */
    public PizzaWorld world;
    /**
     * The mesh former
     */
    public GDXMesh meshComponent;

    // ====================
    // CONSTRUCTORS
    // ====================

    // ====================
    // PUBLIC METHODS
    // ====================

    /**
     * Calculates the scale vector according Mesh and Scale from PizzaModelMaper
     * 
     * @return resultVector as Vec (vector) with the calculated parameters
     */
    private Vec pizzaVectorCalculator() {
	float pizzaMesh = PizzaModelMapper.getPizzaMassType();
	float pizzaScale = (float) PizzaModelMapper.getPizzaScale();
	Vec resultVector = new Vec(pizzaScale, pizzaScale, pizzaScale
		* pizzaMesh);
	return resultVector;
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
    public UnrecognizedMarkerListener _a2_getUnrecognizedMarkerListener() {
	return null;
    }

    @Override
    public void _a3_registerMarkerObjects(MarkerObjectMap markerObjectMap) {
	markerObjectMap.put(new PizzaCameraMarker(0, camera));
    }

    @Override
    public void _a_initFieldsIfNecessary() {
	camera = new GLCamera();
	world = new PizzaWorld(camera);
	pizzaSizeAndMassVector = pizzaVectorCalculator();
    }

    @Override
    public void _b_addWorldsToRenderer(GL1Renderer renderer,
	    GLFactory objectFactory, GeoObj currentPosition) {
	this.renderer = renderer;
	GDXConnection.init(this.getActivity(), this.renderer);
	// Load a previous alpha texture of the ingredient model
	// Helps to show all correctly
	new PizzaModelLoader(this.renderer, PizzaModelMapper.BASIC_PIZZA_MODEL,
		PizzaModelMapper.INGREDIENT_ALPHA_TEXTURE) {
	    @Override
	    public void modelLoaded(GDXMesh pizzaMesh) {
		pizzaMesh.setPosition(INIT_POSITION_VECTOR);
		pizzaMesh.setRotation(INIT_ROTATION_VECTOR);
		pizzaMesh.setScale(pizzaSizeAndMassVector);
		final Obj o = new Obj();
		o.setComp(pizzaMesh);
		world.add(o);
		world.remove(o);
	    }
	};

	// Pizza base model and texture loader
	new PizzaModelLoader(this.renderer, PizzaModelMapper.BASIC_PIZZA_MODEL,
		PizzaModelMapper.BASIC_PIZZA_TEXTURE) {
	    @Override
	    public void modelLoaded(GDXMesh pizzaMesh) {
		pizzaMesh.setPosition(INIT_POSITION_VECTOR);
		pizzaMesh.setRotation(INIT_ROTATION_VECTOR);
		pizzaMesh.setScale(pizzaSizeAndMassVector);
		final Obj o = new Obj();
		o.setComp(pizzaMesh);
		world.add(o);
	    }
	};

	ArrayList<String> ingredientTextures = (ArrayList<String>) PizzaModelMapper
		.getModelIngredientTextures();

	// Method to deploy the ingredients object and textures
	if (ingredientTextures.size() > 0) {
	    for (int i = 0; i < ingredientTextures.size(); i++) {
		// Load a previous alpha texture of the ingredient model
		// Helps to show all correctly
		new PizzaModelLoader(this.renderer,
			PizzaModelMapper.BASIC_PIZZA_MODEL,
			PizzaModelMapper.INGREDIENT_ALPHA_TEXTURE) {
		    @Override
		    public void modelLoaded(GDXMesh pizzaMesh) {
			pizzaMesh.setPosition(INIT_POSITION_VECTOR);
			pizzaMesh.setRotation(INIT_ROTATION_VECTOR);
			pizzaMesh.setScale(pizzaSizeAndMassVector);
			final Obj o = new Obj();
			o.setComp(pizzaMesh);
			world.add(o);
			world.remove(o);
		    }
		};
		new PizzaModelLoader(this.renderer,
			PizzaModelMapper.INGREDIENT_MODEL,
			ingredientTextures.get(i)) {
		    @Override
		    public void modelLoaded(GDXMesh pizzaMesh) {
			// pizzaMesh.setRotation(new Vec(0f, 0f, (float) (Math
			// .random() * 10)));
			pizzaMesh.setPosition(INIT_POSITION_VECTOR);
			pizzaMesh.setRotation(INIT_ROTATION_VECTOR);
			pizzaMesh.setScale(pizzaSizeAndMassVector);
			final Obj o = new Obj();
			o.setComp(pizzaMesh);
			world.add(o);
		    }
		};
	    }
	}

	this.renderer.addRenderElement(world);
    }

    @Override
    public void _c_addActionsToEvents(EventManager eventManager,
	    CustomGLSurfaceView arView, SystemUpdater updater) {
	arView.addOnTouchMoveListener(new ActionBufferedCameraAR(camera));

    }

    @Override
    public void _d_addElementsToUpdateThread(SystemUpdater updater) {
	updater.addObjectToUpdateCycle(world);

    }

    @Override
    public void _e1_addElementsToOverlay(FrameLayout myOverlayView,
	    Activity activity) {
	// the main.xml layout is loaded and the guiSetup is created for
	// customization. then the customized view is added to overlayView
	View sourceView = View.inflate(getActivity(),
		R.layout.arview_gui_layout, null);
	// Display the pizzaGuiSetup
	pizzaGuiSetup = new PizzaGuiSetup(this, sourceView);
	pizzaGuiSetup.run();
	// Add functionality to options android device button
	addDroidARInfoBox(activity);
	myOverlayView.addView(sourceView);

    };

    /**
     * This method can be displayed on _e1_addElementsToOverlay to add
     * functionallity to the android device options button
     * 
     * @param currentActivity
     */
    private void addDroidARInfoBox(final Activity currentActivity) {
	addItemToOptionsMenu(new Command() {
	    @Override
	    public boolean execute() {
		return true;
	    }
	}, "Pizza Now : \"Sergi Grau recommends this\"");
    }

    @Override
    public void _e2_addElementsToGuiSetup(GuiSetup guiSetup, Activity activity) {
    }

    // This will send you back to the last activity
    @Override
    public void onDestroy(Activity a) {
	super.onDestroy(a);
	if (cameraPreview != null)
	    cameraPreview.releaseCamera();
	// Ensure app is gone after back button is pressed!
	if (myThread != null)
	    myThread.stopThread();
    }

    @Override
    public GLRenderer initOpenGLRenderer() {
	GL1Renderer r = new GL1Renderer();
	// r.setUseLightning(_a2_initLightning(r.getMyLights()));
	return r;
    }
    // ====================
    // GETTERS & SETTERS
    // ====================

}
