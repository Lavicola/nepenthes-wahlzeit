package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SphericCoordinate extends AbstractCoordinate {

    protected double longitude;
    protected double latitude;
    protected double radius;


    /**
     * @param radius value can´t be negative
     * @param longitude    value must be between 0 and 360
     * @param latitude  value must be between 0 and 180
     */
    public SphericCoordinate(double radius, double longitude, double latitude) {
        // The Angle Unit is in Radian
        assertCodomain(radius, longitude, latitude);
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() throws ArithmeticException {
        double x, y, z;
        x = radius * Math.sin(latitude) * Math.cos(longitude);
        y = radius * Math.sin(latitude) * Math.sin(longitude);
        z = radius * Math.cos(latitude);
        return new CartesianCoordinate(x, y, z);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() throws ArithmeticException {
        return this;
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



    public static Coordinate readFrom(ResultSet resultSet) throws SQLException {
        // since the values are stored as Cartesian we must use the Cartestian function to the values of ot the database
        return CartesianCoordinate.readFrom(resultSet).asSphericCoordinate();
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        this.asCartesianCoordinate().writeOn(resultSet);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void assertCodomain(double radius, double longitude, double latitude) {
        assertLongitudeCodomain(longitude);
        assertRadiusCodomain(radius);
        assertLatitudeCodomain(latitude);
    }

    public void assertLongitudeCodomain(double longitude) {
        if (longitude < 0 || longitude > 360) {
            throw new IllegalArgumentException("Longitude has to be between 0° and 360°");
        }
    }

    public void assertLatitudeCodomain(double latitude) {
        if (latitude < 0 || latitude > 180) {
            throw new IllegalArgumentException("Thetha has to be between 0° and 180°");
        }
    }

    public void assertRadiusCodomain(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cant be negative");
        }
    }


}
