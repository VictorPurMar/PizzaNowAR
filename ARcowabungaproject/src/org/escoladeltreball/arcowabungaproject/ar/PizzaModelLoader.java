package org.escoladeltreball.arcowabungaproject.ar;

import gl.GL1Renderer;
import gl.scenegraph.MeshComponent;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

import de.rwth.GDXMesh;

public abstract class PizzaModelLoader implements gl.Renderable {

    // ====================
    // CONSTANTS
    // ====================

    private static final String LOGTAG = "PizzaModelCreator";
    private String fileName;
    private String textureFileName;
    private Texture texture;
    private StillModel model;
    private GL1Renderer renderer;

    // ====================
    // ATTRIBUTES
    // ====================

    // ====================
    // CONSTRUCTORS
    // ====================

    public PizzaModelLoader(GL1Renderer renderer, String fileName,
	    String textureFileName) {
	this.renderer = renderer;
	this.fileName = fileName;
	this.textureFileName = textureFileName;
	renderer.addRenderElement(this);
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    public MeshComponent getGDXShape() {
	GDXMesh x = new GDXMesh(model, texture);
	return x;
    }

    public abstract void modelLoaded(GDXMesh pizzaMesh);

    // ====================
    // PROTECTED METHODS
    // ====================

    // ====================
    // PRIVATE METHODS
    // ====================

    private void loadModelFromFile() {

	try {
	    if (textureFileName != null)
		texture = new Texture(Gdx.files.internal(textureFileName), true);
	} catch (Exception e) {
	    Log.e(LOGTAG, "Could not load the specified texture: "
		    + textureFileName);
	    e.printStackTrace();
	}

	if (fileName.endsWith(".obj"))
	    model = new ObjLoader().loadObj(Gdx.files.internal(fileName), true);

    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    @Override
    public void render(javax.microedition.khronos.opengles.GL10 gl,
	    gl.Renderable parent) {

	Log.d(LOGTAG, "Trying to load " + fileName);

	try {
	    loadModelFromFile();
	} catch (Exception e) {
	    Log.e(LOGTAG, "Could not load model");
	    e.printStackTrace();
	}

	if (model != null)
	    modelLoaded(new GDXMesh(model, texture));

	renderer.removeRenderElement(this);

    }

    // ====================
    // GETTERS & SETTERS
    // ====================

}
