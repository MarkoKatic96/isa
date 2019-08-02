package com.acboot.aviocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.Prtljag;

@Repository
public interface PrtljagRepository extends JpaRepository<Prtljag, Long>
{

}