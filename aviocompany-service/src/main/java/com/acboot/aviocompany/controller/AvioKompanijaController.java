package com.acboot.aviocompany.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.acboot.aviocompany.model.Korisnik;
import com.acboot.aviocompany.model.Rola;
import com.acboot.aviocompany.service.AvioKompanijaService;
import com.acboot.aviocompany.service.KorisnikService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/aviocompany")
@Api(tags = "")
public class AvioKompanijaController
{
	@Autowired
	private AvioKompanijaService avioService;
	
	@Autowired
	private KorisnikService korServ;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<AvioKompanijaDTO> getAvioKompanija(@PathVariable Long id, HttpServletRequest req)
	{
		System.out.println("getAvioKompanija()");
		
//		Korisnik k = korServ.zaTokene(req);
//		if(k != null && k.getRola().equals(Rola.ADMIN_AVIO_KOMPANIJE) && k.getZaduzenZaId() == id)
//		{
			AvioKompanijaDTO avioDto = avioService.findById(id);
			return (avioDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<AvioKompanijaDTO>(avioDto, HttpStatus.OK);
//		}
//		
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		
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
	
	/*
	 * Dodajemo difoltnu destinaciju na taj i taj aerodrom
	 */
	@PutMapping("/adddefaultdest/{companyid}")
	public ResponseEntity<Boolean> addDifoltnaDestinacija(@PathVariable("companyid") Long id)
	{
		System.out.println("addDifoltnaDestinacija()");
		
		return (!avioService.addDefaultDestination(id)) ? new ResponseEntity<>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}
	
	/*
	 * ADMIN
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<AvioKompanijaDTO> updateAvioKompanija(@PathVariable("id") String id, @RequestBody AvioKompanijaDTO dto, HttpServletRequest req)
	{
		System.out.println("updateAvioKompanija()");
		
		Korisnik k = korServ.zaTokene(req);
		if(k != null && k.getRola().equals(Rola.ADMIN_AVIO_KOMPANIJE) && k.getZaduzenZaId() == Long.parseLong(id, 10))
		{
			AvioKompanijaDTO avio = avioService.updateOne(Long.parseLong(id, 10), dto);
			return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<AvioKompanijaDTO>(avio, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
