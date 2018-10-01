package com.vinicius.java.task.model;

public enum Priority {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private String description;

    Priority(String descreption){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
