package com.example.pmp_3;

public class Item {
    String name = null;
    Double price = null;
    Boolean selected = null;

    public Item(String name, Double price, Boolean selected) {
        this.name = name;
        this.price = price;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }



}
