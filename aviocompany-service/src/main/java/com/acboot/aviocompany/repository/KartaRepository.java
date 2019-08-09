package com.acboot.aviocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.acboot.aviocompany.model.Karta;
import com.acboot.aviocompany.model.Korisnik;

@EnableJpaRepositories(basePackageClasses = {Karta.class})
@Repository
public interface KartaRepository extends JpaRepository<Karta, Long>
{
	public Karta findByKorisnik(Korisnik korisnik);
}
