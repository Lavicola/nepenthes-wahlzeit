package org.wahlzeit.model;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class SphericCoordinateTest {
    private SphericCoordinate sphericCoordinates0;
    private SphericCoordinate sphericCoordinates1;
    private SphericCoordinate sphericCoordinates2;
    private SphericCoordinate sphericCoordinatesNegative;
    private SphericCoordinate sphericCoordinatesNegative2;
    // CartesianCoordinates0 == sphericCoordinates0 and so on
    private CartesianCoordinates cartesianCoordinates0;
    private CartesianCoordinates cartesianCoordinates1;
    private CartesianCoordinates cartesianCoordinates2;
    private CartesianCoordinates cartesianCoordinatesNegative;
    private CartesianCoordinates cartesianCoordinatesNegative2;

    @Before
    public void setUp() {
        sphericCoordinates0 = new SphericCoordinate(60, 50, 5);
        cartesianCoordinates0 = new CartesianCoordinates(-1.470659472, 0.3998732106, 4.82483014246);
        sphericCoordinates1 = new SphericCoordinate(80, 90.0, 10);
        cartesianCoordinates1 = new CartesianCoordinates(4.453352832, -8.885331406, -1.103872438);
/*
        sphericCoordinates1 = new SphericCoordinate(1.0, 1.0, 1.0);
        sphericCoordinates2 = new SphericCoordinate(1.0, 0.0, 0.0);
        sphericCoordinatesNegative = new SphericCoordinate(-1.0, -2.0, -3.0);
        sphericCoordinatesNegative2 = new SphericCoordinate(-2.0, -2.0, -3.0);
*/
    }


    @Test
    public void sphericCoordinatesToCartesianCoordinates(){
        assertEquals(true, cartesianCoordinates0.equals(sphericCoordinates0));
        assertEquals(true, cartesianCoordinates1.equals(sphericCoordinates1));

    }





}