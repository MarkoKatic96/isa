package com.acboot.aviocompany.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.acboot.aviocompany.dto.KorisnikDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Korisnik 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String grad;
	private String telefon;
	private boolean aktiviran;
	private Rola rola;
//	private Long zaduzenZaId;
	private boolean prviPutLogovan;
	
	@OneToMany(mappedBy="korisnik")
    private List<Karta> spisakRezervisanihKarata;
	
	//treba dodati rezervacije (rezervacije je entitet povezan sa kartom)
	
	@ManyToMany
	@JoinTable(
	  name = "korisnik_prijatelji", 
	  joinColumns = @JoinColumn(name = "korisnik_id"), 
	  inverseJoinColumns = @JoinColumn(name = "prijatelj_id"))
	private List<Korisnik> prijateljiKorisnika;
	
	@ManyToMany(mappedBy = "prijateljiKorisnika")
	private List<Korisnik> korisnici;
	
	/*
	 * ZAHTEVI KORISNIKA
	 */
	@ManyToMany
	@JoinTable(
	  name = "korisnik_zahtevi", 
	  joinColumns = @JoinColumn(name = "korisnik_id"), 
	  inverseJoinColumns = @JoinColumn(name = "prijatelj_id"))
	private List<Korisnik> zahteviKorisnika;
	
	@ManyToMany(mappedBy = "zahteviKorisnika")
	private List<Korisnik> korisniciZaht;
	
	

	
	public Korisnik(KorisnikDTO korisnik) {
		super();
		this.id = korisnik.getId();
		this.email = korisnik.getEmail();
		this.lozinka = korisnik.getLozinka();
		this.ime = korisnik.getIme();
		this.prezime = korisnik.getPrezime();
		this.grad = korisnik.getGrad();
		this.telefon = korisnik.getTelefon();
		this.aktiviran = korisnik.isAktiviran();
		this.rola = korisnik.getRola();
//		this.zaduzenZaId = korisnik.getZaduzenZaId();
		this.prviPutLogovan = korisnik.isPrviPutLogovan();
	}
	
}
