/*
 * SPDX-FileCopyrightText: 2021 David Schmidt https://github.com/Lavicola
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Location {
    private CartesianCoordinate cartesianCoordinate = null;

	/**
	 * 
	 * @methodtype constructor
	 */
    public Location(CartesianCoordinate cartesianCoordinate){
        this.cartesianCoordinate = new CartesianCoordinate(cartesianCoordinate.getX(), cartesianCoordinate.getY(), cartesianCoordinate.getZ());
    }

	/**
	 * 
	 * @methodtype constructor
	 */
    public Location(double x,double y, double z){
        this.cartesianCoordinate = new CartesianCoordinate(x,y,z);
    }

	/**
	 * 
	 * @methodtype constructor
	 */
    public CartesianCoordinate getCoordinate(){
        return this.cartesianCoordinate;
    }


	public void writeOn(ResultSet rset) throws SQLException {
            cartesianCoordinate.writeOn(rset);
    }

	public Location readFrom(ResultSet rset) throws SQLException {
        Coordinate cartesianCoordinate = this.cartesianCoordinate.readFrom(rset);
        if(cartesianCoordinate == null){
            return null;
        }else{
            return new Location(cartesianCoordinate.asCartesianCoordinate());
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
            return this.cartesianCoordinate.isEqual(temp.getCoordinate());
        }else{
            throw new IllegalArgumentException();
        }
    }
    @Override
    public int hashCode() {
        return cartesianCoordinate.hashCode();
    }

    
}
