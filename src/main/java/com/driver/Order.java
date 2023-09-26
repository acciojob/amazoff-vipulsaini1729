package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        String [] time = deliveryTime.split(":", -2);
        int hrs = Integer.parseInt(time[0]);
        int mins = Integer.parseInt(time[1]);
        this.deliveryTime = hrs*60 + mins;
    }

    public String getId() {

        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;}
}
