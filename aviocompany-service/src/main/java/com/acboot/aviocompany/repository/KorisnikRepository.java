package com.acboot.aviocompany.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long>
{
	@Query(value = "select * from korisnik where email = :email ;", nativeQuery = true)
	Optional<Korisnik> getUserByEmail(@Param("email") String email);
}

