package com.acboot.aviocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.Let;

@Repository
public interface LetRepository extends JpaRepository<Let, Long>
{

}
