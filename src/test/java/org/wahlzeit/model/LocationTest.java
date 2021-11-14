package org.wahlzeit.model;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LocationTest {
    
    private Location location0;
	private Location location1;
	private Location locationNegative;
    private Location locationNegative2;

    @Before
	public void setUp() {
		location0 = new Location(new Coordinate(0.0, 0.0, 0.0));
		location1 = new Location(new Coordinate(1.0, 1.0, 1.0));

        locationNegative = new Location(new Coordinate(-1.0, -2.0, -3.0));
        locationNegative2 = new Location(new Coordinate(-2.0, -2.0, -3.0));

}
/**
 * The test is essentially the same as in CoordinateTest.
 */
@Test
public void testEquals() {
    assertEquals(true, location1.equals(new Location(new Coordinate(1.0,1.0,1.0))));
    assertEquals(false,location1.equals(location0));
    
    assertEquals(true, locationNegative.equals(new Location(new Coordinate(-1.0, -2.0, -3.0))));
    assertEquals(false, locationNegative.equals(locationNegative2));

    assertEquals(false, locationNegative.equals(new Location(new Coordinate(1.0, 2.0, 3.0))));

}

@Test(expected = IllegalArgumentException.class)
public void testNullArgument() {
    location0.equals(null);
}

}
