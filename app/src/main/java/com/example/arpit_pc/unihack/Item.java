package com.example.arpit_pc.unihack;

public class Item {

    String name;
    String id;
    float carbonValue;

    public Item(String name, String id, float value){
        this.name = name;
        this.id = id;
        this.carbonValue = value;
    }

    public String GetName(){
        return name;
    }

    public String GetId(){
        return id;
    }

    public float GetCarbonValue(){
        return carbonValue;
    }
}
