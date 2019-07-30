package com.isa.hoteli.hoteliservice.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.hoteli.hoteliservice.dto.KorisnikDTO;

/*
     {
        "email": "z",
        "lozinka": "z",
        "ime": "z",
        "prezime": "z",
        "grad": "z",
        "telefon": "z",
        "aktiviran": true,
        "rola": "MASTER_ADMIN",
        "rezervacijeList": null
    }
 */

@Entity
public class Korisnik {
	
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
	private Long zaduzenZaId;//kombinacijom ovoga i role cemo znati za koji je hotel, avio, rent tacno zaduzen
	private boolean prviPutLogovan;
	@OneToMany(mappedBy="korisnik")
	private List<Rezervacije> rezervacijeList;
	
	public Korisnik() {

	}
	
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
		this.zaduzenZaId = korisnik.getZaduzenZaId();
		this.prviPutLogovan = korisnik.isPrviPutLogovan();
	}
	
	public Korisnik(Korisnik korisnik) {
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
		this.zaduzenZaId = korisnik.getZaduzenZaId();
		this.prviPutLogovan = korisnik.isPrviPutLogovan();
	}
	
	public Korisnik(Long id, String email, String lozinka, String ime, String prezime, String grad, String telefon,
			boolean aktiviran, Rola rola, List<Rezervacije> rezervacijeList, Long zaduzenZaId, boolean prviPutLogovan) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.grad = grad;
		this.telefon = telefon;
		this.aktiviran = aktiviran;
		this.rola = rola;
		this.rezervacijeList = rezervacijeList;
		this.zaduzenZaId = zaduzenZaId;
		this.prviPutLogovan = prviPutLogovan;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public boolean isAktiviran() {
		return aktiviran;
	}
	public void setAktiviran(boolean aktiviran) {
		this.aktiviran = aktiviran;
	}
	public Rola getRola() {
		return rola;
	}
	public void setRola(Rola rola) {
		this.rola = rola;
	}
	
	@JsonIgnore
	public List<Rezervacije> getRezervacijeList() {
		return rezervacijeList;
	}
	@JsonIgnore
	public void setRezervacijeList(List<Rezervacije> rezervacijeList) {
		this.rezervacijeList = rezervacijeList;
	}

	public Long getZaduzenZaId() {
		return zaduzenZaId;
	}

	public void setZaduzenZaId(Long zaduzenZaId) {
		this.zaduzenZaId = zaduzenZaId;
	}

	public boolean isPrviPutLogovan() {
		return prviPutLogovan;
	}

	public void setPrviPutLogovan(boolean prviPutLogovan) {
		this.prviPutLogovan = prviPutLogovan;
	}

	

}
