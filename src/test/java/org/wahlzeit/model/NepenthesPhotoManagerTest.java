package org.wahlzeit.model;

import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class NepenthesPhotoManagerTest {
    
    @Test
    public void UseNepenthesPhotoManagerAsDefault() {    
        PhotoManager manager = PhotoManager.getInstance();
        //if this one fails the photomanger won´t use the methods of NepenthesPhotoManager  
        assertTrue(manager instanceof NepenthesPhotoManager);
    }
    

}
