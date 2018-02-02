package com.universe.android.enums;


public enum QuestionsEnum {
    categoryId("categoryId"),id("id"), status("status"), title("title");

    private final String name;

    private QuestionsEnum(String s) {
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


