package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NepenthesPhoto extends Photo {

    private String name;
    private int altitude;
    private boolean isHybrid;


	/**
	 * 
	 */
	public NepenthesPhoto() {
        super();
	}
	
	/**
	 * 
	 * @methodtype constructor
	 */
	public NepenthesPhoto(PhotoId myId) {
		super(myId);
	}
	
    	/**
	 * 
	 * @methodtype constructor
	 */
	public NepenthesPhoto(ResultSet rset) throws SQLException {
        readFrom(rset);
	}

	/**
	 * 
	 * @methodtype constructor
	 */
	public NepenthesPhoto(ResultSet rset,Location location) throws SQLException {
		super(rset,location);
		readFrom(rset);
		assertClassInvariant();
	}

    public void readFrom(ResultSet rset) throws SQLException {
		assertNotNull(rset);
		super.readFrom(rset);
        name = rset.getString("name");
        altitude = rset.getInt("altitude");
        isHybrid = rset.getBoolean("isHybrid");

	}
	
    public void writeOn(ResultSet rset) throws SQLException {
		assertNotNull(rset);
		super.writeOn(rset);
        rset.updateString("name", name);
		rset.updateInt("altitude", altitude);
		rset.updateBoolean("isHybrid", isHybrid);

	}
	/**
	 * 
	 * @methodtype get
	 */
    public String getName(){
        return this.name;
    }

    /**
	 * 
	 * @methodtype get
	 */
    public int getAltitude(){
        return this.altitude;
    }
    /**
	 * 
	 * @methodtype get
	 */
    public boolean isHybrid(){
        return this.isHybrid;
    }

	/**
	 * 
	 * @methodtype set
	 */
    public void setName(String name){
        this.name=name;
    }
    
	/**
	 * 
	 * @methodtype set
	 */
    public void setAltitude(int altitude){
        this.altitude = altitude;
		assertClassInvariant();
    }

	/**
	 * 
	 * @methodtype set
	 */
    public void setisHybrid(boolean isHybrid){
        this.isHybrid = isHybrid;
    }


	@Override
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
