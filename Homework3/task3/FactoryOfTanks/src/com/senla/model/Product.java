package com.senla.model;

import com.senla.service.IProduct;
import com.senla.service.IProductPart;

public class Product implements IProduct {
    private IProductPart firstPart;
    private IProductPart secondPart;
    private IProductPart thirdPart;

    @Override
    public void installFirstPart(IProductPart part) {
        this.firstPart = part;
        System.out.println("Первая часть установлена");
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.secondPart = part;
        System.out.println("Вторая часть установлена");
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.thirdPart = part;
        System.out.println("Третья часть установлена");
    }
}