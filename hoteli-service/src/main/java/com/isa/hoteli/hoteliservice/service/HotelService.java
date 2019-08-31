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
		Hotel hotel1 = hotelRepository.getOne(id);
		if(hotel1!=null) {
			hotel1.setNaziv(hotel.getNaziv());
			hotel1.setAdresa(hotel.getAdresa());
			hotel1.setOpis(hotel.getOpis());
			hotel1.setKonfiguracija(hotel.getKonfiguracija());
			hotelRepository.save(hotel1);
			return new HotelDTO(hotel1);
		}
		return null;
	}
	
}
