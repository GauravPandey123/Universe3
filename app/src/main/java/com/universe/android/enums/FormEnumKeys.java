package com.universe.android.enums;


public enum FormEnumKeys {

  ;

    private final String name;

    FormEnumKeys(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equalsIgnoreCase(otherName);
    }

    public String toString() {
        return this.name;
    }

}
