package com.eshopper.eshopperapi.service;

import com.eshopper.eshopperapi.dto.SuperDto;

public interface SuperService<T extends SuperDto> {
    boolean save(T dto);

    boolean update(T dto);

    T view(T dto);

    boolean delete(T dto);
}
