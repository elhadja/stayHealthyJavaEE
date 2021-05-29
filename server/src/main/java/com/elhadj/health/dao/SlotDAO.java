package com.elhadj.health.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elhadj.health.model.Slot;

@Repository
public interface SlotDAO extends CrudRepository<Slot, Long> {

}
