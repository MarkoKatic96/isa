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

import com.isa.hoteli.hoteliservice.dto.HotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.dto.HotelskaSobaInfoDTO;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.model.OcenaHotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.model.Rola;
import com.isa.hoteli.hoteliservice.service.CenaNocenjaService;
import com.isa.hoteli.hoteliservice.service.HotelskaSobaService;
import com.isa.hoteli.hoteliservice.service.KorisnikService;
import com.isa.hoteli.hoteliservice.service.OcenaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/sobe")
public class HotelskaSobaController {
	
	@Autowired
	private HotelskaSobaService hotelskaSobaService;
	
	@Autowired
	private OcenaService ocenaService;
	
	@Autowired
	private CenaNocenjaService cenaNocenjaService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value="/admin/all", method = RequestMethod.GET)
	public ResponseEntity<List<HotelskaSobaDTO>> getRooms(){
		List<HotelskaSobaDTO> dto = new ArrayList<>();
		List<HotelskaSoba> lista = hotelskaSobaService.getRooms();
		for (HotelskaSoba item : lista) {
			dto.add(new HotelskaSobaDTO(item));
		}
		return new ResponseEntity<List<HotelskaSobaDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<HotelskaSobaInfoDTO>> getRoomsInfo(){
		List<HotelskaSobaInfoDTO> dto = new ArrayList<>();
		List<HotelskaSoba> lista = hotelskaSobaService.getRooms();
		for (HotelskaSoba item : lista) {
			dto.add(new HotelskaSobaInfoDTO(item.getId(), item.getBrojSobe(), item.getSprat(), item.getBrojKreveta(), item.getOriginalnaCena(), item.getHotel(), item.getTipSobe(), cenaNocenjaService.getValidPriceFromHotelRoom(item.getId()), ocenaService.getMeanRoomRating(item.getId())));
		}
		return new ResponseEntity<List<HotelskaSobaInfoDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/all/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HotelskaSobaDTO>> getRoomsFromHotelAdmin(@PathVariable("id") Long id, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==id) {
			List<HotelskaSobaDTO> dto = new ArrayList<>();
			List<HotelskaSoba> lista = hotelskaSobaService.getRoomsFromHotel(id);
			for (HotelskaSoba item : lista) {
				dto.add(new HotelskaSobaDTO(item));
			}
			return new ResponseEntity<List<HotelskaSobaDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/all/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HotelskaSobaInfoDTO>> getRoomsFromHotel(@PathVariable("id") Long id){
		List<HotelskaSobaInfoDTO> dto = new ArrayList<>();
		List<HotelskaSoba> lista = hotelskaSobaService.getRoomsFromHotel(id);
		for (HotelskaSoba item : lista) {
			dto.add(new HotelskaSobaInfoDTO(item.getId(), item.getBrojSobe(), item.getSprat(), item.getBrojKreveta(), item.getOriginalnaCena(), item.getHotel(), item.getTipSobe(), cenaNocenjaService.getValidPriceFromHotelRoom(item.getId()), ocenaService.getMeanRoomRating(item.getId())));
		}
		return new ResponseEntity<List<HotelskaSobaInfoDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<HotelskaSobaDTO> getRoomById(@PathVariable("id") Long id){
		if(hotelskaSobaService.getRoomById(id)!=null) {
			HotelskaSobaDTO dto = new HotelskaSobaDTO(hotelskaSobaService.getRoomById(id));
			return new ResponseEntity<HotelskaSobaDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<HotelskaSobaDTO> createRoom(@RequestBody HotelskaSobaDTO dto, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && dto.getHotel().getId()==k.getZaduzenZaId()) {
			HotelskaSoba obj = new HotelskaSoba(dto);
			HotelskaSobaDTO returnType = hotelskaSobaService.createRoom(obj);
			if(returnType!=null) {
				return new ResponseEntity<>(returnType, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteRoomById(@PathVariable("id") Long id, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==hotelskaSobaService.getRoomById(id).getHotel().getId()) {
			return new ResponseEntity<String>(hotelskaSobaService.deleteRoom(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HotelskaSobaDTO> updateRoomById(@PathVariable("id") Long id, @RequestBody HotelskaSobaDTO dto, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==hotelskaSobaService.getRoomById(id).getHotel().getId()) {
			HotelskaSoba obj = new HotelskaSoba(dto);
			HotelskaSobaDTO returnTip = hotelskaSobaService.updateRoom(obj, id);
			if(returnTip!=null) {
				return new ResponseEntity<>(returnTip, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/free/{id}", method = RequestMethod.POST)
	public ResponseEntity<List<HotelskaSobaInfoDTO>> getFreeRooms(@PathVariable("id") Long id, @RequestBody Pretraga pretraga){
		List<HotelskaSobaInfoDTO> dto = new ArrayList<>();
		List<HotelskaSoba> lista = hotelskaSobaService.getAllFreeRoomsFromHotel(id, pretraga.getDatumOd(), pretraga.getDatumDo());
		for (HotelskaSoba item : lista) {
			dto.add(new HotelskaSobaInfoDTO(item.getId(), item.getBrojSobe(), item.getSprat(), item.getBrojKreveta(), item.getOriginalnaCena(), item.getHotel(), item.getTipSobe(), cenaNocenjaService.getValidPriceFromHotelRoom(item.getId()), ocenaService.getMeanRoomRating(item.getId())));
		}
		return new ResponseEntity<List<HotelskaSobaInfoDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/reserved/{id}", method = RequestMethod.POST)
	public ResponseEntity<List<HotelskaSobaInfoDTO>> getReservedRooms(@PathVariable("id") Long id, @RequestBody Pretraga pretraga){
		List<HotelskaSobaInfoDTO> dto = new ArrayList<>();
		List<HotelskaSoba> lista = hotelskaSobaService.getAllReservedRoomsFromHotel(id, pretraga.getDatumOd(), pretraga.getDatumDo());
		for (HotelskaSoba item : lista) {
			dto.add(new HotelskaSobaInfoDTO(item.getId(), item.getBrojSobe(), item.getSprat(), item.getBrojKreveta(), item.getOriginalnaCena(), item.getHotel(), item.getTipSobe(), cenaNocenjaService.getValidPriceFromHotelRoom(item.getId()), ocenaService.getMeanRoomRating(item.getId())));
		}
		return new ResponseEntity<List<HotelskaSobaInfoDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/brza/{id}", method = RequestMethod.POST)
	public ResponseEntity<List<HotelskaSobaInfoDTO>> getFastRooms(@PathVariable("id") Long id, @RequestBody Pretraga pretraga){
		List<HotelskaSobaInfoDTO> dto = new ArrayList<>();
		List<HotelskaSoba> lista = hotelskaSobaService.getAllFreeRoomsFromHotelWithDiscount(id, pretraga.getDatumOd(), pretraga.getDatumDo());
		for (HotelskaSoba item : lista) {
			dto.add(new HotelskaSobaInfoDTO(item.getId(), item.getBrojSobe(), item.getSprat(), item.getBrojKreveta(), item.getOriginalnaCena(), item.getHotel(), item.getTipSobe(), cenaNocenjaService.getValidPriceFromHotelRoom(item.getId()), ocenaService.getMeanRoomRating(item.getId())));
		}
		return new ResponseEntity<List<HotelskaSobaInfoDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/slobodne/{id}", method = RequestMethod.POST)
	public ResponseEntity<List<HotelskaSobaInfoDTO>> getFreeRoomsFromHotel(@PathVariable("id") Long id, @RequestBody Pretraga pretraga){
		List<HotelskaSobaInfoDTO> dto = new ArrayList<>();
		List<HotelskaSoba> lista = hotelskaSobaService.getAllFreeRoomsFromHotel(id, pretraga.getDatumOd(), pretraga.getDatumDo());
		for (HotelskaSoba item : lista) {
			dto.add(new HotelskaSobaInfoDTO(item.getId(), item.getBrojSobe(), item.getSprat(), item.getBrojKreveta(), item.getOriginalnaCena(), item.getHotel(), item.getTipSobe(), cenaNocenjaService.getValidPriceFromHotelRoom(item.getId()), ocenaService.getMeanRoomRating(item.getId())));
		}
		return new ResponseEntity<List<HotelskaSobaInfoDTO>>(dto, HttpStatus.OK);
	}

}
