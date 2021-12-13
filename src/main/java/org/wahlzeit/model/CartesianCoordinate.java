package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartesianCoordinate extends AbstractCoordinate {

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
    public CartesianCoordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
        // you could argue if it would be better to make the check as a precondition (see report)
        assertClassInvariants();
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
        // Class invariants will be checked in the constructor
        return new SphericCoordinate(radius, phi, theta);
    }


    @Override
    public double getCartesianDistance(Coordinate coordinate) throws InvalidCoordinateException {
        try{
            // Precondition: argument shall not be null and must be of type Coordinate or subtype
            assertNotNull(coordinate);
            assertIsExpectedObject(coordinate);
            CartesianCoordinate coordinate2 = coordinate.asCartesianCoordinate();

            double x_delta = Math.pow(coordinate2.getX() - this.getX(), 2);
            double y_delta = Math.pow(coordinate2.getY() - this.getY(), 2);
            double z_delta = Math.pow(coordinate2.getZ() - this.getZ(), 2);

            // You could check for postcondition, but i decided to not check it see report why.
            return Math.sqrt(x_delta + y_delta + z_delta);
        }catch (ArithmeticException | NullPointerException exception){
            throw new InvalidCoordinateException(exception.getMessage());
        }

    }

    @Override
    public int hashCode()  {
        //Classinvariants is checked in both constructors, therefore it is not necessary to check it here again.
        CartesianCoordinate coordinate = this.asCartesianCoordinate();
        return Objects.hash(Math.round(coordinate.getX() * ROUND_POSITION), Math.round(coordinate.getY() * ROUND_POSITION), Math.round(coordinate.getZ() * ROUND_POSITION));
        // Since EPSILON does allow inaccuracy we can´t use the full double value to hash.
    }

    @Override
    public boolean equals(Object o) {
        // Precondition: object must be not null and must be of type Coordiante or subtype
        assertNotNull(o);
        assertIsExpectedObject(o);

        return this.isEqual((Coordinate) o);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        CartesianCoordinate coordinate1;
        try{
            assertNotNull(coordinate);
            coordinate1 = coordinate.asCartesianCoordinate();
        }catch (NullPointerException | IllegalArgumentException exception){
            // if the Argument Coordinate is Null or can´t be converted to Cartesian they can´t be equal
            return false;
        }

        //Classinvariants is checked in both constructors, therefore it is not necessary to check it here again.

        boolean isXEqual = checkEqualDouble(coordinate1.getX(), this.getX());
        boolean isYEqual = checkEqualDouble(coordinate1.getY(), this.getY());
        boolean isZEqual = checkEqualDouble(coordinate1.getZ(), this.getZ());

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
     *
     * @param resultSet
     */
    public static Coordinate readFrom(ResultSet resultSet) throws SQLException {
        // If we could not retrieve the values we will try it at max three times
        for (int tries = 0; tries < 3; tries++) {
            try {
                double x = resultSet.getDouble(COLUMN_X);
                if (!resultSet.wasNull()) {
                    return new CartesianCoordinate(x, resultSet.getDouble(COLUMN_Y), resultSet.getDouble(COLUMN_Z));
                }
            } catch (SQLException e) {
                //time.sleep would make sense here, but I think that would be exaggerated for our purpose
            }
        }
        throw new SQLException("Could not retrieve Coordinates out of database!");

    }
    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        assertNotNull(resultSet);
        CartesianCoordinate coordinate = this.asCartesianCoordinate();
        // try writing values three times else give up
        // instead try catch every written value I combined them even if it mean some values might get written multiple times
        for (int tries = 0; tries < 3; tries++) {
            try {
                resultSet.updateDouble(COLUMN_X, coordinate.getX());
                resultSet.updateDouble(COLUMN_Y, coordinate.getY());
                resultSet.updateDouble(COLUMN_Z, coordinate.getZ());
                //if no error occurred we can leave
                return;
            } catch (SQLException e) {
                //time.sleep would make sense here, but I think that would be exaggerated for our purpose
            }
        }
        throw new SQLException("Could not Write values in Database");
    }

    // Since a double can be NaN or Infinitive we must check if thta´s the case if we convert coordinates to cartesian Coordinate
    @Override
    protected void assertClassInvariants() {
        assertCoordinatePointCoDomain(this.x);
        assertCoordinatePointCoDomain(this.y);
        assertCoordinatePointCoDomain(this.z);
        return;
    }

    private void assertCoordinatePointCoDomain(double coordinate_point) {
        if (Double.isNaN(coordinate_point)) {
            throw new IllegalArgumentException("coordinate Point is NaN");
        }
        if (!Double.isFinite(coordinate_point)) {
            throw new IllegalArgumentException("coordinate Point  Infinitive");
        }

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
