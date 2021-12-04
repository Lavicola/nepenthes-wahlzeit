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
        assertIsExceptedObject(coordinate);
        return this.asCartesianCoordinate().getCartesianDistance(coordinate);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        //Precondition: Object shall be not Null and must be an instance of Coordiante/CartesianCoordiante or SphericCoordinate
        assertNotNull(coordinate);
        assertIsExceptedObject(coordinate);
        return this.asSphericCoordinate().getCentralAngle(coordinate);
    }

    @Override
    public int hashCode() {
        return this.asCartesianCoordinate().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        //Precondition: Object shall be not Null and must be an instance of Coordiante/CartesianCoordiante or SphericCoordinate
        assertNotNull(o);
        assertIsExceptedObject(o);
        return this.asCartesianCoordinate().isEqual((Coordinate) o);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        assertNotNull(coordinate);
        assertIsExceptedObject(coordinate);
        return this.asCartesianCoordinate().isEqual(coordinate);
    }

    @Override
    public void assertNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("object was null");
        }
    }

    @Override
    public void assertIsExceptedObject(Object o) {
        if (o instanceof Coordinate) {
            return;
        }
        throw new IllegalArgumentException("Object must be Coordinate, SphericCoordinate or CartesianCoordinate");
    }

    protected abstract void assertClassInvariants();
}
