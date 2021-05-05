package com.example.springboothw4.lesson6;

public class MainApp {

    public static void main(String[] args) {

        Factory factory = new Factory();
        factory.prepareData();
        factory.getProductByIdCostumer(2L);
        factory.getCostumerByIdProduct(2L);

    }

}
