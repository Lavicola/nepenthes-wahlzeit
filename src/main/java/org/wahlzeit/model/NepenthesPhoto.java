package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;


public class NepenthesPhoto extends Photo {

	private Date timeTaken;
	public NepenthesPhotoManager nepenthesPhotoManager = NepenthesPhotoManager.getInstance();
	protected Nepenthes nepenthes = null;

	/**
	 * 
	 */
	public NepenthesPhoto() {
        super();
		nepenthesPhotoManager.nepenthesPhotos.add(this);
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
        nepenthes.setName(rset.getString("name"));
        nepenthes.setAltitude(rset.getInt("altitude"));
        nepenthes.setHybrid(rset.getBoolean("isHybrid"));

	}
	
    public void writeOn(ResultSet rset) throws SQLException {
		assertNotNull(rset);
		super.writeOn(rset);
        rset.updateString("name", nepenthes.getName());
		rset.updateInt("altitude", nepenthes.getAltitude());
		rset.updateBoolean("isHybrid", nepenthes.isHybrid());
	}

	public Nepenthes getNepenthes() {
		return nepenthes;
	}

	public void setNepenthes(Nepenthes nepenthes) {
		this.nepenthes = nepenthes;
	}

	public Date getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Date timeTaken) {
		this.timeTaken = timeTaken;
	}


}
