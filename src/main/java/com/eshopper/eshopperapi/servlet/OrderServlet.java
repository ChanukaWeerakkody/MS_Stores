package com.eshopper.eshopperapi.servlet;

import com.eshopper.eshopperapi.dto.OrdersDto;
import com.eshopper.eshopperapi.service.ServiceFactory;
import com.eshopper.eshopperapi.service.custom.OrdersService;
import com.eshopper.eshopperapi.service.util.ServiceTypes;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class OrderServlet extends HttpServlet {
    private OrdersService ordersService;
    private Jsonb jsonb;

    @Override
    public void init() throws ServletException {
        ordersService = ServiceFactory.getInstance().getService(ServiceTypes.ORDER_SERVICE);
        jsonb = JsonbBuilder.create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        try (BufferedReader reader = req.getReader()) {
            OrdersDto ordersDto = jsonb.fromJson(reader, OrdersDto.class);
            resp.setStatus(ordersService.save(ordersDto) ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

}
