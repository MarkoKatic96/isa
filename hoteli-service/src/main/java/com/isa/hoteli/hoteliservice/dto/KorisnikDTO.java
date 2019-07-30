package com.isa.hoteli.hoteliservice.dto;

import java.util.List;

import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.model.Rola;

public class KorisnikDTO {
	
	private Long id;
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String grad;
	private String telefon;
	private boolean aktiviran;
	private Rola rola;
	private List<Rezervacije> rezervacijeList;
	private Long zaduzenZaId;
	private boolean prviPutLogovan;
	
	public KorisnikDTO() {

	}
	
	public KorisnikDTO(Korisnik korisnik) {
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
	
	public KorisnikDTO(Long id, String email, String lozinka, String ime, String prezime, String grad, String telefon,
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
	public List<Rezervacije> getRezervacijeList() {
		return rezervacijeList;
	}
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
