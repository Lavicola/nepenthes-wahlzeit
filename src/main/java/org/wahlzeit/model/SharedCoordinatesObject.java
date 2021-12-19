package org.wahlzeit.model;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class SharedCoordinatesObject {

    private static SharedCoordinatesObject instance;

    private static int NUMBER_OF_SUPPORTED_COORDINATES = 2;
    private HashMap<Integer, Coordinate[] > coordinates_value_objects = new HashMap<>();


    private SharedCoordinatesObject(){}

    public static SharedCoordinatesObject getInstance(){
        if(instance == null){
            instance = new SharedCoordinatesObject();
        }
        return instance;
    }

    /**
     * This Method returns an instance of a Cartesian Coordinate Object.
     * It the object itself is not already stored in the Hashmap it will store the object and will return the new stored object
     * @param coordinate The Coordinate Object which should be checked if it already exists or not
     * @return
     */
    public CartesianCoordinate getCoordinateInstance(CartesianCoordinate coordinate){
        // Combining the storing method with the return method guarantees that an error will never occur since if the object is not already in hashmap we add it before we return the instance
        // it also saves the trouble of calling two methods (add and get) in the coordinate classes.
        addCoordinate(coordinate);

        return coordinates_value_objects.get(coordinate.hashCode())[coordinate.getArrayIndex()].asCartesianCoordinate();
    }

    /**
     * This Method returns an instance of a Spheric Coordinate Object.
     * It the object itself is not already stored in the Hashmap it will store the object and will return the new stored object
     * @param coordinate The Coordinate Object which should be checked if it already exists or not
     * @return
     */
    public SphericCoordinate getCoordinateInstance(SphericCoordinate coordinate){
        // Combining the storing method with the return method guarantees that an error will never occur since if the object is not already in hashmap we add it before we return the instance
        // it also saves the trouble of calling two methods (add and get) in the coordinate classes.
        addCoordinate(coordinate);

        return coordinates_value_objects.get(coordinate.hashCode())[coordinate.getArrayIndex()].asSphericCoordinate();
    }


    private Coordinate doGetCoordinateInstance(Coordinate coordinate){
        return coordinates_value_objects.get(coordinate.hashCode())[coordinate.getArrayIndex()];
    }

    /**
     * This Method adds a Coordinate Object to our Hashmap if it does not exist. In the end it will return the object stored in the hashmap
     */
    public void addCoordinate(Coordinate coordinate){
        // if entry does not exist add a new entry since there is a brand new object which must be stored
        if(! checkIfCoordinateEntryExists(coordinate)){
            doAddCoordinateEntry(coordinate);
        }
        // Coordinate exists already
        if(! checkIfCoordinateExists(coordinate)){
            doAddCoordinate(coordinate);
        }
    }

    private void doAddCoordinate(Coordinate coordinate){
        if(checkIfCoordinateEntryExists(coordinate)){
            coordinates_value_objects.get(coordinate.hashCode())[coordinate.getArrayIndex()] = coordinate;
        }
    }

    private void doAddCoordinateEntry(Coordinate coordinate){
        if(! checkIfCoordinateEntryExists(coordinate)){
            coordinates_value_objects.put(coordinate.hashCode(), new Coordinate[NUMBER_OF_SUPPORTED_COORDINATES]);
        }

    }

    private boolean checkIfCoordinateExists(Coordinate coordinate){
        if(checkIfCoordinateEntryExists(coordinate)){
            if(coordinates_value_objects.get(coordinate.hashCode())[coordinate.getArrayIndex()] != null){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfCoordinateEntryExists(Coordinate coordinate){
        return coordinates_value_objects.containsKey(coordinate.hashCode());
    }



}
