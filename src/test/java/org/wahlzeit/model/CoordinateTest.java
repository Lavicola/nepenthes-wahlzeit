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
    // SphericCoordinate Angels are in Radian
    private SphericCoordinate sphericCoordinates0;
    private SphericCoordinate sphericCoordinates1;
    private SphericCoordinate sphericCoordinates2;
    private CartesianCoordinate cartesianCoordinate0;
    private CartesianCoordinate cartesianCoordinate1;
    private CartesianCoordinate cartesianCoordinate2;
    private final double delta = CartesianCoordinate.EPSILON;

    @Mock
    private ResultSet resultSet;


    @Before
    public void setUp() {
        //equal
        sphericCoordinates0 = new SphericCoordinate(5, 50, 60);
        cartesianCoordinate0 = new CartesianCoordinate(-1.470659472, 0.3998732106, -4.762064902);
        //equal
        cartesianCoordinate1 = new CartesianCoordinate(4.453352832, -8.885331406, -1.103872438);
        sphericCoordinates1 = new SphericCoordinate(80, 90.0, 10);
        //equal
        sphericCoordinates2 = new SphericCoordinate(-5, -50, -80);
        cartesianCoordinate2 = new CartesianCoordinate(-4.795343936, -1.303856951, 0.5519362192);

        try {
            Mockito.when(resultSet.getDouble("coordinate_x")).thenReturn(5.0);
            Mockito.when(resultSet.getDouble("coordinate_y")).thenReturn(5.0);
            Mockito.when(resultSet.getDouble("coordinate_z")).thenReturn(5.0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //this test checks  both: if equal and if the transformation from spheric -> cartesian works
    @Test
    public void sphericCoordinatesToCartesianCoordinatesTest() {
        assertEquals(true, cartesianCoordinate0.equals(sphericCoordinates0));
        assertEquals(false, cartesianCoordinate1.equals(sphericCoordinates0));
        assertEquals(true, cartesianCoordinate2.equals(sphericCoordinates2));

    }

    //this test checks both: if equal and if the transformation from cartesian -> spheric works
    @Test
    public void cartesianCoordinatesToSphericCoordinatesTest() {
        assertEquals(true, sphericCoordinates0.equals(cartesianCoordinate0));
        assertEquals(false, sphericCoordinates1.equals(cartesianCoordinate0));
        assertEquals(true, sphericCoordinates2.equals(cartesianCoordinate2));
    }

    @Test
    public void getCentralAngle() {
        assertEquals(1.1323356352216298, cartesianCoordinate0.getCentralAngle(cartesianCoordinate1), delta);
        assertEquals(-1.4427519829447186, sphericCoordinates0.getCentralAngle(cartesianCoordinate1), delta);
    }

    @Test
    public void getCartesianDistanceTest() {
        CartesianCoordinate coordinate0 = new CartesianCoordinate(0.0, 0.0, 0.0);
        CartesianCoordinate coordinate1 = new CartesianCoordinate(1.0, 0.0, 0.0);
        SphericCoordinate s_coordinate0 = coordinate0.asSphericCoordinate();

        assertEquals(1.0, coordinate0.getCartesianDistance(coordinate1), delta);
        assertEquals(1.0, s_coordinate0.getCartesianDistance(coordinate1), delta);


    }

    @Test
    public void testReadFrom() throws SQLException {
        CartesianCoordinate coordinate = cartesianCoordinate0.readFrom(resultSet).asCartesianCoordinate();
        assertEquals(5, coordinate.getX(),0);
        assertEquals(5, coordinate.getY(),0);
        assertEquals(5, coordinate.getZ(),0);
    }

    @Test
    public void testWriteOn() throws SQLException {
        cartesianCoordinate0.writeOn(resultSet);
        verify(resultSet, times(1)).updateDouble(eq("coordinate_x"), anyDouble());
        verify(resultSet, times(1)).updateDouble(eq("coordinate_y"), anyDouble());
        verify(resultSet, times(1)).updateDouble(eq("coordinate_z"), anyDouble());
    }





}