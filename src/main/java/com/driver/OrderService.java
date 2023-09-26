package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
       Order order = orderRepository.getOrderById(orderId);
       return  order;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        DeliveryPartner dp = orderRepository.getPartnerById(partnerId);
        return  dp;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Integer count = orderRepository.getOrderCountByPartnerId(partnerId);
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<Order> orderList = orderRepository.getOrdersByPartnerId(partnerId);
        List<String> stringOrderList = new ArrayList<>();

        for (Order order : orderList) {
            stringOrderList.add(order.getId());
        }
        return  stringOrderList;
    }

    public List<String> getAllOrders() {
        List<String> order = orderRepository.getAllOrder();
        return order;
    }

    public Integer getCountOfUnassignedOrders() {
        Integer count = orderRepository.getCountOfUnAssignedOrder();
        return count;
    }

    public Integer convertTimeToMinutes(String time)
    {
        String[] hhmm = time.split(":",-2);
        Integer hrs = Integer.parseInt(hhmm[0]);
        Integer mins = Integer.parseInt(hhmm[1]);
        Integer timeInMinutes = hrs*60 + mins;
        return timeInMinutes;
    }

    public String convertTimeToString(int time)
    {
        int hrs = time/60;
        int mins = time%60;
        String HH = String.valueOf(hrs);
        String MM = String.valueOf(mins);
        if(HH.length() < 2)
            HH = "0" + HH;
        if(MM.length() < 2)
            MM = "0" + MM;
        return HH + ":" + MM;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count = 0;
        Integer timeInMinutes = convertTimeToMinutes(time);
        List<Order> orderList = orderRepository.getOrdersByPartnerId(partnerId);
        for(Order order : orderList)
        {
            if(order.getDeliveryTime() > timeInMinutes)
                count++;
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        String lastDeliveryTime = "";
        int lastTime = 0;
        List<Order> orderList = orderRepository.getOrdersByPartnerId(partnerId);
        for(Order order : orderList)
        {
            lastTime = Math.max(lastTime,order.getDeliveryTime());
        }
        lastDeliveryTime = convertTimeToString(lastTime);
        return lastDeliveryTime;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deleteOrderById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
