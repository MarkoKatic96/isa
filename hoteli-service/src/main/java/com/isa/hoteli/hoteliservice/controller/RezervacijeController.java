package com.isa.hoteli.hoteliservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.service.RezervacijeService;

@RestController
@RequestMapping("/rezervacija")
public class RezervacijeController {

	@Autowired
	private RezervacijeService rezervacijeService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<RezervacijeDTO>> getReservations(){
		List<RezervacijeDTO> dto = new ArrayList<>();
		List<Rezervacije> lista = rezervacijeService.getRezervations();
		for (Rezervacije item : lista) {
			dto.add(new RezervacijeDTO(item));
		}
		return new ResponseEntity<List<RezervacijeDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<RezervacijeDTO>> getReservationsFromHotelRooms(@PathVariable("id") Long id){
		List<RezervacijeDTO> dto = new ArrayList<>();
		List<Rezervacije> lista = rezervacijeService.getReservationsFromHotelRoom(id);
		for (Rezervacije item : lista) {
			dto.add(new RezervacijeDTO(item));
		}
		return new ResponseEntity<List<RezervacijeDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<RezervacijeDTO> getReservationsById(@PathVariable("id") Long id){
		if(rezervacijeService.getReservationById(id)!=null) {
			RezervacijeDTO dto = new RezervacijeDTO(rezervacijeService.getReservationById(id));
			return new ResponseEntity<RezervacijeDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<RezervacijeDTO> createReservation(@RequestBody RezervacijeDTO dto){
		Rezervacije obj = new Rezervacije(dto);
		RezervacijeDTO returnType = rezervacijeService.createReservation(obj);
		if(returnType!=null) {
			return new ResponseEntity<>(returnType, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteReservationById(@PathVariable("id") Long id){
		return new ResponseEntity<String>(rezervacijeService.deleteReservation(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<RezervacijeDTO> updateReservationById(@PathVariable("id") Long id, @RequestBody RezervacijeDTO dto){
		Rezervacije obj = new Rezervacije(dto);
		RezervacijeDTO returnTip = rezervacijeService.updatePrice(obj, id);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
