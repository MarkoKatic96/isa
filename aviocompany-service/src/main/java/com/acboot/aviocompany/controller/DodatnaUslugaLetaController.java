package com.acboot.aviocompany.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acboot.aviocompany.dto.DodatnaUslugaDTO;
import com.acboot.aviocompany.service.DodatnaUslugaService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/service")
@Api(tags = "")
public class DodatnaUslugaController 
{
	@Autowired
	private DodatnaUslugaService uslugaService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<DodatnaUslugaDTO> getDodatnaUsluga(@PathVariable Long id)
	{
		System.out.println("getDodatnaUsluga()");
		
		DodatnaUslugaDTO uslDto = uslugaService.findById(id);
		
		return (uslDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<DodatnaUslugaDTO>(uslDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<DodatnaUslugaDTO>> getAllDodatneUsluge()
	{
		System.out.println("getAllDodatneUsluge()");
		
		List<DodatnaUslugaDTO> listDto = uslugaService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<DodatnaUslugaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<DodatnaUslugaDTO> addDodatnaUsluga(@RequestBody DodatnaUslugaDTO dto)
	{
		System.out.println("addDodatnaUsluga()");
		
		return (uslugaService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<DodatnaUslugaDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<DodatnaUslugaDTO> updateDodatnaUsluga(@PathVariable("id") Long id, @RequestBody DodatnaUslugaDTO dto)
	{
		System.out.println("updateDodatnaUsluga()");
		
		DodatnaUslugaDTO avio = uslugaService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<DodatnaUslugaDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteDodatnaUsluga(@PathVariable("id") Long id)
	{
		System.out.println("deleteDodatnaUsluga()");
		
		return (!uslugaService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}
}
