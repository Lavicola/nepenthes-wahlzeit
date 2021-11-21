package org.wahlzeit.model;

public interface Coordiante {

     CartesianCoordinates asCartesianCoordinate ();

     double getCartesianDistance(Coordiante coordiante);

     SphericCoordinate asSphericCoordinate ();

     double getCentralAngle(Coordiante coordiante);

     boolean isEqual(Coordiante coordiante);

}
