package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;

@Component
public class RezervacijeService {
	
	@Autowired
	private RezervacijeRepository rezervacijeRepository;
	
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
		return new RezervacijeDTO(rezervacijeRepository.save(obj));
	}
	
	public String deleteReservation(Long id) {
		rezervacijeRepository.deleteById(id);
		return "Uspesno obrisana rezervacija sa id: " + id;
	}
	
	public RezervacijeDTO updatePrice(Rezervacije obj, Long id) {
		Optional<Rezervacije> obj1 = rezervacijeRepository.findById(id);
		if(obj1.isPresent()) {
			obj1.get().setKorisnik(obj.getKorisnik());
			obj1.get().setDatumDo(obj.getDatumDo());
			obj1.get().setDatumOd(obj.getDatumOd());
			obj1.get().setUkupnaCena(obj.getUkupnaCena());
			obj1.get().setHotelskaSoba(obj.getHotelskaSoba());
			rezervacijeRepository.save(obj1.get());
			return new RezervacijeDTO(obj1.get());
		}
		return null;
	}

}
