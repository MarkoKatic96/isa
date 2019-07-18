package com.isa.hoteli.hoteliservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;

@Repository
public interface DodatnaUslugaRepository extends JpaRepository<DodatnaUsluga, Long>{

}
