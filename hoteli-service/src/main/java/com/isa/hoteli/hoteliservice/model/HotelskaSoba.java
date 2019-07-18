package com.isa.hoteli.hoteliservice.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class HotelskaSoba {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int brojSobe;
	
	private int sprat;
	
	private int brojKreveta;
	
	@ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private Hotel hotel;
	
	@ManyToOne
    @JoinColumn(name="tipSobe_id", nullable=false)
    private TipSobe tipSobe;
	
	@OneToMany(mappedBy="hotelskaSoba")
	private List<Cenovnik> cenovnikList;
	
	@OneToMany(mappedBy="hotelskaSoba")
	private List<CenaNocenja> cenaNocenjaList;
	
	@OneToMany(mappedBy="hotelskaSoba")
	private List<Rezervacije> rezervacijeList;

	public HotelskaSoba() {

	}
	
	public HotelskaSoba(Long id, int brojSobe, int sprat, int brojKreveta, Hotel hotel, TipSobe tipSobe,
			List<Cenovnik> cenovnikList, List<CenaNocenja> cenaNocenjaList, List<Rezervacije> rezervacijeList) {
		super();
		this.id = id;
		this.brojSobe = brojSobe;
		this.sprat = sprat;
		this.brojKreveta = brojKreveta;
		this.hotel = hotel;
		this.tipSobe = tipSobe;
		this.cenovnikList = cenovnikList;
		this.cenaNocenjaList = cenaNocenjaList;
		this.rezervacijeList = rezervacijeList;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBrojSobe() {
		return brojSobe;
	}

	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}

	public int getSprat() {
		return sprat;
	}

	public void setSprat(int sprat) {
		this.sprat = sprat;
	}

	public int getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public TipSobe getTipSobe() {
		return tipSobe;
	}

	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}

	public List<Cenovnik> getCenovnikList() {
		return cenovnikList;
	}

	public void setCenovnikList(List<Cenovnik> cenovnikList) {
		this.cenovnikList = cenovnikList;
	}

	public List<CenaNocenja> getCenaNocenjaList() {
		return cenaNocenjaList;
	}

	public void setCenaNocenjaList(List<CenaNocenja> cenaNocenjaList) {
		this.cenaNocenjaList = cenaNocenjaList;
	}

	public List<Rezervacije> getRezervacijeList() {
		return rezervacijeList;
	}

	public void setRezervacijeList(List<Rezervacije> rezervacijeList) {
		this.rezervacijeList = rezervacijeList;
	}
	
	
	
}
