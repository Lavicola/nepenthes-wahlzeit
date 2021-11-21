package org.wahlzeit.model;

public class SphericCoordinate implements Coordiante {

    protected double phi;
    protected double theta;
    protected double radius;

    public SphericCoordinate(double phi, double theta, double radius) {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }


    @Override
    public CartesianCoordinates asCartesianCoordinate() {
        double x,y,z;
        x = radius * Math.sin(theta) * Math.cos(phi);
        y = radius * Math.sin(theta) * Math.sin(phi);
        z = radius * Math.cos(theta);
        return new CartesianCoordinates(x,y,z);
    }
//ϕ phi    || θ teta
    @Override
    public double getCartesianDistance(Coordiante coordiante) {
        return this.asCartesianCoordinate().getCartesianDistance(coordiante.asCartesianCoordinate());
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(phi, theta, radius);
    }

    @Override
    public double getCentralAngle(Coordiante coordinate) {
        CartesianCoordinates coordiantes1 = this.asCartesianCoordinate();
        CartesianCoordinates coordinates2 = coordinate.asCartesianCoordinate();
        double vector = coordiantes1.getX()*coordinates2.getX() + coordiantes1.getY()*coordinates2.getY() + coordiantes1.getZ()*coordinates2.getZ();
        double absolute1 = getAbsoluteValue(coordiantes1);
        double absolute2 = getAbsoluteValue(coordinates2);
        if(absolute1 == 0 || absolute2 == 0){
            //throw new Exception("angle undefined");
            return 0;
        }
        double angle = Math.acos((vector)/(absolute1*absolute2));
        return angle;
    }

    //helper function
    private double getAbsoluteValue(Coordiante coordinate){
        CartesianCoordinates coordinates = coordinate.asCartesianCoordinate();
        double absolute_v = Math.sqrt(Math.pow(coordinates.getX(),2) +
                Math.pow(coordinates.getY(),2) +
                Math.pow(coordinates.getZ(),2));
        return absolute_v;
    }



    @Override
    public boolean isEqual(Coordiante coordiante) {
        return this.asCartesianCoordinate().isEqual(coordiante.asCartesianCoordinate());
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
}
