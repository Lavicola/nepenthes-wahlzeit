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
public class Coordinate {
    //immutable since it does not make sense to change the coordiantes later on.
    private final double x;
    private final double y;
    private final double z;
    public final static double EPSILON = 1e-12;


	/**
	 * 
	 * @methodtype constructor
	 */
public Coordinate(double x,double y, double z){
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
     public double getDistance(Coordinate coordinate) {
        // exception handling was not yet asked so we just return the biggest negative number
        if(coordinate == null){
            throw new IllegalArgumentException("The Arguement can´t be null.");
        }

		double x_delta = Math.pow(coordinate.getX() - this.getX(), 2);
		double y_delta = Math.pow(coordinate.getY() - this.getY(),2);
		double z_delta = Math.pow(coordinate.getZ() - this.getZ(),2);

		return Math.sqrt(x_delta + y_delta + z_delta);
	}

	public void writeOn(ResultSet rset) throws SQLException {
        rset.updateDouble("coordinate_x", this.getX());
        rset.updateDouble("coordinate_y", this.getY());
        rset.updateDouble("coordinate_z", this.getZ());
    }

	public Coordinate readFrom(ResultSet rset) throws SQLException {
        double x = rset.getDouble("coordinate_x");
        if(!rset.wasNull()){
            return new Coordinate(x, rset.getDouble("coordinate_y"), rset.getDouble("coordinate_z"));
        }
        return null;
    }
    @Override
    public boolean equals(Object o){
        if(o != null && o.getClass() == this.getClass() ){
            return this.isEqual((Coordinate) o);
        }else{
            throw new IllegalArgumentException();
        }
        
    }

    /**
	 * Check if two Coordinates are equal. 
     * Equal means both points are exactly(!) the same
     * That means (1.0,1.0,1.0000001) != (1.0,1.0,1.0000001) 
	 */
    public boolean isEqual(Coordinate coordinate){
        boolean isXEqual = checkEqualDouble(coordinate.getX(), this.getX());
        boolean isYEqual = checkEqualDouble(coordinate.getY(), this.getY());
        boolean isZEqual = checkEqualDouble(coordinate.getZ(), this.getZ());

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

}
