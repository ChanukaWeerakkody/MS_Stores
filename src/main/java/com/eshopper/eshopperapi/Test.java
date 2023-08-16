package com.eshopper.eshopperapi;

import com.eshopper.eshopperapi.entity.Customer;
import com.eshopper.eshopperapi.entity.Item;
import com.eshopper.eshopperapi.entity.OrderDetails;
import com.eshopper.eshopperapi.entity.Orders;
import com.eshopper.eshopperapi.service.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();

        Customer customer = new Customer();
        customer.setId("C001");
        customer.setName("Kasun");
        customer.setAddress("Galle");
        customer.setSalary(12000.00);

        Transaction transaction = session.beginTransaction();
        session.save(customer);
        transaction.commit();
        session.close();
        System.out.println("customer saved");

        session = FactoryConfiguration.getInstance().getSession();

        Item item1 = new Item();
        item1.setCode("I001");
        item1.setPrice(1200.00);
        item1.setQty(10);
        item1.setDescription("Pen");

        session = FactoryConfiguration.getInstance().getSession();
        transaction = session.beginTransaction();
        session.save(item1);
        transaction.commit();
        session.close();
        System.out.println("item1 saved");

        Item item2 = new Item();
        item2.setCode("I002");
        item2.setPrice(1200.00);
        item2.setQty(12);
        item2.setDescription("Book");

        session = FactoryConfiguration.getInstance().getSession();
        transaction = session.beginTransaction();
        session.save(item2);
        transaction.commit();
        session.close();
        System.out.println("item2 saved");


        Orders order = new Orders();
        order.setCustomer(customer);
        order.setId("O001");

        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setQty(10);
        orderDetails1.setItem(item1);
        orderDetails1.setOrders(order);

        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails2.setQty(10);
        orderDetails2.setItem(item2);
        orderDetails2.setOrders(order);

        order.getOrderDetailsList().add(orderDetails1);
        order.getOrderDetailsList().add(orderDetails2);

        session = FactoryConfiguration.getInstance().getSession();
        transaction = session.beginTransaction();
        session.save(order);

        for (int i = 0; i < order.getOrderDetailsList().size(); i++) {
            session.save(order.getOrderDetailsList().get(i));
        }

        transaction.commit();
        session.close();

        System.out.println("order 1 saved");
        System.out.println(" " );

        session = FactoryConfiguration.getInstance().getSession();
        customer = session.get(Customer.class, "C001");
        System.out.println(customer);
        session.close();

        System.out.println("----------------------------------------------------------------------------------------------------");
        List<Orders> list = customer.getOrdersList();
        System.out.println(list.get(0));
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println(list.get(0).getOrderDetailsList());
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("first order");
        Orders order2 = new Orders();
        order2.setCustomer(customer);
        order2.setId("O002");

        OrderDetails orderDetails3 = new OrderDetails();
        orderDetails3.setQty(5550);
        orderDetails3.setItem(item1);
        orderDetails3.setOrders(order2);

        order2.getOrderDetailsList().add(orderDetails3);

        session = FactoryConfiguration.getInstance().getSession();
        transaction = session.beginTransaction();
        session.save(order2);
        for (int i = 0; i < order2.getOrderDetailsList().size(); i++) {
            session.save(order2.getOrderDetailsList().get(i));
        }

        transaction.commit();
        session.close();

        System.out.println("order 2 saved");
        System.out.println(" " );

        session = FactoryConfiguration.getInstance().getSession();
        customer = session.get(Customer.class, "C001");
        System.out.println(customer);
        session.close();

        System.out.println("----------------------------------------------------------------------------------------------------");
         list = customer.getOrdersList();
        System.out.println(list.size());
//        System.out.println(list.get(0));
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
                System.out.println(list.get(i).getOrderDetailsList());
            System.out.println("----------------------------------------------------------------------------------------------------");

        }


    }
}
