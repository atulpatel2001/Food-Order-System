package com.food.app.service.imple;

import com.food.app.dto.StateDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.AlreadyExistsException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.mapper.StateMapper;
import com.food.app.model.State;
import com.food.app.repository.StateRepository;
import com.food.app.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImple implements StateService {

    @Autowired
    private StateRepository stateRepository;

    @Override
    public StateDto getStateById(Long stateId) {

        Optional<State> stateOptional = this.stateRepository.findStateById(stateId);

        if (stateOptional.isEmpty()) {
            throw new ResourceNotFoundException("State", "State id", stateId.toString());
        }

        return StateMapper.mapToStateDto(stateOptional.get(), new StateDto());

    }

    @Override
    public List<StateDto> getStateByCountryId(Long countryId) {
        Optional<List<State>> stateByCountryId = this.stateRepository.findStateByCountryId(countryId);

        if (stateByCountryId.isEmpty()) {
            throw new AllDataNotFoundException("No State Available From This Country in system");
        }

        List<StateDto> stateDtos = new ArrayList<>();

        stateByCountryId.get().forEach(State -> stateDtos.add(StateMapper.mapToStateDto(State, new StateDto())));

        return stateDtos;
    }

    @Override
    public StateDto getStateByName(String name) {
        Optional<State> StateOptional = this.stateRepository.findByName(name.toLowerCase());
        if (StateOptional.isEmpty()) {
            throw new ResourceNotFoundException("State", "State name", name);
        }
        return StateMapper.mapToStateDto(StateOptional.get(), new StateDto());
    }

    @Override
    public List<StateDto> getAllState() {
        Optional<List<State>> optionalStates = this.stateRepository.findDistinctFirstByIdAndEnableTrue();

        if (optionalStates.isEmpty()) {
            throw new AllDataNotFoundException("in System not have any District");
        }

        List<StateDto> stateDtos = new ArrayList<>();

        optionalStates.get().forEach(State -> stateDtos.add(StateMapper.mapToStateDto(State, new StateDto())));

        return stateDtos;
    }

    @Override
    public void deleteState(Long id) {
        Optional<State> optionalState = this.stateRepository.findById(id);

        if (optionalState.isEmpty()) {
            throw new ResourceNotFoundException("State", "State id", id.toString());
        }

        State state = optionalState.get();

        state.setEnable(false);
        stateRepository.save(state);

    }

    @Override
    public StateDto addState(StateDto state) {
        Optional<State> optionalState = this.stateRepository.findByName(state.getName().trim().toLowerCase());
        if (optionalState.isPresent()) {
            throw new AlreadyExistsException("This State is Already register With This Name "+state.getName());
        }
        State state1 = StateMapper.mapToState(state, new State());
        state1.setName(state1.getName().trim().toLowerCase());
        state1.setEnable(true);
        return StateMapper.mapToStateDto(this.stateRepository.save(state1), new StateDto());

    }

    @Override
    public boolean updateState(StateDto state) {
        boolean f = false;
        Optional<State> optionalState = this.stateRepository.findById(state.getId());
        if (optionalState.isEmpty()) {
            f = false;
            throw new ResourceNotFoundException("State", "id", state.getId().toString());
        } else if (this.stateRepository.findByName(state.getName().trim().toLowerCase()).isPresent()) {
            f = false;
            throw new AlreadyExistsException("State is Already register With This Name");
        } else {
            State oldState = optionalState.get();
            oldState.setName(state.getName().trim().toLowerCase());
            oldState.setCountryId(state.getCountryId()) ;
            this.stateRepository.save(oldState);
            f = true;
        }
        return f;

    }
}
