package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SphericCoordinate extends AbstractCoordinate {

    protected final double longitude;
    protected final double latitude;
    protected final double radius;


    /**
     * @param radius value can´t be negative
     * @param longitude    value must be between 0 and 360
     * @param latitude  value must be between 0 and 180
     */
    public SphericCoordinate(double radius, double longitude, double latitude) {
        assertLongitudeCodomain(longitude);
        assertRadiusCodomain(radius);
        assertLongitudeCodomain(latitude);
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() throws ArithmeticException {
        // Precondition: SphericCoordinate must be valid
        assertClassInvariants();
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
        //Precondition: Object shall be not Null and must be an instance of Coordiante/CartesianCoordiante or SphericCoordinate
        assertIsExceptedObject(coordinate);
        assertNotNull(coordinate);
        SphericCoordinate coordinate1 = this.asSphericCoordinate();
        SphericCoordinate coordinate2 = coordinate.asSphericCoordinate();
        // after converting to spheric coordinate we check Classinvariants
        coordinate1.assertClassInvariants();
        coordinate2.assertClassInvariants();

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
        SphericCoordinate sphericCoordinate = CartesianCoordinate.readFrom(resultSet).asSphericCoordinate();
        // check if the object can be described as SphericCoordiante
        sphericCoordinate.assertClassInvariants();
        return sphericCoordinate;
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        this.asCartesianCoordinate().writeOn(resultSet);
    }

    /**
     * radius value can´t be negative
     * longitude    value must be between 0 and 360
     * latitude  value must be between 0 and 180
     */
    protected void assertClassInvariants() {
        assertLongitudeCodomain(this.getLongitude());
        assertRadiusCodomain(this.getRadius());
        assertLatitudeCodomain(this.getLatitude());
    }

    private void assertLongitudeCodomain(double longitude) {
        if (longitude < 0 || longitude > 360) {
            throw new IllegalArgumentException("Longitude has to be between 0° and 360°");
        }
    }

    private void assertLatitudeCodomain(double latitude) {
        if (latitude < 0 || latitude > 180) {
            throw new IllegalArgumentException("Thetha has to be between 0° and 180°");
        }
    }

    private void assertRadiusCodomain(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cant be negative");
        }
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


    public double getRadius() {
        return radius;
    }

}
