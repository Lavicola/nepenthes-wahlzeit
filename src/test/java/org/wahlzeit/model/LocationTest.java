package org.wahlzeit.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class LocationTest {
    
    private Location location0;
	private Location location1;
	private Location locationNegative;
    private Location locationNegative2;

    @Before
	public void setUp() {
		location0 = new Location(CartesianCoordinate.getCartesianCoordinate(0.0, 0.0, 0.0));
		location1 = new Location(CartesianCoordinate.getCartesianCoordinate(1.0, 1.0, 1.0));

        locationNegative = new Location(CartesianCoordinate.getCartesianCoordinate(-1.0, -2.0, -3.0));
        locationNegative2 = new Location(CartesianCoordinate.getCartesianCoordinate(-2.0, -2.0, -3.0));

}
/**
 * The test is essentially the same as in CartesianCoordinatesTest.
 */
@Test
public void testEquals() {
    assertEquals(true, location1.equals(new Location(CartesianCoordinate.getCartesianCoordinate(1.0,1.0,1.0))));
    assertEquals(false,location1.equals(location0));
    
    assertEquals(true, locationNegative.equals(new Location(CartesianCoordinate.getCartesianCoordinate(-1.0, -2.0, -3.0))));
    assertEquals(false, locationNegative.equals(locationNegative2));

    assertEquals(false, locationNegative.equals(new Location(CartesianCoordinate.getCartesianCoordinate(1.0, 2.0, 3.0))));

}
@Test
public void testHashCode(){
    Location location = new Location(CartesianCoordinate.getCartesianCoordinate(0.0, 0.0, 0.0));
    assertEquals(location.hashCode(),location0.hashCode());
    assertNotEquals(location.hashCode(),location1.hashCode());
}


@Test(expected = IllegalArgumentException.class)
public void testNullArgument() {
    location0.equals(null);
}

}
