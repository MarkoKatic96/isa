package com.acboot.aviocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.DodatnaUsluga;

@Repository
public interface DodatnaUslugaRepository extends JpaRepository<DodatnaUsluga, Long>
{

}
