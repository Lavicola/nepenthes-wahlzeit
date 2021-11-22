package org.wahlzeit.model;


public class NepenthesPhotoManager extends PhotoManager {

	public static NepenthesPhotoManager instance = new NepenthesPhotoManager();

	private NepenthesPhotoManager(){
	}

	public static NepenthesPhotoManager getInstance() {
		return instance;
	}


}