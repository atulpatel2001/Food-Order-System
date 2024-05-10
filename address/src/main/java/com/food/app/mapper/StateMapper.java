package com.food.app.mapper;

import com.food.app.dto.StateDto;
import com.food.app.model.State;

public class StateMapper {


    public static State mapToState(StateDto stateDto,State state){
        state.setName(stateDto.getName());
        state.setId(stateDto.getId());
        state.setCountryId(stateDto.getCountryId());
        return state;
    }

    public  static StateDto mapToStateDto(State state,StateDto stateDto){
        stateDto.setId(state.getId());
        stateDto.setName(state.getName());
        stateDto.setCountryId(state.getCountryId());
        return stateDto;
    }
}
