package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Nepenthes {

    private String name;
    private int altitude;
    private boolean isHybrid;
    private Location location;
    public NepenthesManager nepenthesManager = NepenthesManager.getInstance();
    private NepenthesType nepenthesType;

    // since we don´t want to create the same object twice, we use the static Method zu create a new Object or get the reference from the NepenthesManager, other values will be set via Setters.
    private Nepenthes(String name, NepenthesType nepenthesType){
    this.name = name;
    this.nepenthesType = nepenthesType;
    assertClassInvariant();
    }


    // this function must be static in order to be able to check if a Nepenthes already exists
    public synchronized static Nepenthes getNepenthes(String name, NepenthesType nepenthesType){
        Nepenthes nepenthes = NepenthesManager.getInstance().getNepenthes(name);
        // Nepenthes does not exist, create it and add it to the manager list
        if(nepenthes == null){
            nepenthes = new Nepenthes(name, nepenthesType);
            NepenthesManager.getInstance().addNepenthes(nepenthes);
        }
        return nepenthes;
    }



    public void readFrom(ResultSet rset) throws SQLException {
        this.setName(rset.getString("name"));
        this.setAltitude(rset.getInt("altitude"));
        this.setHybrid(rset.getBoolean("isHybrid"));
        if(location != null){
            this.location.readFrom(rset);
        }
    }

    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateString("name", this.getName());
        rset.updateInt("altitude", this.getAltitude());
        rset.updateBoolean("isHybrid", this.isHybrid());
        if(location != null){
            this.writeOn(rset);
        }
    }


    public NepenthesType getType(){
        return nepenthesType;
    }

    public void setType(NepenthesType nepenthesType){
        this.nepenthesType = nepenthesType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public boolean isHybrid() {
        return isHybrid;
    }

    public void setHybrid(boolean hybrid) {
        isHybrid = hybrid;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    protected void assertClassInvariant(){
        if(this.altitude < 0){
            throw new IllegalArgumentException("altitude can´t be zero");
        }
        if(this.name.isBlank() || this.name.isEmpty()){
            throw new IllegalArgumentException("name can´t be empty or blank!");
        }
        return;
    }




}
