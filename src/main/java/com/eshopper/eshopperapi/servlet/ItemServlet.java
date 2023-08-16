package com.eshopper.eshopperapi.servlet;

import com.eshopper.eshopperapi.dto.ItemDto;
import com.eshopper.eshopperapi.service.ServiceFactory;
import com.eshopper.eshopperapi.service.custom.ItemService;
import com.eshopper.eshopperapi.service.util.ServiceTypes;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public class ItemServlet extends HttpServlet {
    private ItemService itemService;
    private Jsonb jsonb;

    @Override
    public void init() throws ServletException {
        itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM_SERVICE);
        jsonb = JsonbBuilder.create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("item: do-post");
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } else {
            try {
                ItemDto itemDto = jsonb.fromJson(req.getReader(), ItemDto.class);
                validate(itemDto);
                boolean save = itemService.save(itemDto);
                if (save) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    System.out.println("Data saved successfully.");
                } else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save data.");
                }
            } catch (RuntimeException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do-delete");
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } else {
            try {
                ItemDto itemDto = jsonb.fromJson(req.getReader(), ItemDto.class);
                boolean delete = itemService.delete(itemDto);
                if (delete) {
                    resp.setStatus(HttpServletResponse.SC_OK); // 200 status code for success
                } else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete data.");
                }
            } catch (RuntimeException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do-put");
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } else {
            try {
                ItemDto itemDto = jsonb.fromJson(req.getReader(), ItemDto.class);
                validate(itemDto);
                boolean update = itemService.update(itemDto);
                if (update) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update data.");
                }
            } catch (RuntimeException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do-get");
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } else {
            try {
                ItemDto itemDto = new ItemDto();
                itemDto.setCode(req.getParameter("code"));
                System.out.println(itemDto.getCode());
                ItemDto view = itemService.view(itemDto);
                if (view != null) {
                    System.out.println("Item exists");
                    String json = JsonbBuilder.create().toJson(view);  // Convert the customerDto to JSON using JSON-B (Yasson)
                    resp.setContentType("application/json");
                    PrintWriter writer = resp.getWriter();
                    writer.print(json);
                    writer.flush();
                    writer.close();
                } else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Item is not exists");
                }
            } catch (RuntimeException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }
        }
    }


    private void validate(ItemDto itemDto) throws RuntimeException {
        System.out.println(itemDto);
        if (itemDto.getCode() == null || !itemDto.getCode().matches("^I(00[1-9]|0[1-9]\\d|[1-9]\\d{2})$")) {
            throw new RuntimeException("Error occurred: Invalid item code");
        } else if (itemDto.getDescription() == null || !itemDto.getDescription().matches("^[A-Za-z]+(?:\\s[A-Za-z]+)*$")) {
            throw new RuntimeException("Error occurred: Invalid item description");
        } else if (!String.valueOf(itemDto.getQty()).matches("-?\\d+")) {
            throw new RuntimeException("Error occurred: Invalid item quantity");
        } else if (!Pattern.compile("^[0-9]+\\.?[0-9]*$").matcher(itemDto.getPrice().toString()).matches()) {
            throw new RuntimeException("Error occurred: Invalid item price");
        }
    }
}
