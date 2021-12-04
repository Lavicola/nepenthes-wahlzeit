package org.wahlzeit.model;

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
        return this.asCartesianCoordinate().getCartesianDistance(coordinate);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        // Calculated using: https://en.wikipedia.org/wiki/Great-circle_distance --> https://wikimedia.org/api/rest_v1/media/math/render/svg/87cea288a5b6e80757bc81375c3b6a38a30a5184
        // Precondition: argument shall not be null
        assertNotNull(coordinate);
        // With converting both coordinates to Spheric first we gurantee/restore class invariants since Central Angle can only get calculated with SphericCoordinates.
        return this.asSphericCoordinate().getCentralAngle(coordinate);
    }

    @Override
    public int hashCode() {
        return this.asCartesianCoordinate().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        assertNotNull(o);
        if (o instanceof Coordinate) {
            return this.asCartesianCoordinate().isEqual((Coordinate) o);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        assertNotNull(coordinate);
        return this.asCartesianCoordinate().isEqual(coordinate);
    }


    static void assertNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("object was null");
        }
    }


}
