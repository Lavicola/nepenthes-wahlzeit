/*
 * SPDX-FileCopyrightText: 2021 David Schmidt https://github.com/Lavicola
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


/**
 * Cartesian coordinates .
 */
public class CartesianCoordinates implements Coordiante {
    //immutable since it does not make sense to change the coordiantes later on.
    private final double x;
    private final double y;
    private final double z;
    public final static double EPSILON = 1e-5;


	/**
	 * 
	 * @methodtype constructor
	 */
public CartesianCoordinates(double x, double y, double z){
    this.x = x;
    this.y = y;
    this.z = z;
    }

	/**
	 * 
	 * @methodtype get
	 */
    public double getX(){
        return this.x;
    }

    /**
	 * 
	 * @methodtype get
	 */
    public double getY(){
        return this.y;
    }

    /**
	 * 
	 * @methodtype get
	 */
    public double getZ(){
        return this.z;
}


 	/* direct Cartesian distance.
       Formular: root( (x2 - x1)² + (y2 - y1)² + (z2 - z1)² )
    */
     public double getDistance(CartesianCoordinates cartesianCoordinates) {
        // exception handling was not yet asked so we just return the biggest negative number
        assertNotNull(cartesianCoordinates);

		double x_delta = Math.pow(cartesianCoordinates.getX() - this.getX(), 2);
		double y_delta = Math.pow(cartesianCoordinates.getY() - this.getY(),2);
		double z_delta = Math.pow(cartesianCoordinates.getZ() - this.getZ(),2);

		return Math.sqrt(x_delta + y_delta + z_delta);
	}

	public void writeOn(ResultSet rset) throws SQLException {
        rset.updateDouble("coordinate_x", this.getX());
        rset.updateDouble("coordinate_y", this.getY());
        rset.updateDouble("coordinate_z", this.getZ());
    }

	public CartesianCoordinates readFrom(ResultSet rset) throws SQLException {
        double x = rset.getDouble("coordinate_x");
        if(!rset.wasNull()){
            return new CartesianCoordinates(x, rset.getDouble("coordinate_y"), rset.getDouble("coordinate_z"));
        }
        return null;
    }
    @Override
    public boolean equals(Object o){
         assertNotNull(o);
         if(o instanceof Coordiante){
            return this.isEqual((Coordiante) o);
        }else{
            throw new IllegalArgumentException();
        }
        
    }

    /**
	 * Check if two Coordinates are equal. 
     *
     *
	 */
    public boolean isEqual(CartesianCoordinates cartesianCoordinates){
        assertNotNull(cartesianCoordinates);
        boolean isXEqual = checkEqualDouble(cartesianCoordinates.getX(), this.getX());
        boolean isYEqual = checkEqualDouble(cartesianCoordinates.getY(), this.getY());
        boolean isZEqual = checkEqualDouble(cartesianCoordinates.getZ(), this.getZ());

        if(isXEqual && isYEqual && isZEqual){
            return true;
        }
        return false;
    }

    private boolean checkEqualDouble(double d1, double d2) {
        return EPSILON > Math.abs(d1-d2);
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public CartesianCoordinates asCartesianCoordinate() {
        return this;
    }

    @Override
    public double getCartesianDistance(Coordiante coordiante) {
        return this.getDistance(coordiante.asCartesianCoordinate());
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        double radius = Math.sqrt(Math.pow(x,2) +
                                  Math.pow(y,2) +
                                  Math.pow(z,2));
        //TODO IF RADIUS 0
        if(radius == 0){
            return new SphericCoordinate(0,0,0);
        }
        double theta = Math.acos(z / radius);
        double phi = CalculatePhi(x,y);
        return new SphericCoordinate(phi, theta, radius);
    }


    //helper function which return Phi for a given x and y
    public double CalculatePhi(double x,double y){
        double phi;

        if(x > 0){
            phi = Math.atan((y/x));
        }else if(x < 0){
            phi = Math.atan((y/x)) + Math.PI;
        }else{
            phi = Math.PI/2;
        }
        return phi;
    }


    @Override
    public double getCentralAngle(Coordiante coordiante) {
        SphericCoordinate sphericCoordiante = this.asSphericCoordinate();
        return sphericCoordiante.getCentralAngle(coordiante);

    }

    @Override
    public boolean isEqual(Coordiante coordiante) {
        assertNotNull(coordiante);
        return this.isEqual(coordiante.asCartesianCoordinate());
    }

    static void assertNotNull(Object o) {
        if(o == null){
            throw new IllegalArgumentException("object was null");
        }
        return;
    }
}


