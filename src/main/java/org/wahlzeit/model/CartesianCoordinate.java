package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
        SphericCoordinate sphericCoordinate = new SphericCoordinate(radius, phi, theta);
        //check if the Cartesian Coordiante can be displayed as spheric coordiante. (Redundant since it will be checked in the constructor anyway but just for demonstration I did add it.
        sphericCoordinate.assertClassInvariants();
        return sphericCoordinate;
    }


    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        // Precondition: argument shall not be null and must be of type Coordinate or subtype
        assertNotNull(coordinate);
        assertIsExceptedObject(coordinate);

        CartesianCoordinate coordinate1 = this.asCartesianCoordinate();
        CartesianCoordinate coordinate2 = coordinate.asCartesianCoordinate();

        double x_delta = Math.pow(coordinate2.getX() - coordinate1.getX(), 2);
        double y_delta = Math.pow(coordinate2.getY() - coordinate1.getY(), 2);
        double z_delta = Math.pow(coordinate2.getZ() - coordinate1.getZ(), 2);

        return Math.sqrt(x_delta + y_delta + z_delta);

    }


    @Override
    public int hashCode() {
        CartesianCoordinate coordinate = this.asCartesianCoordinate();
        // Since EPSILON does allow inaccuracy we can´t use the full double value to hash.
        return Objects.hash(Math.round(coordinate.getX() * ROUND_POSITION), Math.round(coordinate.getY() * ROUND_POSITION), Math.round(coordinate.getZ() * ROUND_POSITION));
    }

    @Override
    public boolean equals(Object o) {
        // Precondition: object must be not null and must be of type Coordiante or subtype
        assertNotNull(o);
        assertIsExceptedObject(o);

        return this.isEqual((Coordinate) o);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        assertNotNull(coordinate);

        CartesianCoordinate coordinate1 = coordinate.asCartesianCoordinate();
        CartesianCoordinate coordinate2 = this.asCartesianCoordinate();

        boolean isXEqual = checkEqualDouble(coordinate1.getX(), coordinate2.getX());
        boolean isYEqual = checkEqualDouble(coordinate1.getY(), coordinate2.getY());
        boolean isZEqual = checkEqualDouble(coordinate1.getZ(), coordinate2.getZ());


        if (isXEqual && isYEqual && isZEqual) {
            return true;
        }
        return false;
    }

    private boolean checkEqualDouble(double d1, double d2) {
        return EPSILON > Math.abs(d1 - d2);
    }






    /**
     * The class itself knows it structure best, therefore read and write will be defined here and not in the abstract class
     * @param resultSet
     */
    public static Coordinate readFrom(ResultSet resultSet) throws SQLException {
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
     * Since every number is valid in a cartesian coordinatesystem there is nothing to test here
     */
    @Override
    protected void assertClassInvariants() {

        return;
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
