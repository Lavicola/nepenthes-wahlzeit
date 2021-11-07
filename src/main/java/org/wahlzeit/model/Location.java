/*
 * SPDX-FileCopyrightText: 2021 David Schmidt https://github.com/Lavicola
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;


public class Location {
    private Coordinate coordinate = null;

	/**
	 * 
	 * @methodtype constructor
	 */
    public Location(Coordinate coordinate){
        this.coordinate = new Coordinate(coordinate.getX(), coordinate.getY(), coordinate.getZ());
    }

	/**
	 * 
	 * @methodtype constructor
	 */
    public Location(double x,double y, double z){
        this.coordinate = new Coordinate(x,y,z);
    }

	/**
	 * 
	 * @methodtype constructor
	 */
    public Coordinate getCoordinate(){
        return this.coordinate;
    }

}
