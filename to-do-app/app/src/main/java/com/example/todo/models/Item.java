package com.example.todo.models;

import java.util.ArrayList;

public class Item {
    private ArrayList<String> itemsList;

    public Item() {
        itemsList = new ArrayList<>();
    }

    public ArrayList<String> getItemsList() {
        return itemsList;
    }

    public void addItem(String item) {
        this.itemsList.add(item);
    }
}
