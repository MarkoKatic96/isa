package com.acboot.aviocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.Destinacija;

@Repository
public interface DestinacijaRepository extends JpaRepository<Destinacija, Long>
{
	

}
