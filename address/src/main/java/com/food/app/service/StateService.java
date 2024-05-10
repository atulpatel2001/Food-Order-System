package com.food.app.service;

import com.food.app.dto.StateDto;

import java.util.List;

public interface StateService {


    StateDto getStateById(Long StateId);
    List<StateDto> getStateByCountryId(Long countryId);
    StateDto getStateByName(String name);

    List<StateDto> getAllState();


    void deleteState(Long id);


    StateDto addState(StateDto State);

    boolean updateState(StateDto State);
}
