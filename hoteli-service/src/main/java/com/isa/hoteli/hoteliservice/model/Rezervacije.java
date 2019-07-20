package com.isa.hoteli.hoteliservice.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;

@Entity
public class Rezervacije {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date datumOd;
	private Date datumDo;
	private float ukupnaCena;
	
	@ManyToOne
    @JoinColumn(name="hotelskaSoba_id", nullable=false)
    private HotelskaSoba hotelskaSoba;
	
	@ManyToOne
    @JoinColumn(name="korisnik_id", nullable=false)
    private Korisnik korisnik;

	public Rezervacije() {

	}

	public Rezervacije(RezervacijeDTO rezervacija) {
		super();
		this.id = rezervacija.getId();
		this.datumOd = rezervacija.getDatumOd();
		this.datumDo = rezervacija.getDatumDo();
		this.ukupnaCena = rezervacija.getUkupnaCena();
		this.hotelskaSoba = rezervacija.getHotelskaSoba();
		this.korisnik = rezervacija.getKorisnik();
	}
	
	public Rezervacije(Long id, Date datumOd, Date datumDo, float ukupnaCena, HotelskaSoba hotelskaSoba, Korisnik korisnik) {
		super();
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.hotelskaSoba = hotelskaSoba;
		this.korisnik = korisnik;
		this.ukupnaCena = ukupnaCena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}

	public Date getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}

	public HotelskaSoba getHotelskaSoba() {
		return hotelskaSoba;
	}

	public void setHotelskaSoba(HotelskaSoba hotelskaSoba) {
		this.hotelskaSoba = hotelskaSoba;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public float getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(float ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}
	
	

}
