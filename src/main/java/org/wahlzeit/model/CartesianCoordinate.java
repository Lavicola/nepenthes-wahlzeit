package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartesianCoordinate extends AbstractCoordinate{

    //immutable since it does not make sense to change the coordiantes later on.
    private final double x;
    private final double y;
    private final double z;
    //database columns
    public final static String COLUMN_X = "coordinate_x";
    public final static String COLUMN_Y = "coordinate_y";
    public final static String COLUMN_Z = "coordinate_z";


    /**
     * @methodtype constructor
     */
    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() throws ArithmeticException {
        return this;
    }


    @Override
    public SphericCoordinate asSphericCoordinate() throws ArithmeticException {
        // The Angle Unit is in Radian
        double radius = Math.sqrt((Math.pow(x, 2) +
                Math.pow(y, 2) +
                Math.pow(z, 2)));
        if (radius <= EPSILON) {
            return new SphericCoordinate(0, 0, 0);
        }
        double phi = Math.atan2(y, x);
        double theta = Math.acos(z / radius);
        return new SphericCoordinate(radius, phi, theta);
    }



    /**
     * The class itself knows it structure best, therefore read and write will be defined here and not in the abstract class
     * @param resultSet
     */
    public static Coordinate readFrom(ResultSet resultSet) throws SQLException {
        assertNotNull(resultSet);
        double x = resultSet.getDouble(COLUMN_X);
        if (!resultSet.wasNull()) {
            return new CartesianCoordinate(x, resultSet.getDouble(COLUMN_Y), resultSet.getDouble(COLUMN_Z));
        }
        return null;
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        assertNotNull(resultSet);
        CartesianCoordinate coordinate = this.asCartesianCoordinate();
        resultSet.updateDouble(COLUMN_X, coordinate.getX());
        resultSet.updateDouble(COLUMN_Y, coordinate.getY());
        resultSet.updateDouble(COLUMN_Z, coordinate.getZ());
    }



    /**
         * @methodtype get
         */
    public double getX() {
        return this.x;
    }

    /**
     * @methodtype get
     */
    public double getY() {
        return this.y;
    }

    /**
     * @methodtype get
     */
    public double getZ() {
        return this.z;
    }

}
