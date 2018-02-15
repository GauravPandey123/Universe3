package com.universe.android.enums;


public enum FormEnumKeys {

    type("type"),inputType("inputType"),maxValue("maxValue"),minValue("minValue"),maxLength("maxLength"),orientation("orientation"),optionValuesCount("optionValuesCount"),optionValues("optionValues") ;

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
