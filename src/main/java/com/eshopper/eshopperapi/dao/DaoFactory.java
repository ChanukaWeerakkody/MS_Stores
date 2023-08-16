package com.eshopper.eshopperapi.dao;

import com.eshopper.eshopperapi.dao.custom.impl.CustomerDaoImpl;
import com.eshopper.eshopperapi.dao.custom.impl.ItemDaoImpl;
import com.eshopper.eshopperapi.dao.custom.impl.OrdersDaoImpl;
import com.eshopper.eshopperapi.dao.util.DaoTypes;


public class DaoFactory {
    private static DaoFactory instance;

    public synchronized static DaoFactory getInstance() {
        return instance == null ? instance = new DaoFactory() : instance;
    }

    public <T extends SuperDao> T getDAO(DaoTypes type) {

        switch (type) {
            case CUSTOMER_DAO:
                return (T) new CustomerDaoImpl();
            case ITEM_DAO:
                return (T) new ItemDaoImpl();
            case ORDERS_DAO:
                return (T) new OrdersDaoImpl();
            default:
                throw new RuntimeException("Error: Invalid DAO Type");
        }
    }
}
