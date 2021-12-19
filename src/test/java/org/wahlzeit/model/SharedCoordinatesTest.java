package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SharedCoordinatesTest {


    // test if the objects are truly equals (=reference)
    @Test
    public void addCoordinateTest(){
        assertEquals(CartesianCoordinate.getCartesianCoordinate(1, 1, 1),CartesianCoordinate.getCartesianCoordinate(1, 1, 1));
        assertNotEquals(CartesianCoordinate.getCartesianCoordinate(1, 1, 1),CartesianCoordinate.getCartesianCoordinate(1, 1, 1.5));
        assertEquals(SphericCoordinate.getSphericCoordinate(5.196152422706632, 0.7853981633974483, 0.9553166181245093),SphericCoordinate.getSphericCoordinate(5.196152422706632, 0.7853981633974483, 0.9553166181245093));
        assertNotEquals(SphericCoordinate.getSphericCoordinate(5.196152422706632, 0.7853981633974483, 0.9553166181245093),SphericCoordinate.getSphericCoordinate(5.186152422706632, 0.7853981633974483, 0.9553166181245093));
    }

}
