package com.isa.hoteli.hoteliservice.dto;

import java.sql.Date;

import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Korisnik;

public class RezervacijeDTO {
	
	private Long id;
	private Date datumOd;
	private Date datumDo;
	private HotelskaSoba hotelskaSoba;
    private Korisnik korisnik;
    
	public RezervacijeDTO() {

	}
    
	public RezervacijeDTO(Long id, Date datumOd, Date datumDo, HotelskaSoba hotelskaSoba, Korisnik korisnik) {
		super();
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.hotelskaSoba = hotelskaSoba;
		this.korisnik = korisnik;
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
    
    
    
}
