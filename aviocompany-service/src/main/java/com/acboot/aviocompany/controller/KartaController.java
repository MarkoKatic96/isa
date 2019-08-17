package com.acboot.aviocompany.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

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
import com.acboot.aviocompany.dto.KorisnikDTO;
import com.acboot.aviocompany.dto.SlanjePozivniceZaRezervacijuDTO;
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
	
	@GetMapping("/getfree/{flightid}")
	public ResponseEntity<List<KartaDTO>> getAllNerezervisaneKarte(@PathVariable("flightid") Long idLeta)
	{
		System.out.println("getAllNerezervisaneKarte()");
		
		List<KartaDTO> listDto = kartaService.getAllNerezervisaneKarte(idLeta);
		
		return (listDto.isEmpty()) ? new ResponseEntity<List<KartaDTO>>(new ArrayList<KartaDTO>(), HttpStatus.NOT_FOUND) : new ResponseEntity<List<KartaDTO>>(listDto, HttpStatus.OK);
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
	 * BRZA REZERVACIJA
	 */
	@PostMapping("/expressreservation/{userid}/{ticketid}")
	public ResponseEntity<Boolean> brzaRezervacijaJedneKarte(@PathVariable("userid") Long idKorisnika, @PathVariable("ticketid") Long idKarte)
	{
		System.out.println("brzaRezervacijaJedneKarte()");
		
//		Long idKorisnika, idKarte;
		
//		Object[] rezz = rezervacija.toArray();
		
//		idKorisnika = (Long) rezz[0];
//		idKarte = (Long) rezz[1];
		
		System.out.println("ID_KORISNIK: " + idKorisnika + "\nID_KARTE: " + idKarte);
		
		return (!kartaService.brzaRezervacijaJedneKarte(idKorisnika, idKarte)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	/*
	 * POSTAVLJANJE KARTE NA BRZU REZERVACIJU OD STRANE ADMINA
	 */
	@PutMapping("/settoexpress/{ticketid}/{discount}")
	public ResponseEntity<Boolean> postaviKartuNaBrzuRezervaciju(@PathVariable("ticketid") Long idKarte, @PathVariable("discount") Integer popust)
	{
		System.out.println("postaviKartuNaBrzuRezervaciju()");
		
		return (!kartaService.postaviKartuNaBrzuRezervaciju(idKarte, popust)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	/*
	 * Vraca sve karte koje su za brzu rezervaciju
	 */
	@GetMapping("/getexpress")
	public ResponseEntity<List<KartaDTO>> getAllBrzaRezervacijaKarte()
	{
		System.out.println("getAllBrzaRezervacijaKarte()");
		
		List<KartaDTO> listDto = kartaService.getAllNerezervisaneKarte();
		
		return (listDto.isEmpty()) ? new ResponseEntity<List<KartaDTO>>(new ArrayList<KartaDTO>(), HttpStatus.NOT_FOUND) : new ResponseEntity<List<KartaDTO>>(listDto, HttpStatus.OK);
	}
	
	/*
	 * Otkazivanje rezervacije od strane korisnika
	 */
	@PostMapping("/deletereservation/{userid}/{ticketid}")
	public ResponseEntity<Boolean> obrisiRezervacijuJedneKarte(@PathVariable("userid") Long idKorisnika, @PathVariable("ticketid") Long idKarte)
	{
		System.out.println("obrisiRezervacijuJedneKarte()");
		
		return (!kartaService.obrisiRezervacijuJedneKarte(idKorisnika, idKarte)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	/*
	 * Rezervacija vise karata odjednom od strane korisnika (nije testirano)
	 * Prima id korisnika koji rezervise sve to, prima listu karata koja je rezervisana kao i listu prijatelja koje je korisnik pozvao
	 */
	@PostMapping("/reservemore/{userid}")
	public ResponseEntity<String> rezervisiViseKarata(@PathVariable("userid") Long idKorisnika, @RequestBody SlanjePozivniceZaRezervacijuDTO pozivnica)
	{
		System.out.println("rezervisiViseKarata()");
	
		String retVal = kartaService.rezervisiViseKarata(idKorisnika, pozivnica);
		
		return (retVal.equals("REZERVISANE")) ? new ResponseEntity<String>(retVal, HttpStatus.CREATED) : new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST);
	}
	
//	/*
//	 * Rezervacija vise karata odjednom od strane korisnika (nije testirano)
//	 * Prima id korisnika koji rezervise sve to, prima listu karata koja je rezervisana kao i listu prijatelja koje je korisnik pozvao
//	 */
//	@PutMapping("/acceptfriendinv/{userid}")
//	public ResponseEntity<String> rezervisiViseKarata(@PathVariable("userid") Long idKorisnika, @RequestBody SlanjePozivniceZaRezervacijuDTO pozivnica)
//	{
//		System.out.println("rezervisiViseKarata()");
//	
//		String retVal = kartaService.rezervisiViseKarata(idKorisnika, pozivnica);
//		
//		return (retVal.equals("REZERVISANE")) ? new ResponseEntity<String>(retVal, HttpStatus.CREATED) : new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST);
//	}

}