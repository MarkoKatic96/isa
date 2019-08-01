package com.isa.hoteli.hoteliservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.HotelInfoDTO;
import com.isa.hoteli.hoteliservice.dto.HotelskaSobaInfoDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.model.Rola;
import com.isa.hoteli.hoteliservice.service.HotelService;
import com.isa.hoteli.hoteliservice.service.HotelskaSobaService;
import com.isa.hoteli.hoteliservice.service.KorisnikService;
import com.isa.hoteli.hoteliservice.service.OcenaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private OcenaService ocenaService;
	
	@Autowired
	private HotelskaSobaService hotelskaSobaService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value="/test/all", method = RequestMethod.GET)
	public ResponseEntity<List<HotelDTO>> getHotels(){
		List<HotelDTO> hoteliDTO = new ArrayList<>();
		List<Hotel> hoteli = hotelService.getHotels();
		for (Hotel hotel : hoteli) {
			hoteliDTO.add(new HotelDTO(hotel));
		}
		return new ResponseEntity<List<HotelDTO>>(hoteliDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<HotelInfoDTO>> getHotelsInfo(){
		List<HotelInfoDTO> hoteliDTO = new ArrayList<>();
		List<Hotel> hoteli = hotelService.getHotels();
		for (Hotel hotel : hoteli) {
			hoteliDTO.add(new HotelInfoDTO(hotel.getId(), hotel.getNaziv(), hotel.getAdresa(), hotel.getOpis(), ocenaService.getMeanHotelRating(hotel.getId())));
		}
		return new ResponseEntity<List<HotelInfoDTO>>(hoteliDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<HotelDTO> getHotelById(@PathVariable("id") Long id){
		if(hotelService.getHotelById(id)!=null) {
			HotelDTO hotelDTO = new HotelDTO(hotelService.getHotelById(id));
			return new ResponseEntity<HotelDTO>(hotelDTO, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO){
		Hotel hotel = new Hotel(hotelDTO);
		HotelDTO returnHotel = hotelService.createHotel(hotel);
		if(returnHotel!=null) {
			return new ResponseEntity<>(returnHotel, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteHotelById(@PathVariable("id") Long id){
		return new ResponseEntity<String>(hotelService.deleteHotel(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HotelDTO> updateHotelById(@PathVariable("id") Long id, @RequestBody HotelDTO hotelDTO, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==id) {
			Hotel hotel = new Hotel(hotelDTO);
			HotelDTO returnHotel = hotelService.updateHotel(hotel, id);
			if(returnHotel!=null) {
				return new ResponseEntity<>(returnHotel, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/brza", method = RequestMethod.POST)
	public ResponseEntity<List<HotelInfoDTO>> getFastHotels(@RequestBody Pretraga pretraga){
		List<HotelInfoDTO> hoteliDTO = new ArrayList<>();
		List<Hotel> lista = hotelService.getHotels();
		for (Hotel hotel : lista) {
			if(!hotelskaSobaService.getAllFreeRoomsFromHotelWithDiscount(hotel.getId(), pretraga.getDatumOd(), pretraga.getDatumDo()).isEmpty()) {
				hoteliDTO.add(new HotelInfoDTO(hotel.getId(), hotel.getNaziv(), hotel.getAdresa(), hotel.getOpis(), ocenaService.getMeanHotelRating(hotel.getId())));
			}
		}
		return new ResponseEntity<List<HotelInfoDTO>>(hoteliDTO, HttpStatus.OK);
	}
	
}
