package org.wahlzeit.model;

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
