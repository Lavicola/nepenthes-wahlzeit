package org.wahlzeit.model;

import static java.lang.Double.NaN;
import static org.junit.Assert.*;
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
    // SphericCoordinate Angels are in Radian
    private SphericCoordinate sphericCoordinates0;
    private SphericCoordinate sphericCoordinates1;
    private CartesianCoordinate cartesianCoordinate0;
    private CartesianCoordinate cartesianCoordinate1;

    @Mock
    private ResultSet resultSet;


    @Before
    public void setUp() {
        //equal
        cartesianCoordinate0 = CartesianCoordinate.getCartesianCoordinate(3, 3, 3);
        sphericCoordinates0 = SphericCoordinate.getSphericCoordinate(5.196152422706632, 0.7853981633974483, 0.9553166181245093);
        //equal
        sphericCoordinates1 = SphericCoordinate.getSphericCoordinate(10, 0.3, 0.8);
        cartesianCoordinate1 = CartesianCoordinate.getCartesianCoordinate(6.853164493328191, 2.1199322023239766, 6.967067093471654);


        try {
            Mockito.when(resultSet.getDouble("coordinate_x")).thenReturn(5.0);
            Mockito.when(resultSet.getDouble("coordinate_y")).thenReturn(5.0);
            Mockito.when(resultSet.getDouble("coordinate_z")).thenReturn(5.0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //check if the transformation spheric --> cartesian works
    @Test
    public void asCartesianCoordinateTest() {
        CartesianCoordinate coordinate0 = sphericCoordinates0.asCartesianCoordinate();
        CartesianCoordinate coordinate1 = sphericCoordinates1.asCartesianCoordinate();

        assertEquals(cartesianCoordinate0.getX(), coordinate0.getX(), AbstractCoordinate.EPSILON);
        assertEquals(cartesianCoordinate0.getY(), coordinate0.getY(), AbstractCoordinate.EPSILON);
        assertEquals(cartesianCoordinate0.getZ(), coordinate0.getZ(), AbstractCoordinate.EPSILON);

        assertEquals(cartesianCoordinate1.getX(), coordinate1.getX(), AbstractCoordinate.EPSILON);
        assertEquals(cartesianCoordinate1.getY(), coordinate1.getY(), AbstractCoordinate.EPSILON);
        assertEquals(cartesianCoordinate1.getZ(), coordinate1.getZ(), AbstractCoordinate.EPSILON);
        return;
    }


    //check if the transformation cartesian --> spheric works
    @Test
    public void asSphericCoordinateTest() {
        SphericCoordinate coordinate0 = cartesianCoordinate0.asSphericCoordinate();
        SphericCoordinate coordinate1 = cartesianCoordinate1.asSphericCoordinate();

        assertEquals(sphericCoordinates0.getRadius(), coordinate0.getRadius(), AbstractCoordinate.EPSILON);
        assertEquals(sphericCoordinates0.getLongitude(), coordinate0.getLongitude(), AbstractCoordinate.EPSILON);
        assertEquals(sphericCoordinates0.getLatitude(), coordinate0.getLatitude(), AbstractCoordinate.EPSILON);

        assertEquals(sphericCoordinates1.getRadius(), coordinate1.getRadius(), AbstractCoordinate.EPSILON);
        assertEquals(sphericCoordinates1.getLongitude(), coordinate1.getLongitude(), AbstractCoordinate.EPSILON);
        assertEquals(sphericCoordinates1.getLatitude(), coordinate1.getLatitude(), AbstractCoordinate.EPSILON);

    }

    @Test
    public void equalsTest() {
        assertEquals(true, cartesianCoordinate0.equals(sphericCoordinates0));
        assertEquals(false, cartesianCoordinate0.equals(sphericCoordinates1));
        assertEquals(true, sphericCoordinates1.equals(cartesianCoordinate1));
        assertEquals(false, sphericCoordinates1.equals(sphericCoordinates0));
    }

    @Test
    public void testHashCode() {
        CartesianCoordinate co = CartesianCoordinate.getCartesianCoordinate(1, 2, 32);

        assertEquals(cartesianCoordinate0.hashCode(), sphericCoordinates0.hashCode());
        assertEquals(cartesianCoordinate1.hashCode(), sphericCoordinates1.hashCode());
    }

    @Test
    public void getCartesianDistanceTest() throws InvalidCoordinateException {
        assertEquals(0, sphericCoordinates0.getCartesianDistance(cartesianCoordinate0), AbstractCoordinate.EPSILON);
        assertEquals(10, sphericCoordinates1.getCartesianDistance(CartesianCoordinate.getCartesianCoordinate(0, 0, 0)), AbstractCoordinate.EPSILON);
    }

    @Test
    public void getCentralAngle() throws InvalidCoordinateException {
        assertEquals(0.5496167631033911, cartesianCoordinate0.getCentralAngle(cartesianCoordinate1), AbstractCoordinate.EPSILON);
    }

    @Test
    public void testReadFrom() throws SQLException {
        CartesianCoordinate coordinate = CartesianCoordinate.readFrom(resultSet).asCartesianCoordinate();
        assertEquals(5, coordinate.getX(), 0);
        assertEquals(5, coordinate.getY(), 0);
        assertEquals(5, coordinate.getZ(), 0);
    }

    @Test
    public void testWriteOn() throws SQLException {
        cartesianCoordinate0.writeOn(resultSet);
        sphericCoordinates0.writeOn(resultSet);
        verify(resultSet, times(2)).updateDouble(eq(CartesianCoordinate.COLUMN_X), anyDouble());
        verify(resultSet, times(2)).updateDouble(eq(CartesianCoordinate.COLUMN_Y), anyDouble());
        verify(resultSet, times(2)).updateDouble(eq(CartesianCoordinate.COLUMN_Z), anyDouble());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArgument() {
        cartesianCoordinate0.equals(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidSphericCoordinateTest() {
        SphericCoordinate.getSphericCoordinate(-5, 1, 2);
        SphericCoordinate.getSphericCoordinate(5, 4000, 2);
        SphericCoordinate.getSphericCoordinate(NaN, 200, 2);
        SphericCoordinate.getSphericCoordinate(1.0 / 0, 200, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidCartesianCoordinateTest() {
        CartesianCoordinate.getCartesianCoordinate(1, 1, NaN);
        CartesianCoordinate.getCartesianCoordinate(1, 1, 1.0 / 0);
    }

    //test the isSame Method
    @Test
    public void isSameTest() {
        assertTrue(cartesianCoordinate0.isSame(sphericCoordinates0));
        assertTrue(sphericCoordinates0.isSame(cartesianCoordinate0));
        assertTrue(sphericCoordinates1.isSame(sphericCoordinates1));
        assertFalse(sphericCoordinates1.isSame(sphericCoordinates0));
    }


}
