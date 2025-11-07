package com.senla.service.impl;

import com.senla.service.ILineStep;
import com.senla.service.IProductPart;
import com.senla.model.Part;

public class TowerStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Башня собрана");
        return new Part("Башня");
    }
}