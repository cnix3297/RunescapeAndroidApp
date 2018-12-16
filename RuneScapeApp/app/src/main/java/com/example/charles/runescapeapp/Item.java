package com.example.charles.runescapeapp;

import java.util.regex.Pattern;

public class Item {
    private int buyPrice;
    private int buyAmount;
    private String itemName;
    private int min;
    private int max;

    public void setBuyPrice(int buyPrice) { this.buyPrice = buyPrice; }
    public void setBuyAmount(int buyAmount) { this.buyAmount = buyAmount; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setMin(int min) { this.min = min; }
    public void setMax(int max) { this.max = max; }

    public int getBuyPrice() { return  buyPrice; }
    public int getBuyAmount() { return buyAmount; }
    public String getItemName() {
        return itemName;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }

    public boolean checkSetItemName(String s){
        if (checkString(s)){
            itemName = s;
            return true;
        }
        return false;
    }

    public boolean checkSetItemBuyAmount(String s) {
        if(checkInt(s)){
            buyAmount = Integer.parseInt(s);
            return true;
        }

        return false;
    }

    public boolean checkSetItemBuyPrice(String s){
        if(checkInt(s)){
            buyPrice = Integer.parseInt(s);
            return true;
        }
        return false;
    }

    private boolean checkInt(String s) {
        if(!s.isEmpty())
            return true;
        return false;
    }

    private  boolean checkString(String s){
        return     Pattern.compile("[^a-z0-9A-Z\\s]|^[A-Z]^[0-9]|\\s[^A-Z]^[0-9]").matcher(s).find();
    }




}
