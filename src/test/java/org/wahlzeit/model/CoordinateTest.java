package org.wahlzeit.model;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CoordinateTest {
    
    private Coordinate coordinate0;
	private Coordinate coordinate1;
	private Coordinate coordinate2;
	private Coordinate coordinateNegative;
    private Coordinate coordinateNegative2;

    private static double delta = 0;
    @Mock
    public ResultSet resultSet;


    @Before
	public void setUp() {
		coordinate0 = new Coordinate(0.0, 0.0, 0.0);
		coordinate1 = new Coordinate(1.0, 1.0, 1.0);
		coordinate2 = new Coordinate(1.0, 0.0, 0.0);
        coordinateNegative = new Coordinate(-1.0, -2.0, -3.0);
        coordinateNegative2 = new Coordinate(-2.0, -2.0, -3.0);

        try {
            Mockito.when(resultSet.getDouble("coordinate_x")).thenReturn(5.0);
            Mockito.when(resultSet.getDouble("coordinate_y")).thenReturn(5.0);
            Mockito.when(resultSet.getDouble("coordinate_z")).thenReturn(5.0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        

}

@Test
public void testReadFrom() throws SQLException {
    Coordinate coordinate = coordinate0.readFrom(resultSet);
    assertEquals(5, coordinate.getX(),0);
    assertEquals(5, coordinate.getY(),0);
    assertEquals(5, coordinate.getZ(),0);

}

@Test
public void testWriteOn() throws SQLException {    
    coordinate0.writeOn(resultSet);
    verify(resultSet, times(1)).updateDouble(eq("coordinate_x"), anyDouble());
    verify(resultSet, times(1)).updateDouble(eq("coordinate_y"), anyDouble());
    verify(resultSet, times(1)).updateDouble(eq("coordinate_z"), anyDouble());   
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