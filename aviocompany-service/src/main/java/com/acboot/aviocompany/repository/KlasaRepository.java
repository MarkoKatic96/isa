package com.acboot.aviocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.Klasa;

@Repository
public interface KlasaRepository extends JpaRepository<Klasa, Long>
{

}
