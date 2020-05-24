package com.example.myapplication;

import java.io.Serializable;


public class ShopItem implements Serializable{
    private Integer id = null;
    private String name = null;
    private Double price = null;
    private Boolean selected = null;

    public ShopItem(int id,String name, Double price,Boolean selected) {
        this.id=id;
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

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
