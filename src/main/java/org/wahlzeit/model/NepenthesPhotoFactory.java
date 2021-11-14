package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.services.SysLog;

public class NepenthesPhotoFactory extends PhotoFactory {

    private static NepenthesPhotoFactory instance = null;

	/**
	 * 
	 */
	protected NepenthesPhotoFactory() {
		// do nothing
	}

    /**
	 * Public singleton access method.
	 */
	public static synchronized NepenthesPhotoFactory getInstance() {
		if (instance == null) {
			SysLog.logSysInfo("setting NepenthesPhotoFactory");
			setInstance(new NepenthesPhotoFactory());
		}
		
		return instance;
	}

	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}

	/**
	 * @methodtype factory
	 */
	public Photo createPhoto() {
		return new NepenthesPhoto();
	}

	/**
	 * 
	 */
	public Photo createPhoto(PhotoId id) {
		return new NepenthesPhoto(id);
	}
	
	/**
	 * 
	 */
	public Photo createPhoto(ResultSet rs) throws SQLException {
		return new NepenthesPhoto(rs);
	}
	

    
}
