package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SphericCoordinate implements Coordinate {

    protected double phi;
    protected double theta;
    protected double radius;

    public SphericCoordinate(double radius, double theta, double phi) {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        double x, y, z;
        x = radius * Math.sin(phi) * Math.cos(theta);
        y = radius * Math.sin(phi) * Math.sin(theta);
        z = radius * Math.cos(phi);
        return new CartesianCoordinate(x, y, z);
    }

    //ϕ phi    || θ teta
    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return this.asCartesianCoordinate().getCartesianDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(phi, theta, radius);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        assertNotNull(coordinate);
        return calculateCentralAngle(coordinate.asSphericCoordinate());
    }

    private double calculateCentralAngle(SphericCoordinate c) {
        //calculated using https://wikimedia.org/api/rest_v1/media/math/render/svg/87cea288a5b6e80757bc81375c3b6a38a30a5184 formular
        double phi1 = this.getPhi();
        double phi2 = c.getPhi();
        double theta1 = this.getTheta();
        double thehta2 = c.getTheta();
        double phi_delta = phi1 - phi2;
        double theta_delta = theta1 - thehta2;
        double first_term = Math.pow(
                Math.cos(phi2) * Math.sin(theta_delta),
                2);
        double second_term = Math.pow(
                Math.cos(phi1) * Math.sin(phi2)
                        - (Math.sin(phi1) * Math.cos(phi2) * Math.cos(theta_delta))
                , 2);
        double third_term = Math.sin(phi1) * Math.sin(phi2) + Math.cos(phi1) * Math.cos(phi2) * Math.cos(theta_delta);

        return Math.atan((Math.sqrt(first_term + second_term)) / third_term);

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
        return this.asCartesianCoordinate().isEqual(coordinate.asCartesianCoordinate());
    }

    @Override
    public Coordinate readFrom(ResultSet resultSet) throws SQLException {
        return this.asCartesianCoordinate().readFrom(resultSet).asSphericCoordinate();
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        this.asCartesianCoordinate().writeOn(resultSet);

    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    static void assertNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("object was null");
        }
        return;
    }
}
