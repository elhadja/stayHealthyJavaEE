package com.elhadj.health.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elhadj.health.model.Slot;

@Repository
public interface SlotDAO extends CrudRepository<Slot, Long> {
	List<Slot> findByDoctorIdAndDateGreaterThanEqual(long doctorId, LocalDate date);
}
