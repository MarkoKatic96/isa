package com.isa.hoteli.hoteliservice.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
/*
 {
    "naziv": "h",
    "hotel": {
        "id": 2
    },
    "hotelskaSobaList": []
}
 */
@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String naziv;
	
	private String adresa;
	
	private String opis;
	
	@OneToMany(mappedBy="hotel")
	private List<Cenovnik> cenovnikList;
	
	@OneToMany(mappedBy="hotel")
	private List<HotelskaSoba> hotelskaSobaList;
	
	@OneToMany(mappedBy="hotel")
	private List<DodatnaUsluga> dodatnaUslugaList;
	
	/*@OneToMany(mappedBy="hotel")
	private List<TipSobe> tipSobeList;*/
	
	private String konfiguracija;
	
	public Hotel() {

	}
	
	public Hotel(HotelDTO hotel) {
		super();
		this.id = hotel.getId();
		this.naziv = hotel.getNaziv();
		this.adresa = hotel.getAdresa();
		this.opis = hotel.getOpis();
		this.cenovnikList = hotel.getCenovnikList();
		this.hotelskaSobaList = hotel.getHotelskaSobaList();
		this.dodatnaUslugaList = hotel.getDodatnaUslugaList();
		//this.tipSobeList = hotel.getTipSobeList();
		this.konfiguracija = hotel.getKonfiguracija();
	}

	public Hotel(Long id, String naziv, String adresa, String opis, List<Cenovnik> cenovnikList,
			List<HotelskaSoba> hotelskaSobaList, List<DodatnaUsluga> dodatnaUslugaList/*, List<TipSobe> tipSobeList*/,
			String konfiguracija) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.cenovnikList = cenovnikList;
		this.hotelskaSobaList = hotelskaSobaList;
		this.dodatnaUslugaList = dodatnaUslugaList;
		//this.tipSobeList = tipSobeList;
		this.konfiguracija = konfiguracija;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}


	public List<Cenovnik> getCenovnik() {
		return cenovnikList;
	}

	public void setCenovnik(List<Cenovnik> cenovnik) {
		this.cenovnikList = cenovnik;
	}

	public String getKonfiguracija() {
		return konfiguracija;
	}

	public void setKonfiguracija(String konfiguracija) {
		this.konfiguracija = konfiguracija;
	}

	public List<Cenovnik> getCenovnikList() {
		return cenovnikList;
	}

	public void setCenovnikList(List<Cenovnik> cenovnikList) {
		this.cenovnikList = cenovnikList;
	}

	public List<HotelskaSoba> getHotelskaSobaList() {
		return hotelskaSobaList;
	}

	public void setHotelskaSobaList(List<HotelskaSoba> hotelskaSobaList) {
		this.hotelskaSobaList = hotelskaSobaList;
	}

	public List<DodatnaUsluga> getDodatnaUslugaList() {
		return dodatnaUslugaList;
	}

	public void setDodatnaUslugaList(List<DodatnaUsluga> dodatnaUslugaList) {
		this.dodatnaUslugaList = dodatnaUslugaList;
	}

	/*public List<TipSobe> getTipSobeList() {
		return tipSobeList;
	}

	public void setTipSobeList(List<TipSobe> tipSobeList) {
		this.tipSobeList = tipSobeList;
	}*/
	
	
	
}
