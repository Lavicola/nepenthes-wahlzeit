package org.wahlzeit.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NepenthesPhotoManager extends PhotoManager {

	public static NepenthesPhotoManager instance = new NepenthesPhotoManager();
	// According to the UML the Photomanager is in relation with 0 or * photos therefore the need a a list/hashmap to store them.
	public ArrayList<NepenthesPhoto> nepenthesPhotos = new ArrayList<>();



	private NepenthesPhotoManager(){
	}

	public static NepenthesPhotoManager getInstance() {
		return instance;
	}
















}