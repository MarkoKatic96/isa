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

import com.acboot.aviocompany.dto.KartaDTO;
import com.acboot.aviocompany.service.KartaService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ticket")
@Api(tags = "")
public class KartaController
{
	@Autowired
	private KartaService kartaService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<KartaDTO> getKarta(@PathVariable Long id)
	{
		System.out.println("getKarta()");
		
		KartaDTO kartaDto = kartaService.findById(id);
		
		return (kartaDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<KartaDTO>(kartaDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<KartaDTO>> getAllKarte()
	{
		System.out.println("getAllKarte()");
		
		List<KartaDTO> listDto = kartaService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<KartaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<KartaDTO> addKarta(@RequestBody KartaDTO dto)
	{
		System.out.println("addKarta()");
		
		return (kartaService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<KartaDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<KartaDTO> updateKarta(@PathVariable("id") Long id, @RequestBody KartaDTO dto)
	{
		System.out.println("updateKarta()");
		
		KartaDTO avio = kartaService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<KartaDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteKarta(@PathVariable("id") Long id)
	{
		System.out.println("deleteKarta()");
		
		return (!kartaService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}
	
	
	/*
	 * OSTALE OPERACIJE
	 */

	/*
	 * Rezervacija jedne karte od strane korisnika
	 */
	@PostMapping("/reserveone/")
	public ResponseEntity<Boolean> rezervisiJednuKartu(@RequestBody List<Long> rezervacija)
	{
		System.out.println("rezervisiJednuKartu()");
		
		Long idKorisnika, idKarte;
		
		Object[] rezz = rezervacija.toArray();
		
		idKorisnika = (Long) rezz[0];
		idKarte = (Long) rezz[1];
		
		System.out.println("ID_KORISNIK: " + idKorisnika + "\nID_KARTE: " + idKarte);
		
		return (!kartaService.rezervisiJednuKartu(idKorisnika, idKarte)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	/*
	 * Rezervacija vise karata odjednom od strane korisnika
	 */
	@PostMapping("/reservemore/{userid}")
	public ResponseEntity<String> rezervisiViseKarata(@PathVariable("userid") Long idKorisnika, @RequestBody List<KartaDTO> karte)
	{
		System.out.println("rezervisiViseKarata()");
	
		String retVal = kartaService.rezervisiViseKarata(idKorisnika, karte);
		
		return (retVal.equals("REZERVISANE")) ? new ResponseEntity<String>(retVal, HttpStatus.CREATED) : new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST);
	}

}