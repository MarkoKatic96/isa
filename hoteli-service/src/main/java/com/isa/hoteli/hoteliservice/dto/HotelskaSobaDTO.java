package com.isa.hoteli.hoteliservice.dto;

import java.util.List;

import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Cenovnik;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.model.TipSobe;

public class HotelskaSobaDTO {

	private Long id;	
	private int brojSobe;	
	private int sprat;	
	private int brojKreveta;
	private Hotel hotel;
	private TipSobe tipSobe;
	private List<Cenovnik> cenovnikList;
	private List<CenaNocenja> cenaNocenjaList;
	private List<Rezervacije> rezervacijeList;
	
	public HotelskaSobaDTO() {

	}
	
	public HotelskaSobaDTO(Long id, int brojSobe, int sprat, int brojKreveta, Hotel hotel, TipSobe tipSobe,
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
