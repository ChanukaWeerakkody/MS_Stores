package com.eshopper.eshopperapi.dao;

import com.eshopper.eshopperapi.entity.SuperEntity;
import org.hibernate.Session;

public interface CrudDao<T extends SuperEntity> extends SuperDao {
    boolean save(T entity, Session session);

    boolean update(T entity, Session session);

    T view(T entity, Session session);

    boolean delete(T entity, Session session);
}
