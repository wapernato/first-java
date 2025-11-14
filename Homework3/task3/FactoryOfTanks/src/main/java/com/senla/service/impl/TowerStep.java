package service.impl;

import service.ILineStep;
import service.IProductPart;
import model.Part;

public class TowerStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Башня собрана");
        return new Part("Башня");
    }
}