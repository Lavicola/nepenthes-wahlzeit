package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NepenthesPhotoManager extends PhotoManager {
    

	/**
	 * 
	 */
	protected static final PhotoManager instance = new NepenthesPhotoManager();


	/**
	 * 
	 */
	public NepenthesPhotoManager() {
		photoTagCollector = NepenthesPhotoFactory.getInstance().createPhotoTagCollector();
	}

	/**
	 * 
	 */
	protected Photo createObject(ResultSet rset) throws SQLException {
		return NepenthesPhotoFactory.getInstance().createPhoto(rset);
	}


}
