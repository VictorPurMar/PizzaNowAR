/*
 *  CameraMarker.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  The camera marker will set the world coordinates to the center of a marker
 *  and move the camera around it	
 *  		
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

import gl.GLCamera;
import gl.MarkerObject;
import markerDetection.UnrecognizedMarkerListener;

public class PizzaCameraMarker implements MarkerObject {

    private int myId;
    private GLCamera myCamera;

    /**
     * The camera marker will set the world coordinates to the center of a
     * marker and move the camera around it
     * 
     * @param id
     * @param camera
     */
    public PizzaCameraMarker(int id, GLCamera camera) {
	myId = id;
	myCamera = camera;
    }

    @Override
    public int getMyId() {
	return myId;
    }

    @Override
    public void OnMarkerPositionRecognized(float[] rotMatrix, int start, int end) {
	myCamera.setRotationMatrix(rotMatrix, start);
    }

    public UnrecognizedMarkerListener _a2_getUnrecognizedMarkerListener() {
	return null;
    }

}
