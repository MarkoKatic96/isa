package com.acboot.aviocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.AvioKompanija;

@Repository
public interface AvioKompanijaRepository extends JpaRepository<AvioKompanija, Long>
{
	
}
