package com.acboot.aviocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.Karta;

@Repository
public interface KartaRepository extends JpaRepository<Karta, Long>
{

}
