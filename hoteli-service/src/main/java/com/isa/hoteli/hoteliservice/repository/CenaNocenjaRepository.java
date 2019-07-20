package com.isa.hoteli.hoteliservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.CenaNocenja;

@Repository
public interface CenaNocenjaRepository extends JpaRepository<CenaNocenja, Long>{

	@Query(value = "SELECT * FROM cena_nocenja WHERE hotelska_soba_id = ?1", nativeQuery=true)
	List<CenaNocenja> getAllFromHotelRoom(Long id);
	
}
