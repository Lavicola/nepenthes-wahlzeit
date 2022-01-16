package org.wahlzeit.model;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class NepenthesTypeTest {

    NepenthesManager nepenthesManager;
    // hl = highland
    Nepenthes hL_nepenthes1;
    //uhl = ultra hl
    Nepenthes uhL_nepenthes1;
    //ll = lowland
    Nepenthes ll_nepenthes1;
    //ull = ultra ll
    Nepenthes ull_nepenthes1;

    @Before
    public void setUp() {
        nepenthesManager = NepenthesManager.getInstance();
        nepenthesManager.createNepenthesType("highland");
        nepenthesManager.createNepenthesType("lowland");
        nepenthesManager.createNepenthesType("ultra_highland");
        nepenthesManager.createNepenthesType("ultra_lowland");
        //
        uhL_nepenthes1 = nepenthesManager.createNepenthes("ultra_highland","lamii",2800,false);
        hL_nepenthes1 = nepenthesManager.createNepenthes("highland","villosa",2000,false);
        //
        ull_nepenthes1 = nepenthesManager.createNepenthes("ultra_lowland","treubiana",300,false);
        ll_nepenthes1 = nepenthesManager.createNepenthes("lowland","northiana",800,false);
        // superType: ultraHighland and ultraLowland  subtype: highland,lowland
        nepenthesManager.doAddSubType(uhL_nepenthes1.getType(),hL_nepenthes1.getType());
        nepenthesManager.doAddSubType(ull_nepenthes1.getType(),ll_nepenthes1.getType());
    }

    @Test
    public void isSubType(){
        assertTrue(hL_nepenthes1.getType().isSubtype(uhL_nepenthes1.getType()));
        assertTrue(ll_nepenthes1.getType().isSubtype(ull_nepenthes1.getType()));
        assertFalse(ull_nepenthes1.getType().isSubtype(ll_nepenthes1.getType()));
        assertFalse(hL_nepenthes1.getType().isSubtype(ull_nepenthes1.getType()));
    }

    @Test
    public void isSuperType(){
        assertTrue(uhL_nepenthes1.getType().isSuperType(hL_nepenthes1.getType()));
        assertTrue(ull_nepenthes1.getType().isSuperType(ll_nepenthes1.getType()));
        assertFalse(ull_nepenthes1.getType().isSuperType(uhL_nepenthes1.getType()));
        assertFalse(ll_nepenthes1.getType().isSuperType(ull_nepenthes1.getType()));

    }

    @Test
    public void duplicateNepenthesTypeTest(){
        //no exception if the key exists we just ignore it
        nepenthesManager.createNepenthesType("intermediate");
        nepenthesManager.createNepenthesType("intermediate");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNepenthesWithoutExistingTypeTest(){
        nepenthesManager.createNepenthes("intermediate");
    }



    @Test
    public void hasInstanceTest(){
        assertTrue(uhL_nepenthes1.getType().hasInstance(hL_nepenthes1));
        assertTrue(ull_nepenthes1.getType().hasInstance(ll_nepenthes1));
        assertFalse(ll_nepenthes1.getType().hasInstance(ull_nepenthes1));
    }




}
