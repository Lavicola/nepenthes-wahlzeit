package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

public class Nepenthes {

    private String name;
    private int altitude;
    private boolean isHybrid;
    private Location location;
    public NepenthesManager nepenthesManager = NepenthesManager.getInstance();
    private NepenthesType nepenthesType;

    // since we don´t want to create the same object twice, we use the static Method zu create a new Object or get the reference from the NepenthesManager
    private Nepenthes(String name, NepenthesType nepenthesType){
    this.name = name;
    this.nepenthesType = nepenthesType;
    assertClassInvariant();
    }



    public static Nepenthes getNepenthes(String name, NepenthesType nepenthesType){
        Nepenthes nepenthes = NepenthesManager.getInstance().getNepenthes(name);
        if(nepenthes == null){
            nepenthes = new Nepenthes(name, nepenthesType);
        }
        return nepenthes;
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
