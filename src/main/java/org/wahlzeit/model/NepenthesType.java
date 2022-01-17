package org.wahlzeit.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NepenthesType {

    String name;
    protected NepenthesType superType = null;
    protected Set<NepenthesType> subTypes = new HashSet<NepenthesType>();
    public NepenthesManager nepenthesManager = NepenthesManager.getInstance();


    public NepenthesType(String typeName) {
        this.name = typeName;
    }


    public NepenthesType getSuperType() {
        return superType;
    }

    public Iterator<NepenthesType> getSubTypeIterator() {
        return subTypes.iterator();
    }

    public Set<NepenthesType> getSubTypes() {
        return subTypes;
    }

    public void addSubType(NepenthesType nepenthesType) {
        assert (nepenthesType != null) : "tried to set null sub-type";
        nepenthesType.setSuperType(this);
        subTypes.add(nepenthesType);
    }

    private void setSuperType(NepenthesType nepenthesType) {
        this.superType = nepenthesType;
    }


    public Nepenthes createInstance(String name, NepenthesType nepenthesType) {
        return Nepenthes.getNepenthes(name, nepenthesType);

    }


    public boolean hasInstance(Nepenthes nepenthes) {
        assert (nepenthes != null) : "asked about null object";
        if (nepenthes.getType() == this) {
            return true;
        }
        for (NepenthesType type : subTypes) {
            if (type.hasInstance(nepenthes)) {
                return true;
            }
        }
        return false;
    }



    public boolean isSubtype(NepenthesType superType) {
        // if they are both the same it cannot be subtype!
        if(superType == null){
            return false;
        }

        if (this == superType) {
            return false;
        }
        Iterator<NepenthesType> iterator = superType.getSubTypeIterator();
        while (iterator.hasNext()) {
            NepenthesType tmp = iterator.next();
            if (tmp.equals(this)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSuperType(NepenthesType subType){
        assert (subType != null) : "asked about null object";
        if(this == subType.superType){
            return true;
        }
        return false;
    }



    public String getName() {
        return name;
    }



}





