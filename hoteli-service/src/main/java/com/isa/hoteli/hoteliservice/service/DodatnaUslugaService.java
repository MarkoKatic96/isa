package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.DodatnaUslugaDTO;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.repository.DodatnaUslugaRepository;

@Component
public class DodatnaUslugaService {
	
	@Autowired
	private DodatnaUslugaRepository dodatnaUslugaRepository;
	
	public List<DodatnaUsluga> getServices(){
		return dodatnaUslugaRepository.findAll();
	}
	
	public List<DodatnaUsluga> getServicesFromHotel(Long id){
		return dodatnaUslugaRepository.getAllFromHotel(id);
	}
	
	public DodatnaUsluga getServiceById(Long id){
		return dodatnaUslugaRepository.getOne(id);
	}
	
	public DodatnaUslugaDTO createService(DodatnaUsluga obj) {
		return new DodatnaUslugaDTO(dodatnaUslugaRepository.save(obj));
	}
	
	public String deleteService(Long id) {
		dodatnaUslugaRepository.deleteById(id);
		return "Uspesno obrisana usluga sa id: " + id;
	}
	
	public DodatnaUslugaDTO updateService(DodatnaUsluga obj, Long id) {
		DodatnaUsluga obj1 = dodatnaUslugaRepository.getOne(id);
		if(obj1!=null) {
			obj1.setCena(obj.getCena());
			obj1.setNaziv(obj.getNaziv());
			obj1.setPopust(obj.getPopust());
			obj1.setHotel(obj.getHotel());
			dodatnaUslugaRepository.save(obj1);
			return new DodatnaUslugaDTO(obj1);
		}
		return null;
	}

}
