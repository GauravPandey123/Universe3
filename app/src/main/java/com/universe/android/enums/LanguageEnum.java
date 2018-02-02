package com.universe.android.enums;

public enum LanguageEnum {
    english("en"), hindi("hi");


    private final String name;

    LanguageEnum(String s) {
        name = s;
    }

    /**
     * Equals name boolean.
     *
     * @param otherName the other name
     * @return the boolean
     */
    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
