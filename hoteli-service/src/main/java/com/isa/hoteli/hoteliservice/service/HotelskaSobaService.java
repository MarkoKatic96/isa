package com.isa.hoteli.hoteliservice.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.HotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.CenaNocenjaRepository;
import com.isa.hoteli.hoteliservice.repository.HotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;

@Component
public class HotelskaSobaService {
	
	@Autowired
	private HotelskaSobaRepository hotelskaSobaRepository;
	
	@Autowired
	private RezervacijeRepository rezervacijeRepository;
	
	@Autowired
	private CenaNocenjaRepository cenaNocenjaRepository;
	
	public List<HotelskaSoba> getRooms(){
		return hotelskaSobaRepository.findAll();
	}
	
	public List<HotelskaSoba> getRoomsFromHotel(Long id){
		return hotelskaSobaRepository.getAllFromHotel(id);
	}
	
	public List<HotelskaSoba> getFreeRoomsFromHotel(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()) {
				returnList.add(hotelskaSoba);
			}
		}
		return returnList;

	}
	
	public HotelskaSoba getRoomById(Long id){
		return hotelskaSobaRepository.getOne(id);
	}
	
	public HotelskaSobaDTO createRoom(HotelskaSoba soba) {
		if(hotelskaSobaRepository.getRoomWithNumber(soba.getHotel().getId(), soba.getBrojSobe())==null) {
			return new HotelskaSobaDTO(hotelskaSobaRepository.save(soba));
		}
		return null;
	}
	
	public String deleteRoom(Long id) {
		Date datum = new Date(System.currentTimeMillis());
		if(rezervacijeRepository.neMozeMenjatiBrisati(id, datum).isEmpty()) {
			hotelskaSobaRepository.deleteById(id);
			return "Uspesno obrisana soba sa id: " + id;
		}else {
			return "Soba se ne moze obrisati jer postoje rezervacije za nju.";
		}
	}
	
	public HotelskaSobaDTO updateRoom(HotelskaSoba soba, Long id) {
		Date datum = new Date(System.currentTimeMillis());
		if(rezervacijeRepository.neMozeMenjatiBrisati(id, datum).isEmpty() && hotelskaSobaRepository.getRoomWithNumber(soba.getHotel().getId(), soba.getBrojSobe())==null){
			Optional<HotelskaSoba> soba1 = hotelskaSobaRepository.findById(id);
			if(soba1.isPresent()) {
				soba1.get().setBrojKreveta(soba.getBrojKreveta());
				soba1.get().setBrojSobe(soba.getBrojSobe());
				soba1.get().setSprat(soba.getSprat());
				soba1.get().setOriginalnaCena(soba.getOriginalnaCena());
				soba1.get().setTipSobe(soba.getTipSobe());
				soba1.get().setHotel(soba.getHotel());
				hotelskaSobaRepository.save(soba1.get());
				return new HotelskaSobaDTO(soba1.get());
			}
		}
		
		return null;
	}
	
	public HotelskaSobaDTO updateRoomPrice(HotelskaSoba soba, Long id) {
			Optional<HotelskaSoba> soba1 = hotelskaSobaRepository.findById(id);
			if(soba1.isPresent()) {
				soba1.get().setOriginalnaCena(soba.getOriginalnaCena());
				hotelskaSobaRepository.save(soba1.get());
				return new HotelskaSobaDTO(soba1.get());
			}
		
		return null;
	}
	
	public List<HotelskaSoba> getAllFreeRoomsFromHotel(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()){
				returnList.add(hotelskaSoba);
			}
		}
		return returnList;
	}

	public List<HotelskaSoba> getAllReservedRoomsFromHotel(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(!rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()){
				returnList.add(hotelskaSoba);
			}
		}
		return returnList;
	}
	
	public List<HotelskaSoba> getAllFreeRoomsFromHotelWithDiscount(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		List<HotelskaSoba> returnList2 = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()){
				returnList.add(hotelskaSoba);
			}
		}
		for (HotelskaSoba hotelskaSoba : returnList) {
			if(cenaNocenjaRepository.getValidFromHotelRoom(hotelskaSoba.getId(), datumOd)!=null) {
				if(cenaNocenjaRepository.getValidFromHotelRoom(hotelskaSoba.getId(), datumOd).getCenaNocenja()<hotelskaSoba.getOriginalnaCena()) {
					returnList2.add(hotelskaSoba);
				}
			}
		}
		return returnList2;
	}
	
}
