package com.senla.service.impl;

import com.senla.service.ILineStep;
import com.senla.service.IProductPart;
import com.senla.model.Part;

public class EngineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Двигатель собран");
        return new Part("Двигатель");
    }
}