package com.eshopper.eshopperapi.service.custom.impl;

import com.eshopper.eshopperapi.dao.DaoFactory;
import com.eshopper.eshopperapi.dao.custom.ItemDao;
import com.eshopper.eshopperapi.dao.util.DaoTypes;
import com.eshopper.eshopperapi.dto.ItemDto;
import com.eshopper.eshopperapi.entity.Item;
import com.eshopper.eshopperapi.service.custom.ItemService;
import com.eshopper.eshopperapi.service.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;

    public ItemServiceImpl() {
        itemDao = DaoFactory.getInstance().getDAO(DaoTypes.ITEM_DAO);
    }

    @Override
    public boolean save(ItemDto dto) {
        Item item = new Item();

        item.setCode(dto.getCode());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setQty(dto.getQty());

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try (session) {
            itemDao.save(item, session);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(ItemDto dto) {
        Item item = new Item();
        item.setCode(dto.getCode());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setQty(dto.getQty());

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try (session) {
            itemDao.update(item, session);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemDto view(ItemDto dto) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Item item = new Item();
            item.setCode(dto.getCode());
            Item viewed = itemDao.view(item, session);
            if (viewed != null) {
                dto.setCode(viewed.getCode());
                dto.setDescription(viewed.getDescription());
                dto.setPrice(viewed.getPrice());
                dto.setQty(viewed.getQty());
                return dto;
            }
            throw new RuntimeException("Item not found");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(ItemDto dto) {
        Item item = new Item();
        item.setCode(dto.getCode());
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            itemDao.delete(item, session);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
