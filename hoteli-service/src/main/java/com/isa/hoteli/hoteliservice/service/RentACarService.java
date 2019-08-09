package com.isa.hoteli.hoteliservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.model.RentACar;
import com.isa.hoteli.hoteliservice.repository.RentACarRepository;

@Component
public class RentACarService {
	
	@Autowired
	RentACarRepository rentACarRepository;
	
	public RentACar createRentACar(RentACar rentACar) {
		return rentACarRepository.save(rentACar);
	}

}
