package model;

import service.IProductPart;

public class Part implements IProductPart {
    private final String name;
    public Part(String name) { this.name = name; }
    @Override public String toString() { return name; }
}