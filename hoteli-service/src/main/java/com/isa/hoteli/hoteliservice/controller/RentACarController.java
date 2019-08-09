package com.isa.hoteli.hoteliservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.RentACar;
import com.isa.hoteli.hoteliservice.service.RentACarService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rent")
public class RentACarController {
	
	@Autowired
	private RentACarService rentACarService;
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<RentACar> createRent(@RequestBody RentACar rentACar){
		RentACar rentACar2 = rentACarService.createRentACar(rentACar);
		if(rentACar2!=null) {
			return new ResponseEntity<>(rentACar2, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
