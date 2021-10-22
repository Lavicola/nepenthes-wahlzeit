package org.wahlzeit.model;

/**
 * A Location represents 3 Dimensional space where a photo was taken.
 */
public class Location {
    public Coordinate coordinate = null;

	/**
	 * 
	 * @methodtype constructor
	 */
    public Location(){

    }

	/**
	 * 
	 * @methodtype constructor
	 */
    public Location(Coordinate coordinate){
        this.coordinate = coordinate;
    }


}
