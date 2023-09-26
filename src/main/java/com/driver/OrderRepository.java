package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    //HashMap of <OrderId, Order Object>
    HashMap<String, Order> orders = new HashMap<>();

    //HashMap of <DeliveryPartnerID, DeliveryPartner Object>
    HashMap<String, DeliveryPartner> deliveryPartners = new HashMap<>();

    //HashMap of <partnerId, ListOfReceivedOrder>
    HashMap<String, List<Order>> orderReceived = new HashMap<>();

    //HashMap of <OrderId, Order Object>
    HashMap<String, Order> unassignedOrder = new HashMap<>();

    public void addOrder(Order order){
        String orderId = order.getId();;
        orders.put(orderId,order);
        unassignedOrder.put(orderId,order);
    }

    public void addPartner(String partnerId){
        DeliveryPartner dp = new DeliveryPartner(partnerId);
        deliveryPartners.put(partnerId, dp);
    }

    public void addOrderPartnerPair(String orderId, String partnerId){

        List<Order> orderTillNow = orderReceived.getOrDefault(partnerId, new ArrayList<>());
        Order newOrder = orders.get(orderId);
        orderTillNow.add(newOrder);
        orderReceived.put(partnerId,orderTillNow);

        DeliveryPartner dp = deliveryPartners.get(partnerId);
        dp.setNumberOfOrders(dp.getNumberOfOrders() + 1);
        unassignedOrder.remove(orderId);

    }

    public Order getOrderById(String orderId){
        Order order = orders.get(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner deliveryPartner = deliveryPartners.get(partnerId);
        return  deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId){
        Integer count = 0;
        List<Order> ordersTillNow = orderReceived.get(partnerId);
        count = ordersTillNow.size();
        return count;
    }

    public List<Order> getOrdersByPartnerId(String partnerId){
        List<Order> orderList = orderReceived.get(partnerId);
        return orderList;
    }

    public List<String> getAllOrder(){
        List<String> allOrderList = new ArrayList<>();
        for (String order : orders.keySet()) {
            allOrderList.add(order);
        }
        return allOrderList;
    }

    public Integer getAssignedOrderCount(){
        Integer count = 0;
        for (String dp : orderReceived.keySet()) {
            count += orderReceived.get(dp).size();
        }
        return count;
    }

    public Integer getCountOfUnAssignedOrder(){
        return unassignedOrder.size();
    }

    public void deletePartnerById(String partnerId){
        List<Order> orderList = getOrdersByPartnerId(partnerId);
        for (Order order : orderList) {
            unassignedOrder.put(order.getId(), order);
        }

        DeliveryPartner dp = deliveryPartners.get(partnerId);
        dp.setNumberOfOrders(0);
        deliveryPartners.remove(partnerId);
    }

    public boolean containsOrder(List<Order> orderList, Order order){
        for (Order o : orderList) {
            if(orderList.contains(o))
                return true;
        }
        return false;
    }

    public void deleteOrderById(String orderId){
        Order order = orders.get(orderId);

        List<Order> orderList = null;
        String partner = "";
        for(String pId : orderReceived.keySet()){
            if(containsOrder(orderReceived.get(pId), order)){
                orderList = orderReceived.get(pId);
                partner = pId;
                break;
            }
        }
        orderList.remove(order);
        orderReceived.put(partner, orderList);
        DeliveryPartner dp = deliveryPartners.get(partner);
        dp.setNumberOfOrders(dp.getNumberOfOrders() - 1);
        orders.remove(orderId);
    }
}
