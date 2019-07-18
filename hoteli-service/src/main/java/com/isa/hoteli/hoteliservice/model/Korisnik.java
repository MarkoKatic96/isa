package com.isa.hoteli.hoteliservice.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	@OneToMany(mappedBy="korisnik")
	private List<Rezervacije> rezervacijeList;
	
	public Korisnik() {

	}
	
	public Korisnik(Long id, String email, String lozinka, String ime, String prezime, String grad, String telefon,
			boolean aktiviran, Rola rola, List<Rezervacije> rezervacijeList) {
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

	

}
