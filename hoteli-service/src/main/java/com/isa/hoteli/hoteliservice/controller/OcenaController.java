package com.isa.hoteli.hoteliservice.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.dto.OcenaHotelDTO;
import com.isa.hoteli.hoteliservice.dto.OcenaHotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.OcenaHotel;
import com.isa.hoteli.hoteliservice.model.OcenaHotelskaSoba;
import com.isa.hoteli.hoteliservice.service.OcenaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ocena")
public class OcenaController {
	
	@Autowired
	private OcenaService ocenaService;
	
	@RequestMapping(value="/hotel/all", method = RequestMethod.GET)
	public ResponseEntity<List<OcenaHotelDTO>> getHotelRatings(){
		List<OcenaHotelDTO> dto = new ArrayList<>();
		List<OcenaHotel> lista = ocenaService.getHotelRatings();
		for (OcenaHotel item : lista) {
			dto.add(new OcenaHotelDTO(item));
		}
		return new ResponseEntity<List<OcenaHotelDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/soba/all", method = RequestMethod.GET)
	public ResponseEntity<List<OcenaHotelskaSobaDTO>> getRoomRatings(){
		List<OcenaHotelskaSobaDTO> dto = new ArrayList<>();
		List<OcenaHotelskaSoba> lista = ocenaService.getRoomRatings();
		for (OcenaHotelskaSoba item : lista) {
			dto.add(new OcenaHotelskaSobaDTO(item));
		}
		return new ResponseEntity<List<OcenaHotelskaSobaDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/hotel/", method = RequestMethod.POST)
	public ResponseEntity<OcenaHotelDTO> createHotelRating(@RequestBody OcenaHotelDTO dto){
		OcenaHotel obj = new OcenaHotel(dto);
		obj.setDatumOcene(new Date(System.currentTimeMillis()));
		OcenaHotelDTO returnType = ocenaService.createHotelRating(obj);
		if(returnType!=null) {
			return new ResponseEntity<>(returnType, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/soba/", method = RequestMethod.POST)
	public ResponseEntity<OcenaHotelskaSobaDTO> createRoomRating(@RequestBody OcenaHotelskaSobaDTO dto){
		OcenaHotelskaSoba obj = new OcenaHotelskaSoba(dto);
		obj.setDatumOcene(new Date(System.currentTimeMillis()));
		OcenaHotelskaSobaDTO returnType = ocenaService.createHotelRoomRating(obj);
		if(returnType!=null) {
			return new ResponseEntity<>(returnType, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/prosek/hotel/{id}", method = RequestMethod.GET)
	public ResponseEntity<Float> hotelMeanById(@PathVariable("id") Long id){
		float f = ocenaService.getMeanHotelRating(id);
		Float returnFloat = new Float(f);
		return new ResponseEntity<Float>(returnFloat, HttpStatus.OK);
	}
	
	@RequestMapping(value="/prosek/soba/{id}", method = RequestMethod.GET)
	public ResponseEntity<Float> roomMeanById(@PathVariable("id") Long id){
		float f = ocenaService.getMeanRoomRating(id);
		Float returnFloat = new Float(f);
		return new ResponseEntity<Float>(returnFloat, HttpStatus.OK);
	}

}
