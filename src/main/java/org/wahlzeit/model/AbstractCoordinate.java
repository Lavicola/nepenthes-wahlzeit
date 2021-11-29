package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class AbstractCoordinate implements Coordinate {

    public final static int PRECISION = 5;
    public final static double EPSILON = Math.pow(10, -PRECISION);
    public final static double ROUND_POSITION = Math.pow(10, PRECISION);


    @Override
    public abstract CartesianCoordinate asCartesianCoordinate() throws ArithmeticException;
    @Override
    public abstract SphericCoordinate asSphericCoordinate() throws ArithmeticException;


    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        // Precondition: argument shall not be null
        assertNotNull(coordinate);

        // Converting both coordinates to cartesian we can gurantee/restore class invariant since CartesianDistance can only be calculated with  cartesian Cooordiantes
        CartesianCoordinate coordinate1 = this.asCartesianCoordinate();
        CartesianCoordinate coordinate2 = coordinate.asCartesianCoordinate();

        double x_delta = Math.pow(coordinate2.getX() - coordinate1.getX(), 2);
        double y_delta = Math.pow(coordinate2.getY() - coordinate1.getY(), 2);
        double z_delta = Math.pow(coordinate2.getZ() - coordinate1.getZ(), 2);

        return Math.sqrt(x_delta + y_delta + z_delta);

    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        // Calculated using: https://en.wikipedia.org/wiki/Great-circle_distance --> https://wikimedia.org/api/rest_v1/media/math/render/svg/87cea288a5b6e80757bc81375c3b6a38a30a5184
        // Precondition: argument shall not be null
        assertNotNull(coordinate);
        // With converting both coordinates to Spheric first we gurantee/restore class invariants since Central Angle can only get calculated with SphericCoordinates.
        SphericCoordinate coordinate1 = this.asSphericCoordinate();
        SphericCoordinate coordinate2 = coordinate.asSphericCoordinate();
        double phi1 = coordinate1.getLongitude();
        double phi2 = coordinate2.getLongitude();
        double theta1 = coordinate1.getLatitude();
        double thehta2 = coordinate2.getLatitude();
        double theta_delta = theta1 - thehta2;

        double first_term = Math.pow(
                Math.cos(phi2) *
                        Math.sin(theta_delta),
                2);

        double second_term = Math.pow(Math.cos(phi1) * Math.sin(phi2) -
                        Math.sin(phi1) * Math.cos(phi2) * Math.cos(theta_delta),
                2);
        double third_term = Math.sin(phi1) * Math.sin(phi2) + Math.cos(phi1) * Math.cos(phi2) * Math.cos(theta_delta);


        return Math.sqrt((first_term + second_term)) / third_term;
    }

    @Override
    public int hashCode() {
        CartesianCoordinate coordinate = this.asCartesianCoordinate();
        // Since EPSILON does allow inaccuracy we can´t use the full double value to hash.
        return Objects.hash(Math.round(coordinate.getX() * ROUND_POSITION), Math.round(coordinate.getY() * ROUND_POSITION), Math.round(coordinate.getZ() * ROUND_POSITION));
    }

    @Override
    public boolean equals(Object o) {
        assertNotNull(o);
        if (o instanceof Coordinate) {
            return this.isEqual((Coordinate) o);
        } else {
            throw new IllegalArgumentException();
        }
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


    static void assertNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("object was null");
        }
        return;
    }


}
