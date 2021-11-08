/*
 * SPDX-FileCopyrightText: 2021 David Schmidt https://github.com/Lavicola
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

// We don´t implement a setter since it does not make sense to change the coordiantes of a location later on.
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

	/**
	 * forward equals to coordinate equals in order to check if both locations have the same coordinates. 
	 */
	//
    @Override
    public boolean equals(Object o){
        if(o != null && o.getClass() == this.getClass() ){
			Location temp = (Location) o;
            return this.coordinate.isEqual(temp.getCoordinate());
        }else{
            throw new IllegalArgumentException();
        }
    }


}
