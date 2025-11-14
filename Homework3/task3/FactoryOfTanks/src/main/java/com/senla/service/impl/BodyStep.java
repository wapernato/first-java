
package service.impl;

import service.ILineStep;
import service.IProductPart;
import model.Part;

public class BodyStep implements ILineStep {
    @Override
    public Part buildProductPart(){
        System.out.println("Корпус собран");
        return new Part("Корпус");
    }
}