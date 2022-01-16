package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

public class Nepenthes {
    //in order for the Manager to be able to distinguish different Nepenthes objects and store these in it´s hashmap we need a ID which will be set in the NepenthesManager
    int id = 0;
    public NepenthesManager nepenthesManager = NepenthesManager.getInstance();
    private NepenthesType nepenthesType;
    private Location location;
    private String name;
    private int altitude;
    private boolean isHybrid;


    public Nepenthes(int id, NepenthesType nepenthesType){
    this.id = id;
    this.nepenthesType = nepenthesType;
    }



    public int getId(){
        return this.id;
    }


    public Nepenthes(NepenthesType nepenthesType,String name, int altitude,boolean isHybrid){
        this.nepenthesType = nepenthesType;
        this.name = name;
        this.altitude = altitude;
        this.isHybrid = isHybrid;
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
