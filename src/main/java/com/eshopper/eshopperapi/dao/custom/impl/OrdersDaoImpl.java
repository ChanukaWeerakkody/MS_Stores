package com.eshopper.eshopperapi.dao.custom.impl;

import com.eshopper.eshopperapi.dao.custom.OrdersDao;
import com.eshopper.eshopperapi.entity.Orders;
import org.hibernate.Session;

public class OrdersDaoImpl implements OrdersDao {
    @Override
    public boolean save(Orders entity, Session session) throws RuntimeException {
        session.save(entity);
        System.out.println("order saved!");
        return true;
    }

    @Override
    public boolean update(Orders entity, Session session) {
        return false;
    }

    @Override
    public Orders view(Orders entity, Session session) {
        return null;
    }

    @Override
    public boolean delete(Orders entity, Session session) {
        return false;
    }
}
