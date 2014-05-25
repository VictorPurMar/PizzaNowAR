/*
 *  PizzaModelLoader.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  	
 *  Makes a GLDXMesh render object from a model (.obj) file and texture (.png or .jpg)		
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

    /**
     * Add render from filename .obj path and filename texture path
     * 
     * @param renderer
     * @param fileName
     * @param textureFileName
     */
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

    /**
     * Get the GDXMesh component
     * 
     * @return x GDXMesh
     */
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

    /**
     * Load the texture and model .obj from file
     */
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
