package org.wahlzeit.model;


public class Location {
    protected Coordinate coordinate = null;

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
}
