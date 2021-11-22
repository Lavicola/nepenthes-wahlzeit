package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Coordinate {

    CartesianCoordinate asCartesianCoordinate();

    double getCartesianDistance(Coordinate coordinate);

    SphericCoordinate asSphericCoordinate();

    double getCentralAngle(Coordinate coordinate);

    boolean isEqual(Coordinate coordinate);

    /**
     * Adding readFrom and writeOn here makes it possible to from SphericCoordinate as well
     * But in the database only the Cartesian values will be stored.
     * That means in the readFrom Method the Coordiantes will be Converted to the corresponding class
     */
     Coordinate readFrom(ResultSet resultSet) throws SQLException;

     void writeOn(ResultSet resultSet) throws SQLException;

}
