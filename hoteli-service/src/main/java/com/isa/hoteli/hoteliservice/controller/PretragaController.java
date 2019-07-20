package com.isa.hoteli.hoteliservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.service.HotelService;
import com.isa.hoteli.hoteliservice.service.PretragaService;

@RestController
@RequestMapping("/pretraga")
public class PretragaController {
	
	@Autowired
	private PretragaService pretragaService;
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<List<HotelDTO>> getHotels(@RequestBody Pretraga pretraga){
		List<HotelDTO> dto = new ArrayList<HotelDTO>();
		List<Hotel> lista = pretragaService.getSearch(pretraga);
		for (Hotel hotel : lista) {
			dto.add(new HotelDTO(hotel));
		}
		return new ResponseEntity<List<HotelDTO>>(dto, HttpStatus.OK);
	}

}
