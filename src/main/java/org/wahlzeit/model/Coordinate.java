/*
 * SPDX-FileCopyrightText: 2021 David Schmidt https://github.com/Lavicola
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;


/**
 * Cartesian coordinates .
 */
public class Coordinate {
    private double x;
    private double y;
    private double z;


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
		double x_delta = Math.pow(coordinate.getX() - this.getX(), 2);
		double y_delta = Math.pow(coordinate.getY() - this.getY(),2);
		double z_delta = Math.pow(coordinate.getZ() - this.getZ(),2);

		return Math.sqrt(x_delta + y_delta + z_delta);
	}

    public boolean equals(Coordinate coordinate){
        return isEqual(coordinate);
    }

    /**
	 * Check if two Coordinates are equal. 
     * Equal means both points are exactly(!) the same!
     * That means (1.0,1.0,1.0000001) != (1.0,1.0,1.0000001) 
	 */
    public boolean isEqual(Coordinate coordinate){
        double x_delta = coordinate.getX() - this.getX();
        double y_delta = coordinate.getY() - this.getY();
        double z_delta = coordinate.getZ() - this.getZ();

        //Only if the delta value of all points are 0 they are truly equal!
        if(x_delta == 0 && y_delta == 0 && z_delta == 0){
            return true;
        }
        return false;
    }

}
