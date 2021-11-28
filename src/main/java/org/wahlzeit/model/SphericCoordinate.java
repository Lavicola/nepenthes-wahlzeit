package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {

    protected double phi;
    protected double theta;
    protected double radius;


    /**
     * @param radius value can´t be negative
     * @param phi    value must be between 0 and 360
     * @param theta  value must be between 0 and 180
     */
    public SphericCoordinate(double radius, double phi, double theta) {
        // The Angle Unit is in Radian
        assertCodomain(radius, phi, theta);
        this.radius = radius;
        this.phi = phi;
        this.theta = theta;
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() throws ArithmeticException {
        double x, y, z;
        x = radius * Math.sin(theta) * Math.cos(phi);
        y = radius * Math.sin(theta) * Math.sin(phi);
        z = radius * Math.cos(theta);
        return new CartesianCoordinate(x, y, z);
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


    public void assertCodomain(double radius, double phi, double theta) {
        assertPhiCodomain(phi);
        assertRadiusCodomain(radius);
        assertThetaCodomain(theta);
    }

    public void assertPhiCodomain(double phi) {
        if (phi < 0 || phi > 360) {
            throw new IllegalArgumentException("Phi has to be between 0° and 360°");
        }
    }

    public void assertThetaCodomain(double theta) {
        if (theta < 0 || theta > 180) {
            throw new IllegalArgumentException("Thetha has to be between 0° and 180°");
        }
    }

    public void assertRadiusCodomain(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cant be negative");
        }
    }


}
