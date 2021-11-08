package org.wahlzeit.model;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CoordinateTest {
    
    private Coordinate coordinate0;
	private Coordinate coordinate1;
	private Coordinate coordinate2;
	private Coordinate coordinateNegative;
    private Coordinate coordinateNegative2;

    private static double delta = 0;

    @Before
	public void setUp() {
		coordinate0 = new Coordinate(0.0, 0.0, 0.0);
		coordinate1 = new Coordinate(1.0, 1.0, 1.0);
		coordinate2 = new Coordinate(1.0, 0.0, 0.0);

        coordinateNegative = new Coordinate(-1.0, -2.0, -3.0);
        coordinateNegative2 = new Coordinate(-2.0, -2.0, -3.0);

}

@Test
public void testDistance() {
    assertEquals(1.0, coordinate0.getDistance(coordinate2), delta);
    assertEquals(Math.sqrt(17.0), coordinate0.getDistance(coordinateNegative2), delta);
    assertEquals(0, coordinateNegative2.getDistance(coordinateNegative2), delta);
}

@Test
public void testEquals() {
    assertEquals(true, coordinate1.equals(new Coordinate(1.0,1.0,1.0)));
    assertEquals(false, coordinate1.equals(coordinate0));
    
    assertEquals(true, coordinateNegative.equals(new Coordinate(-1.0, -2.0, -3.0)));
    assertEquals(false, coordinateNegative.equals(coordinateNegative2));
    assertEquals(false, coordinateNegative.equals(new Coordinate(1.0, 2.0, 3.0)));
    
}

@Test(expected = IllegalArgumentException.class)
public void testNullArgument() {
    coordinate0.equals(null);
}

}