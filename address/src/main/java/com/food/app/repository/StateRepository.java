package com.food.app.repository;

import com.food.app.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends JpaRepository<State,Long> {
    @Query("SELECT DISTINCT s FROM State s WHERE s.enable = true order by s.id desc")
    Optional<List<State>> findDistinctFirstByIdAndEnableTrue();
    @Query("SELECT s FROM State s WHERE s.enable = true and s.id =:stateId")
    Optional<State> findStateById(Long stateId);
    @Query("SELECT s FROM State s WHERE s.enable = true and s.countryId =:countryId")
    Optional<List<State>> findStateByCountryId(Long countryId);
    Optional<State> findByName(String name);
}
