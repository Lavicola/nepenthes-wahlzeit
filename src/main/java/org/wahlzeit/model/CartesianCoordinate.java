/*
 * SPDX-FileCopyrightText: 2021 David Schmidt https://github.com/Lavicola
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


/**
 * Cartesian coordinates .
 */
public class CartesianCoordinate implements Coordinate {
    //immutable since it does not make sense to change the coordiantes later on.
    private final double x;
    private final double y;
    private final double z;
    public final static double EPSILON = 1e-5;
    public final static double EPSILON_CUT = 1e5;


    /**
     * @methodtype constructor
     */
    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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


    /* direct Cartesian distance.
      Formular: root( (x2 - x1)² + (y2 - y1)² + (z2 - z1)² )
   */
    public double getCartesianDistance(Coordinate coordinate) {
        assertNotNull(coordinate);
        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();

        double x_delta = Math.pow(cartesianCoordinate.getX() - this.getX(), 2);
        double y_delta = Math.pow(cartesianCoordinate.getY() - this.getY(), 2);
        double z_delta = Math.pow(cartesianCoordinate.getZ() - this.getZ(), 2);

        return Math.sqrt(x_delta + y_delta + z_delta);
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        resultSet.updateDouble("coordinate_x", this.getX());
        resultSet.updateDouble("coordinate_y", this.getY());
        resultSet.updateDouble("coordinate_z", this.getZ());
    }

    @Override
    public Coordinate readFrom(ResultSet resultSet) throws SQLException {
        double x = resultSet.getDouble("coordinate_x");
        if (!resultSet.wasNull()) {
            return new CartesianCoordinate(x, resultSet.getDouble("coordinate_y"), resultSet.getDouble("coordinate_z"));
        }
        return null;
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

    /**
     * Check if two Coordinates are equal.
     */
    public boolean isEqual(CartesianCoordinate cartesianCoordinate) {
        assertNotNull(cartesianCoordinate);
        boolean isXEqual = checkEqualDouble(cartesianCoordinate.getX(), this.getX());
        boolean isYEqual = checkEqualDouble(cartesianCoordinate.getY(), this.getY());
        boolean isZEqual = checkEqualDouble(cartesianCoordinate.getZ(), this.getZ());

        if (isXEqual && isYEqual && isZEqual) {
            return true;
        }
        return false;
    }

    private boolean checkEqualDouble(double d1, double d2) {
        return EPSILON > Math.abs(d1 - d2);
    }


    @Override
    public int hashCode() {
        //Since equals only look at the EPSILION places behind the comma, we need to make sure that our hash only looks EPSILION_CUT behind our comma
        return Objects.hash(cutdecimal(x), cutdecimal(y), cutdecimal(z));
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        // The Angle Unit is in Radian
        double radius = Math.sqrt(Math.pow(x, 2) +
                Math.pow(y, 2) +
                Math.pow(z, 2));
        if (radius == 0) {
            throw new IllegalArgumentException("There is no SphericCoordiante since the radius is 0");
        }
        double theta = Math.acos(z / radius);
        double phi = CalculatePhi(x, y);
        return new SphericCoordinate(radius, theta, phi);
    }


    //helper function which return Phi for a given x and y
    public double CalculatePhi(double x, double y) {
        double phi = 0;
        if(x == 0){
            throw new IllegalArgumentException("There is no valid Coordiante if x is 0");
        }

        if (x > 0 && y>=0) {
            phi = Math.atan((y / x));
        }
        if(x > 0 && y < 0){
            phi = Math.atan((y / x)) + 2 * Math.PI;
        }
        if(x < 0 ){
            phi = Math.atan((y / x)) + Math.PI;
        }

        return phi;
    }


    @Override
    public double getCentralAngle(Coordinate coordinate) {
        return this.asSphericCoordinate().getCentralAngle(coordinate);

    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        assertNotNull(coordinate);
        return this.isEqual(coordinate.asCartesianCoordinate());
    }

    static void assertNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("object was null");
        }
        return;
    }


    private static double cutdecimal(double number) {
        return Math.floor(number * EPSILON_CUT) / EPSILON_CUT;
    }


}


