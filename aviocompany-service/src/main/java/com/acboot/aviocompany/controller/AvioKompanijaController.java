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

import com.acboot.aviocompany.dto.AvioKompanijaDTO;
import com.acboot.aviocompany.service.AvioKompanijaService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/aviocompany")
@Api(tags = "")
public class AvioKompanijaController
{
	@Autowired
	private AvioKompanijaService avioService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<AvioKompanijaDTO> getAvioKompanija(@PathVariable Long id)
	{
		System.out.println("getAvioKompanija()");
		
		AvioKompanijaDTO avioDto = avioService.findById(id);
		
		return (avioDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<AvioKompanijaDTO>(avioDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<AvioKompanijaDTO>> getAllAvioKompanije()
	{
		System.out.println("getAllAvioKompanije()");
		
		List<AvioKompanijaDTO> listDto = avioService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<AvioKompanijaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<AvioKompanijaDTO> addAvioKompanija(@RequestBody AvioKompanijaDTO dto)
	{
		System.out.println("addAvioKompanija()");
		
		return (avioService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<AvioKompanijaDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<AvioKompanijaDTO> updateAvioKompanija(@PathVariable("id") Long id, @RequestBody AvioKompanijaDTO dto)
	{
		System.out.println("updateAvioKompanija()");
		
		AvioKompanijaDTO avio = avioService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<AvioKompanijaDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteAvioKompanija(@PathVariable("id") Long id)
	{
		System.out.println("deleteAvioKompanija()");
		
		return (!avioService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}
	
	
	/*
	 * OSTALE OPERACIJE
	 */
	
	/*
	 * Trazi prosecnu ocenu za jednu aviokompaniju na osnovu prosecnih ocena letova
	 */
	@GetMapping("/getavgrating/{id}")
	public ResponseEntity<Float> getSrednjaOcenaAvioKompanije(@PathVariable("id") Long id)
	{
		System.out.println("getSrednjaOcenaAvioKompanije()");
		
		Float avg = avioService.getSrednjaOcenaAvioKompanije(id);
		
		return(avg == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<Float>(avg, HttpStatus.OK);
	}

}
