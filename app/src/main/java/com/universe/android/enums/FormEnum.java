package com.universe.android.enums;


public enum FormEnum {


    //FormIds
    survey("survey"),client("client"),customer("customer"),category("category"),question("question");

    private final String name;

    FormEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }


};


