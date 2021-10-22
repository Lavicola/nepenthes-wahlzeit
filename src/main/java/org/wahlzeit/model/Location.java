package org.wahlzeit.model;


public class Location {
    public Coordinate coordinate = null;

    /*
     Constructor if Multiplicity is 0
    */
    public Location(){

    }

    /*
     Constructor if Multiplicity is 1
    */
    public Location(Coordinate coordinate){
        this.coordinate = coordinate;
    }


}
