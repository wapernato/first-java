package com.senla.service.impl;

import com.senla.service.ILineStep;
import com.senla.service.IProductPart;
import com.senla.model.Part;

public class BodyStep implements ILineStep {
    @Override
    public IProductPart buildProductPart(){
        System.out.println("Корпус собран");
        return new Part("Корпус");
    }
}