package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.OcenaHotelDTO;
import com.isa.hoteli.hoteliservice.dto.OcenaHotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.model.OcenaHotel;
import com.isa.hoteli.hoteliservice.model.OcenaHotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.OcenaHotelRepository;
import com.isa.hoteli.hoteliservice.repository.OcenaHotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;

@Component
public class OcenaService {
	
	@Autowired
	private OcenaHotelRepository ocenaHotelRepository;
	
	@Autowired
	private OcenaHotelskaSobaRepository ocenaHotelskaSobaRepository;
	
	@Autowired
	private RezervacijeRepository rezervacijeRepository;
	
	public List<OcenaHotel> getHotelRatings(){
		return ocenaHotelRepository.findAll();
	}
	
	public List<OcenaHotelskaSoba> getRoomRatings(){
		return ocenaHotelskaSobaRepository.findAll();
	}
	
	public OcenaHotelDTO createHotelRating(OcenaHotel ocena) {
		Optional<Rezervacije> r = rezervacijeRepository.findById(ocena.getRezervacijaId());
		if(r.isPresent()) {
			OcenaHotel o = ocenaHotelRepository.vecOcenjeno(ocena.getRezervacijaId());
			if(o==null) {
				return new OcenaHotelDTO(ocenaHotelRepository.save(ocena));
			}
			return null;
		}
		return null;//rezervacija ne postoji
	}
	
	public OcenaHotelskaSobaDTO createHotelRoomRating(OcenaHotelskaSoba ocena) {
		Optional<Rezervacije> r = rezervacijeRepository.findById(ocena.getRezervacijaId());
		if(r.isPresent()) {
			OcenaHotelskaSoba o = ocenaHotelskaSobaRepository.vecOcenjeno(ocena.getRezervacijaId());
			if(o==null) {
				return new OcenaHotelskaSobaDTO(ocenaHotelskaSobaRepository.save(ocena));
			}
			return null; //vec ocenjena soba
		}
		return null;
	}
	
	public float getMeanHotelRating(Long id) {
		if(!ocenaHotelRepository.ocene(id).isEmpty()) {
			float mean = ocenaHotelRepository.prosek(id);
			return mean;
		}else {
			return (float) 0.0;
		}
	}
	
	public float getMeanRoomRating(Long id) {
		if(!ocenaHotelskaSobaRepository.ocene(id).isEmpty()) {
			float mean = ocenaHotelskaSobaRepository.prosek(id);
			return mean;
		}else {
			return (float) 0.0;
		}
	}

}
