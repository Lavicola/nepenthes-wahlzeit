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
public class CartesianCoordinate extends AbstractCoordinate {
    //immutable since it does not make sense to change the coordiantes later on.
    private final double x;
    private final double y;
    private final double z;


    /**
     * @methodtype constructor
     */
    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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