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

}
