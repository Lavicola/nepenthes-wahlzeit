package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;

public class NepenthesManager extends ObjectManager{

    //in order to distinguish the objects in the hashmap
    public int counter = 0;
    private static NepenthesManager instance;
    private HashMap<String,NepenthesType> nepenthesTypes = new HashMap<>();
    private HashMap<Integer,Nepenthes> nepenthes = new HashMap<>();


    public static NepenthesManager getInstance() {
        if(instance == null) {
            instance = new NepenthesManager();
        }
        return instance;
    }

    private NepenthesManager() {

    }



    public void doAddSubType(NepenthesType nepenthesSuperType, NepenthesType nepenthesSubType){
        nepenthesSuperType.addSubType(nepenthesSubType);
    }




    public void createNepenthesType(String typeName){
        assert(typeName != null) : "typename is null";
        assert(nepenthesTypes.containsKey(typeName) == false) : "Type already exists";
        if(!nepenthesTypes.containsKey(typeName)){
            nepenthesTypes.put(typeName, new NepenthesType(typeName));
        }
    }

    //create empty Nepenthes and later on set the values
    public Nepenthes createNepenthes(String typeName) {
        assertIsValidNepenthesTypeName(typeName);
        //it fails if the typeName does not exist, the TypeName must be created before
        NepenthesType nepenthesType = getNepenthesType(typeName);
        Nepenthes result = nepenthesType.createInstance(counter++);
        nepenthes.put(result.getId(), result);
        return result;
    }
    //for unit testing it is more comfortable to just call one method
    public Nepenthes createNepenthes(String typeName,String name,int altitude,boolean isHybrid) {
        Nepenthes nepenthes = createNepenthes(typeName);
        nepenthes.setHybrid(isHybrid);
        nepenthes.setAltitude(altitude);
        nepenthes.setName(name);
        return nepenthes;
    }






    public NepenthesType getNepenthesType(String typeName){
        assertIsValidNepenthesTypeName(typeName);
        return nepenthesTypes.get(typeName);
    }








    public void assertIsValidNepenthesTypeName(String typeName){
        assert(typeName != null) : "typename is null";
        assert(nepenthesTypes.containsKey(typeName) ) : "Typename does not exist";
    }

    /**
     * @param rset
     */
    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        return null;
    }
}