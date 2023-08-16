package com.eshopper.eshopperapi.service.custom.impl;

import com.eshopper.eshopperapi.dao.DaoFactory;
import com.eshopper.eshopperapi.dao.custom.OrdersDao;
import com.eshopper.eshopperapi.dao.util.DaoTypes;
import com.eshopper.eshopperapi.dto.OrderDetailsDto;
import com.eshopper.eshopperapi.dto.OrdersDto;
import com.eshopper.eshopperapi.entity.Customer;
import com.eshopper.eshopperapi.entity.Item;
import com.eshopper.eshopperapi.entity.OrderDetails;
import com.eshopper.eshopperapi.entity.Orders;
import com.eshopper.eshopperapi.service.custom.OrdersService;
import com.eshopper.eshopperapi.service.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrdersService {
    private final OrdersDao ordersDao;

    public OrderServiceImpl() {
        ordersDao = DaoFactory.getInstance().getDAO(DaoTypes.ORDERS_DAO);
    }

    @Override
    public boolean save(OrdersDto dto) {
        Orders order = new Orders();
        order.setId(dto.getId());

        Customer customer = new Customer();
        customer.setId(dto.getCustomerDto().getId());
        customer.setName(dto.getCustomerDto().getName());
        customer.setAddress(dto.getCustomerDto().getAddress());
        customer.setSalary(dto.getCustomerDto().getSalary());

        order.setCustomer(customer);

        List<OrderDetails> orderDetails = new ArrayList<>();

        for (OrderDetailsDto ele : dto.getOrderDetailsDtoList()) {
            OrderDetails details = new OrderDetails();
            details.setOrders(order);

            Item item = new Item();
            item.setCode(ele.getItemDto().getCode());
            item.setPrice(ele.getItemDto().getPrice());
            item.setQty(ele.getItemDto().getQty());
            item.setDescription(ele.getItemDto().getDescription());

            details.setQty(ele.getQty());
            details.setItem(item);
            orderDetails.add(details);
            order.setOrderDetailsList(orderDetails);
        }

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            if (ordersDao.save(order, session)) {
                order.getOrderDetailsList().forEach(session::save);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(OrdersDto dto) {
        return false;
    }

    @Override
    public OrdersDto view(OrdersDto dto) {
        return null;
    }

    @Override
    public boolean delete(OrdersDto dto) {
        return false;
    }
}
