package org.wahlzeit.model;

import static org.junit.Assert.assertTrue;
import org.junit.Test;



public class NepenthesFactoryTest {
    


    @Test
    public void UseNepenthesPhotoFactoryAsDefault() {    
        PhotoFactory factory = PhotoFactory.getInstance();
        //if this one fails the factory won´t use the methods of NepenthesPhotoFactory  
        assertTrue(factory instanceof NepenthesPhotoFactory);
    }
    

}
