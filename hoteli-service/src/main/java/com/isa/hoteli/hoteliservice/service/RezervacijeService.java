package com.isa.hoteli.hoteliservice.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;
import com.isa.hoteli.hoteliservice.repository.HotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;

@Component
public class RezervacijeService {
	
	@Autowired
	private RezervacijeRepository rezervacijeRepository;
	
	@Autowired
	private HotelskaSobaRepository hotelskaSobaRepository;
	
	public List<Rezervacije> getRezervations(){
		return rezervacijeRepository.findAll();
	}
	
	public List<Rezervacije> getReservationsFromHotelRoom(Long id){
		return rezervacijeRepository.getAllFromHotelRoom(id);
	}
	
	public Rezervacije getReservationById(Long id){
		return rezervacijeRepository.getOne(id);
	}
	
	public RezervacijeDTO createReservation(Rezervacije obj) {
		Optional<HotelskaSoba> hs = hotelskaSobaRepository.findById(obj.getHotelskaSoba().getId());
		List<Rezervacije> lista = rezervacijeRepository.findKonfliktRezervacije(obj.getHotelskaSoba().getId(), obj.getDatumOd(), obj.getDatumDo());
		if(hs.isPresent() && lista.isEmpty()) {
			obj.setHotel(hs.get().getHotel());
			return new RezervacijeDTO(rezervacijeRepository.save(obj));
		}
		return null;
	}
	
	public String deleteReservation(Long id) {
		rezervacijeRepository.deleteById(id);
		return "Uspesno obrisana rezervacija sa id: " + id;
	}
	
	public RezervacijeDTO updateReservation(Rezervacije obj, Long id) {
		Optional<Rezervacije> obj1 = rezervacijeRepository.findById(id);
		if(obj1.isPresent()) {
			obj1.get().setKorisnik(obj.getKorisnik());
			obj1.get().setDatumDo(obj.getDatumDo());
			obj1.get().setDatumOd(obj.getDatumOd());
			obj1.get().setUkupnaCena(obj.getUkupnaCena());
			obj1.get().setBrojOsoba(obj.getBrojOsoba());
			obj1.get().setHotelskaSoba(obj.getHotelskaSoba());
			rezervacijeRepository.save(obj1.get());
			return new RezervacijeDTO(obj1.get());
		}
		return null;
	}
	
	public int dnevnaPosecenost(Long id, Date date) {
		return rezervacijeRepository.dnevnaPosecenost(id, date);
	}

}
