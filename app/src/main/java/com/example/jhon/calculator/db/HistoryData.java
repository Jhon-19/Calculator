package com.example.jhon.calculator.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Jhon on 2020/1/29.
 */

public class HistoryData extends DataSupport {

    private int id;
    private String input;
    private String result;

    public int getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
