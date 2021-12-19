package org.wahlzeit.model;

import org.junit.Test;

public class SharedObjectTest {


    @Test
    public void addCoordinateTest(){

        CartesianCoordinate cartesian = new CartesianCoordinate(1, 1, 1);
        SharedCoordinatesObject test = SharedCoordinatesObject.getInstance();

        test.addCoordinate(cartesian);

        Coordinate coordinate = test.getCoordinateInstance(cartesian);

        if(coordinate == cartesian){

        }



    }

}
