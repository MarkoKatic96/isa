package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;

@Component
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	public List<Hotel> getHotels(){
		return hotelRepository.findAll();
	}
	
	public Hotel getHotelById(Long id){
		return hotelRepository.getOne(id);
	}
	
	public HotelDTO createHotel(Hotel hotel) {
		return new HotelDTO(hotelRepository.save(hotel));
	}
	
	public String deleteHotel(Long id) {
		hotelRepository.deleteById(id);
		return "Uspesno obrisan hotel sa id: " + id;
	}
	
	public HotelDTO updateHotel(Hotel hotel, Long id) {
		Optional<Hotel> hotel1 = hotelRepository.findById(id);
		if(hotel1.isPresent()) {
			hotel1.get().setNaziv(hotel.getNaziv());
			hotel1.get().setAdresa(hotel.getAdresa());
			hotel1.get().setOpis(hotel.getOpis());
			hotel1.get().setKonfiguracija(hotel.getKonfiguracija());
			hotelRepository.save(hotel1.get());
			return new HotelDTO(hotel1.get());
		}
		return null;
	}
	
}
