package com.isa.hoteli.hoteliservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.model.Rola;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{

	@Query(value = "SELECT * FROM korisnik WHERE email = ?1", nativeQuery=true)
	Korisnik getUserByEmail(String email);
	
	@Query(value = "SELECT * FROM korisnik WHERE rola = 2 AND zaduzen_za_id IS NULL", nativeQuery=true)
	List<Korisnik> findAdmineHotela();
	
	@Query(value = "SELECT * FROM korisnik WHERE rola = 1 AND zaduzen_za_id IS NULL", nativeQuery=true)
	List<Korisnik> findAdmineAvio();
	
	@Query(value = "SELECT * FROM korisnik WHERE rola = 3 AND zaduzen_za_id IS NULL", nativeQuery=true)
	List<Korisnik> findAdmineRent();
}
