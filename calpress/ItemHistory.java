package com.example.calpress;

public class ItemHistory {

    private int id;
    private String expression;
    private String result;
    private String createdAt;

    public ItemHistory(
            int id,
            String expression,
            String result,
            String createdAt
    ) {

        this.id = id;
        this.expression = expression;
        this.result = result;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getExpression() {
        return expression;
    }

    public String getResult() {
        return result;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}