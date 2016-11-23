package com.bignerdranch.android.model;

import java.util.ArrayList;

/**
 * Created by bly on 2016/11/23.
 */
public class Result {
    private int status;
    private String message;
    private int size;
    private int total;
    private ArrayList<Entities> entities;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Entities> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entities> entities) {
        entities = entities;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return status+" "+message+" "+total+" "+size+" "+ entities;
    }

}
