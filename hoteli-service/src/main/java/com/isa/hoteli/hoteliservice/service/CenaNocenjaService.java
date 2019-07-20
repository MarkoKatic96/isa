package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.HotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.repository.CenaNocenjaRepository;

@Component
public class CenaNocenjaService {
	
	@Autowired
	private CenaNocenjaRepository cenaNocenjaRepository;
	
	public List<CenaNocenja> getPrices(){
		return cenaNocenjaRepository.findAll();
	}
	
	public List<CenaNocenja> getPricesFromHotelRoom(Long id){
		return cenaNocenjaRepository.getAllFromHotelRoom(id);
	}
	
	public CenaNocenja getPriceById(Long id){
		return cenaNocenjaRepository.getOne(id);
	}
	
	public CenaNocenjaDTO createPrice(CenaNocenja obj) {
		return new CenaNocenjaDTO(cenaNocenjaRepository.save(obj));
	}
	
	public String deletePrice(Long id) {
		cenaNocenjaRepository.deleteById(id);
		return "Uspesno obrisana cena sa id: " + id;
	}
	
	public CenaNocenjaDTO updatePrice(CenaNocenja obj, Long id) {
		Optional<CenaNocenja> obj1 = cenaNocenjaRepository.findById(id);
		if(obj1.isPresent()) {
			obj1.get().setCenaNocenja(obj.getCenaNocenja());
			obj1.get().setDatumDo(obj.getDatumDo());
			obj1.get().setDatumOd(obj.getDatumOd());
			obj1.get().setHotelskaSoba(obj.getHotelskaSoba());
			cenaNocenjaRepository.save(obj1.get());
			return new CenaNocenjaDTO(obj1.get());
		}
		return null;
	}
}
