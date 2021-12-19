package org.wahlzeit.model;

import java.util.HashMap;


/***
 * This class stores every created Object in a Hashmap.
 * It is implemented as a Singleton and used from Cartesian and Spheric in order to  store every created Object in one global Hashmap
 */

public class SharedCoordinates {

    private static SharedCoordinates instance;

    // increase values in case of a new coordinate system
    private static int NUMBER_OF_SUPPORTED_COORDINATES = 2;
    // the shared global hashmap for cartesian and spheric
    private HashMap<Integer, Coordinate[] > coordinates_value_objects = new HashMap<>();

    //don´t allow to create an instance
    private SharedCoordinates(){}

    public static SharedCoordinates getInstance(){
        if(instance == null){
            instance = new SharedCoordinates();
        }
        return instance;
    }

    /**
     * This Method returns an instance of a Cartesian Coordinate Object.
     * It the object itself is not already stored in the Hashmap it will store the object and will return the new stored object
     * @param coordinate The Coordinate Object which should be stored if necessary and returned at the end
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
     * @param coordinate The Coordinate Object which should be stored if necessary and returned at the end
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
