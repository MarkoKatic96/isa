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
import com.isa.hoteli.hoteliservice.dto.DodatnaUslugaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.service.DodatnaUslugaService;

@RestController
@RequestMapping("/usluga")
public class DodatnaUslugaController {

	@Autowired
	private DodatnaUslugaService dodatnaUslugaService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<DodatnaUslugaDTO>> getServices(){
		List<DodatnaUslugaDTO> dto = new ArrayList<>();
		List<DodatnaUsluga> lista = dodatnaUslugaService.getServices();
		for (DodatnaUsluga item : lista) {
			dto.add(new DodatnaUslugaDTO(item));
		}
		return new ResponseEntity<List<DodatnaUslugaDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<DodatnaUslugaDTO>> getPricesFromHotel(@PathVariable("id") Long id){
		List<DodatnaUslugaDTO> dto = new ArrayList<>();
		List<DodatnaUsluga> lista = dodatnaUslugaService.getServicesFromHotel(id);
		for (DodatnaUsluga item : lista) {
			dto.add(new DodatnaUslugaDTO(item));
		}
		return new ResponseEntity<List<DodatnaUslugaDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<DodatnaUslugaDTO> getServicesById(@PathVariable("id") Long id){
		if(dodatnaUslugaService.getServiceById(id)!=null) {
			DodatnaUslugaDTO dto = new DodatnaUslugaDTO(dodatnaUslugaService.getServiceById(id));
			return new ResponseEntity<DodatnaUslugaDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<DodatnaUslugaDTO> createService(@RequestBody DodatnaUslugaDTO dto){
		DodatnaUsluga obj = new DodatnaUsluga(dto);
		DodatnaUslugaDTO returnType = dodatnaUslugaService.createService(obj);
		if(returnType!=null) {
			return new ResponseEntity<>(returnType, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteServiceById(@PathVariable("id") Long id){
		return new ResponseEntity<String>(dodatnaUslugaService.deleteService(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<DodatnaUslugaDTO> updateServiceById(@PathVariable("id") Long id, @RequestBody DodatnaUslugaDTO dto){
		DodatnaUsluga obj = new DodatnaUsluga(dto);
		DodatnaUslugaDTO returnTip = dodatnaUslugaService.updateService(obj, id);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
