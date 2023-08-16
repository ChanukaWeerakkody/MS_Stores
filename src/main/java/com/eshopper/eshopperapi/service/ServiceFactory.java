package com.eshopper.eshopperapi.service;


import com.eshopper.eshopperapi.service.custom.impl.CustomerServiceImpl;
import com.eshopper.eshopperapi.service.custom.impl.ItemServiceImpl;
import com.eshopper.eshopperapi.service.custom.impl.OrderServiceImpl;
import com.eshopper.eshopperapi.service.util.ServiceTypes;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    public synchronized static ServiceFactory getInstance() {
        return instance == null ? instance = new ServiceFactory() : instance;
    }

    public <T extends SuperService> T getService(ServiceTypes type) {

        switch (type) {
            case CUSTOMER_SERVICE:
                return (T) new CustomerServiceImpl();
            case ITEM_SERVICE:
                return (T) new ItemServiceImpl();
            case ORDER_SERVICE:
                return (T) new OrderServiceImpl();
            default:
                throw new RuntimeException("Error: Invalid service Type");
        }
    }
}
