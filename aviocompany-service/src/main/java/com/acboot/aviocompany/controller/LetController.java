package com.acboot.aviocompany.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.acboot.aviocompany.dto.KlasaDTO;
import com.acboot.aviocompany.dto.LetDTO;
import com.acboot.aviocompany.dto.PretragaDTO;
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
	
	////////////////////////////////////////////
	
	/*
	 * Povezivanje letova i klasa
	 */
	@PutMapping("/addclass")
	public ResponseEntity<LetDTO> addKlaseLeta(@RequestBody LetDTO skills)
	{
		System.out.println("addKlaseLeta()");
		
		LetDTO dto = letService.addKlaseLeta(skills);
		
		return (dto == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<LetDTO>(dto, HttpStatus.CREATED);
	}
	
	
	/*
	 * Vraca id aviokompanije za koju je vezan let koji je prosledjen preko id-ja
	 */
	@GetMapping("/getcompanyid/{flightid}")
	public ResponseEntity<Long> getIdKompanije(@PathVariable("flightid") Integer idLeta)
	{
		System.out.println("getIdKompanije()");
		
		Long idKompanije = letService.getIdKompanije(idLeta);
		
		return (idKompanije == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<Long>(idKompanije, HttpStatus.OK);
	}
	
	  /////////////////////////////////
	 /////////////////////////////////
	/////////////////////////////////
	
	
	/*
	 * PRETRAGA KOJA OBUHVATA SVE OVE ISPOD POJEDINACNE
	 */
	@PostMapping("/searchflights")
	public ResponseEntity<List<LetDTO>> searchLetove(@RequestBody PretragaDTO pretraga)
	{		
		System.out.println("searchLetove()");
		
		List<LetDTO> letovi = letService.searchLetove(pretraga);
		
		return(letovi.isEmpty()) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<List<LetDTO>>(letovi, HttpStatus.OK); 
	}
	
	
	
	/* (Y)
	 * Pretraga po vremenu poletanja
	 */
	@GetMapping("/searchbydate/{time1}/{time2}")
	public ResponseEntity<List<LetDTO>> searchLetoviPoVremenu(@PathVariable("time1") String time1, @PathVariable("time2") String time2)
	{
		System.out.println("searchLetoviPoVremenu()");
		
		DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
		
		String vreme1 = LocalDateTime.parse(time1).format(format);
		String vreme2 = LocalDateTime.parse(time2).format(format);
		List<LetDTO> letovi = letService.searchLetoviPoVremenu(LocalDateTime.parse(vreme1), LocalDateTime.parse(vreme2));
	
		return (letovi.isEmpty()) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<List<LetDTO>>(letovi, HttpStatus.OK);
	}
	
	
	/*
	 * Pretraga letova po destinaciji poletanja i sletanja (preko id-jeva)
	 */
	@GetMapping("/searchbydest/{fromdest}/{todest}")
	public ResponseEntity<List<LetDTO>> searchLetoviPoDestinaciji(@PathVariable("fromdest") Long takeOffDestination, @PathVariable("todest") Long landingDestination)
	{
		System.out.println("searchLetoviPoDestinaciji()");
		
		List<LetDTO> letovi = letService.searchLetoviPoDestinaciji(takeOffDestination, landingDestination);
		
		return(letovi.isEmpty()) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<List<LetDTO>>(letovi, HttpStatus.OK);
	}
	
	
	/*
	 * Pretraga letova po tipu leta
	 */
	@GetMapping("/searchbytype/{type}")
	public ResponseEntity<List<LetDTO>> searchLetoviPoTipu(@PathVariable("type") String type)
	{		
		System.out.println("searchLetoviPoTipu()");
		
		List<LetDTO> letovi = letService.searchLetoviPoTipu(type);
		
		return(letovi.isEmpty()) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<List<LetDTO>>(letovi, HttpStatus.OK); 
	}
	
	
	/*
	 * Pretraga letova po broju preostalih karata (broju osoba) -> npr ako korisnik hoce da rezervise za jos {broj} ljudi
	 */
	@GetMapping("/searchbyfreeseats/{number}")
	public ResponseEntity<List<LetDTO>> searchLetoviPoBrojuMesta(@PathVariable("number") String number)
	{		
		System.out.println("searchLetoviPoBrojuMesta()");
		
		Integer num = Integer.parseInt(number);
		List<LetDTO> letovi = letService.searchLetoviPoBrojuMesta(num);
		
		return(letovi.isEmpty()) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<List<LetDTO>>(letovi, HttpStatus.OK); 
	}
	
	
	/*
	 * Pretraga letova po klasama koje podrzava
	 * Radi tako sto vraca rezultat za bilo koji od navedenih parametara
	 */
	@GetMapping("/searchbyclasses")
	public ResponseEntity<List<LetDTO>> searchLetoviPoKlasama(@RequestBody List<KlasaDTO> klase)
	{		
		System.out.println("searchLetoviPoKlasama()");
		
		List<LetDTO> letovi = letService.searchLetoviPoKlasama(klase);
		
		return(letovi.isEmpty()) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<List<LetDTO>>(letovi, HttpStatus.OK); 
	}
	
	
	/*
	 * ADMIN OPERACIJE 
	 */
	 
	
	/*
	 * Trazi prosecnu ocenu za jedan let na osnovu ocena korisnika (jedna karta jedna ocena)
	 */
	@GetMapping("/getavgrating/{id}")
	public ResponseEntity<Float> getSrednjaOcenaLeta(@PathVariable("id") Long id)
	{
		System.out.println("getSrednjaOcenaLeta()");
		
		Float avg = letService.getSrednjaOcenaLeta(id);
		
		return(avg == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<Float>(avg, HttpStatus.OK);
	}

}