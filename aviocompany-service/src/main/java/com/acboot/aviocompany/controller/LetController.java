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

import com.acboot.aviocompany.dto.LetDTO;
import com.acboot.aviocompany.service.LetService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/flight")
@Api(tags = "")
public class LetController
{
	@Autowired
	private LetService letService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<LetDTO> getLet(@PathVariable Long id)
	{
		System.out.println("getLet()");
		
		LetDTO LetDto = letService.findById(id);
		
		return (LetDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<LetDTO>(LetDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<LetDTO>> getAllLetovi()
	{
		System.out.println("getAllLetovi()");
		
		List<LetDTO> listDto = letService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<LetDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<LetDTO> addLet(@RequestBody LetDTO dto)
	{
		System.out.println("addLet()");
		
		return (letService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<LetDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<LetDTO> updateLet(@PathVariable("id") Long id, @RequestBody LetDTO dto)
	{
		System.out.println("updateLet()");
		
		LetDTO avio = letService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<LetDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteLet(@PathVariable("id") Long id)
	{
		System.out.println("deleteLet()");
		
		return (!letService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}

}