package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SphericCoordinate extends AbstractCoordinate {

    protected final double longitude;
    protected final double latitude;
    protected final double radius;


    /**
     * @param radius    value can´t be negative
     * @param longitude value must be between 0 and 360
     * @param latitude  value must be between 0 and 180
     */
    private SphericCoordinate(double radius, double longitude, double latitude) {
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
        // you could argue if it would be better to make the check as a precondition (see report)
        assertClassInvariants();
    }

    //calling the constructor results always in a new object therefore the object must be created via a static in order to be able to actually return an already existing Object
    public static SphericCoordinate getSphericCoordinate(double radius, double longitude, double latitude){
        //add to shared_coordinate list and return the instance
        return shared_coordinates.getCoordinateInstance(new SphericCoordinate(radius, longitude, latitude));
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() throws ArithmeticException {
        // Precondition: SphericCoordinate must be valid. Since only a valid SphericCoordinate can be created we don´t have to test it here again
        double x, y, z;
        x = radius * Math.sin(latitude) * Math.cos(longitude);
        y = radius * Math.sin(latitude) * Math.sin(longitude);
        z = radius * Math.cos(latitude);
        //assertClassInvariants is  checked in the constructor
        return shared_coordinates.getCoordinateInstance(CartesianCoordinate.getCartesianCoordinate(x, y, z)) ;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() throws ArithmeticException {
        return this;
    }


    @Override
    public double getCentralAngle(Coordinate coordinate) throws InvalidCoordinateException {
        try{
            //Precondition: Object shall be not Null and must be an instance of Coordiante/CartesianCoordiante or SphericCoordinate
            assertIsExpectedObject(coordinate);
            assertNotNull(coordinate);
            SphericCoordinate coordinate2 = coordinate.asSphericCoordinate();
            double phi1 = this.getLongitude();
            double phi2 = coordinate2.getLongitude();
            double theta1 = this.getLatitude();
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

            double result = Math.sqrt((first_term + second_term)) / third_term;
            // Postcondition between 0 and 360 (0 and 2 * pi)
            assertValidCentralAngle(result);
            return result;

        }catch (ArithmeticException | NullPointerException exception){
            throw new InvalidCoordinateException(exception.getMessage());
        }
    }

    private void assertValidCentralAngle(double result) {
        if( !(result > 0 && result <= 360) ){
            throw new IllegalArgumentException("central Angle must be bigger than 0 and smaller than 360 ");
        }
    }


    public static Coordinate readFrom(ResultSet resultSet) throws SQLException {
        // since the values are stored as Cartesian we must use the Cartestian function to the values of ot the database
        SphericCoordinate sphericCoordinate = CartesianCoordinate.readFrom(resultSet).asSphericCoordinate();
        //assertClassInvariants is  checked in the constructor
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
        assertLongitudeCodomain();
        assertRadiusCodomain();
        assertLatitudeCodomain();
    }

    private void assertLongitudeCodomain() {
        if (this.longitude < 0 || this.longitude > 360) {
            throw new IllegalArgumentException("Longitude has to be between 0° and 360°");
        }
        if (Double.isNaN(this.longitude)) {
            throw new IllegalArgumentException("Longitude Point is NaN");
        }
        if (!Double.isFinite(this.longitude)) {
            throw new IllegalArgumentException("Longitude Point is Infinitive");
        }

    }

    private void assertLatitudeCodomain() {
        if (this.latitude < 0 || this.latitude > 180) {
            throw new IllegalArgumentException("latitude has to be between 0° and 180°");
        }
        if (Double.isNaN(this.latitude)) {
            throw new IllegalArgumentException("latitude Point is NaN");
        }
        if (!Double.isFinite(this.latitude)) {
            throw new IllegalArgumentException("latitude Point is Infinitive");
        }

    }

    private void assertRadiusCodomain() {
        if (this.radius < 0) {
            throw new IllegalArgumentException("Radius cant be negative");
        }
        if (Double.isNaN(this.radius)) {
            throw new IllegalArgumentException("radius is NaN");
        }
        if (!Double.isFinite(this.radius)) {
            throw new IllegalArgumentException("radius is Infinitive");
        }

    }

    @Override
    public int getArrayIndex() {
        return ARRAY_INDEX.SPHERIC.ordinal();
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
