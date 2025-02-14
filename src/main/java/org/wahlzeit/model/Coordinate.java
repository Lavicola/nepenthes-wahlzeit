package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Coordinate {

    CartesianCoordinate asCartesianCoordinate() throws ArithmeticException;

    double getCartesianDistance(Coordinate coordinate) throws InvalidCoordinateException;

    SphericCoordinate asSphericCoordinate() throws ArithmeticException;

    double getCentralAngle(Coordinate coordinate) throws InvalidCoordinateException;

    boolean isEqual(Coordinate coordinate);

    boolean isSame(Object o);

    /**
     * Adding readFrom and writeOn here makes it possible to from SphericCoordinate as well
     * But in the database only the Cartesian values will be stored.
     * That means in the readFrom Method the Coordiantes will be Converted to the corresponding class
     */
    static Coordinate readFrom(ResultSet resultSet) throws SQLException {
        return null;
    }

    void writeOn(ResultSet resultSet) throws SQLException;

    void assertNotNull(Object o);

    void assertIsExpectedObject(Object o);

    //check if the object space is valid
    private void assertClassInvariants() {

    }

    // returns the array index which is used for the sharedCoordinateObject see report for more information
    enum ARRAY_INDEX{
        CARTESIAN(0),
        SPHERIC(1);

        ARRAY_INDEX(int i) {
        }
    }
    // every sublcass needs to implement this in order to get the right index for the shared coordinates.
    int getArrayIndex();



}
