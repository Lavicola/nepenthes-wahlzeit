/*
 * SPDX-FileCopyrightText: 2021 David Schmidt https://github.com/Lavicola
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

import java.sql.SQLException;
import java.sql.ResultSet;

// We don´t implement a setter since it does not make sense to change the coordiantes of a location later on.
public class Location {
    private CartesianCoordinates cartesianCoordinates = null;

	/**
	 * 
	 * @methodtype constructor
	 */
    public Location(CartesianCoordinates cartesianCoordinates){
        this.cartesianCoordinates = new CartesianCoordinates(cartesianCoordinates.getX(), cartesianCoordinates.getY(), cartesianCoordinates.getZ());
    }

	/**
	 * 
	 * @methodtype constructor
	 */
    public Location(double x,double y, double z){
        this.cartesianCoordinates = new CartesianCoordinates(x,y,z);
    }

	/**
	 * 
	 * @methodtype constructor
	 */
    public CartesianCoordinates getCoordinate(){
        return this.cartesianCoordinates;
    }


	public void writeOn(ResultSet rset) throws SQLException {
            cartesianCoordinates.writeOn(rset);
    }

	public Location readFrom(ResultSet rset) throws SQLException {
        CartesianCoordinates cartesianCoordinates = this.cartesianCoordinates.readFrom(rset);
        if(cartesianCoordinates == null){
            return null;
        }else{
            return new Location(cartesianCoordinates);
        }
        
    }
    
	/**
	 * forward equals to cartesianCoordinates equals in order to check if both locations have the same coordinates.
	 */
	//
    @Override
    public boolean equals(Object o){
        if(o != null && o.getClass() == this.getClass() ){
			Location temp = (Location) o;
            return this.cartesianCoordinates.isEqual(temp.getCoordinate());
        }else{
            throw new IllegalArgumentException();
        }
    }
    @Override
    public int hashCode() {
        return cartesianCoordinates.hashCode();
    }

    
}
