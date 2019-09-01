package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.TipSobeDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.TipSobe;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;
import com.isa.hoteli.hoteliservice.repository.TipSobeRepository;

@Component
public class TipSobeService {

	@Autowired
	private TipSobeRepository tipSobeRepository;
	
	public List<TipSobe> getTypes(){
		return tipSobeRepository.findAll();
	}
	
	public List<TipSobe> getTypesFromHotel(Long id){
		return tipSobeRepository.getAllFromHotel(id);
	}
	
	public TipSobe getTipSobeById(Long id){
		return tipSobeRepository.getOne(id);
	}
	
	public TipSobeDTO createType(TipSobe tipSobe) {
		return new TipSobeDTO(tipSobeRepository.save(tipSobe));
	}
	
	public String deleteType(Long id) {
		tipSobeRepository.deleteById(id);
		return "Uspesno obrisan tip sa id: " + id;
	}
	
	public TipSobeDTO updateType(TipSobe tipSobe, Long id) {
		TipSobe tipSobe1 = tipSobeRepository.getOne(id);
		if(tipSobe1!=null) {
			tipSobe1.setNaziv(tipSobe.getNaziv());
			tipSobe1.setHotel(tipSobe.getHotel());
			tipSobeRepository.save(tipSobe1);
			return new TipSobeDTO(tipSobe1);
		}
		return null;
	}
	
}
