package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;

public class NepenthesManager extends ObjectManager{

    private static NepenthesManager instance;
    // just like shown in the UML the Manager keep track of every Nepenthes and NepentheyTyp
    protected HashMap<String,NepenthesType> nepenthesTypes = new HashMap<>();
    // every Nepenthes Name is unique and therefore we can use the name as key
    protected HashMap<String,Nepenthes> nepenthes = new HashMap<>();


    public static NepenthesManager getInstance() {
        if(instance == null) {
            instance = new NepenthesManager();
        }
        return instance;
    }

    private NepenthesManager() {

    }

    public void createNepenthesType(String typeName){
        assert(typeName != null) : "Arguement is null";
        //if the type already exists we won´t throw an exception since it makes no sense in my opinion
        if(!nepenthesTypes.containsKey(typeName)){
            nepenthesTypes.put(typeName, new NepenthesType(typeName));
        }
    }

    public void doAddSubType(NepenthesType nepenthesSuperType, NepenthesType nepenthesSubType){
        nepenthesSuperType.addSubType(nepenthesSubType);
    }

    //create a Nepenthes with the minimum of necessary values
    public Nepenthes createNepenthes(String typeName, String name) {
        //it fails if the typeName does not exist, the TypeName must be created before
        NepenthesType nepenthesType = getNepenthesType(typeName);
        Nepenthes result = nepenthesType.createInstance(name,nepenthesType);
        nepenthes.put(result.getName(), result);
        return result;
    }
    //for unit testing it is more comfortable to just call one method
    public Nepenthes createNepenthes(String typeName,String name,int altitude,boolean isHybrid) {
        Nepenthes nepenthes = createNepenthes(typeName,name);
        nepenthes.setHybrid(isHybrid);
        nepenthes.setAltitude(altitude);
        nepenthes.setName(name);
        return nepenthes;
    }

    public NepenthesType getNepenthesType(String typeName){
        assertIsValidNepenthesTypeName(typeName);
        return nepenthesTypes.get(typeName);
    }

    public void addNepenthes(Nepenthes nepenthes){
        if(!nepenthesExists(nepenthes.getName())){
            this.nepenthes.put(nepenthes.getName(), nepenthes);
        }
    }


    public Nepenthes getNepenthes(String name){
        if(nepenthesExists(name)){
            return nepenthes.get(name);
        }
        return null;
    }

    public boolean nepenthesExists(String name){
        if(nepenthes.containsKey(name)){
            return true;
        }
        return false;
    }

    public void assertIsValidNepenthesTypeName(String typeName){
        if(typeName == null){
            throw new IllegalArgumentException("Arguement is Null");
        }
        if(!nepenthesTypes.containsKey(typeName)){
            throw new IllegalArgumentException("Key does not exists");
        }
    }

    /**
     * @param rset
     */
    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        return null;
    }
}