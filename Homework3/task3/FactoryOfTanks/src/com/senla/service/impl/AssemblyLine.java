package com.senla.service.impl;

import com.senla.service.IAssemblyLine;
import com.senla.service.ILineStep;
import com.senla.service.IProduct;
import com.senla.service.IProductPart;

public class AssemblyLine implements IAssemblyLine {
    private final ILineStep first;
    private final ILineStep second;
    private final ILineStep third;

    public AssemblyLine(ILineStep first, ILineStep second, ILineStep third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        product.installFirstPart(first.buildProductPart());
        product.installSecondPart(second.buildProductPart());
        product.installThirdPart(third.buildProductPart());
        return product;
    }
}