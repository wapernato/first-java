package service.impl;

import service.ILineStep;
import service.IProductPart;
import model.Part;

public class EngineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Двигатель собран");
        return new Part("Двигатель");
    }
}