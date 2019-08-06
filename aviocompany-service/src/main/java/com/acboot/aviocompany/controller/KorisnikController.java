package com.acboot.aviocompany.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acboot.aviocompany.dto.KartaDTO;
import com.acboot.aviocompany.dto.KorisnikDTO;
import com.acboot.aviocompany.model.Korisnik;
import com.acboot.aviocompany.model.Login;
import com.acboot.aviocompany.model.Rola;
import com.acboot.aviocompany.security.JwtTokenUtils;
import com.acboot.aviocompany.service.KorisnikService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
@Api(tags = "")
public class KorisnikController
{
	@Autowired
	private KorisnikService korService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<KorisnikDTO>> getKorisnici(HttpServletRequest req)
	{
		Korisnik k = korService.zaTokene(req);
		
		if(k != null && k.getRola().equals(Rola.MASTER_ADMIN))
		{
			List<KorisnikDTO> dto = new ArrayList<KorisnikDTO>();
			List<Korisnik> lista = korService.getUsers();
			
			for(Korisnik item : lista)
			{
				dto.add(new KorisnikDTO(item));
			}
			return new ResponseEntity<List<KorisnikDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getUserById(@PathVariable("id") Long id){
		if(korService.getUserById(id)!=null) {
			KorisnikDTO dto = new KorisnikDTO(korService.getUserById(id));
			return new ResponseEntity<KorisnikDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/all/{email}", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getUserByEmail(@PathVariable("email") String email){
		
		System.out.println("preuzmiRolu()");
		
		if(korService.getUserByEmail(email)!=null) {
			KorisnikDTO dto = new KorisnikDTO(korService.getUserByEmail(email));
			return new ResponseEntity<KorisnikDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<KorisnikDTO> createUser(@RequestBody KorisnikDTO dto){
		Korisnik obj = new Korisnik(dto);
		obj.setRola(Rola.KORISNIK);
		KorisnikDTO returnType = korService.createKorisnika(obj);
		if(returnType!=null) {
			return new ResponseEntity<>(returnType, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
		return new ResponseEntity<String>(korService.deleteKorisnika(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<KorisnikDTO> updateUserById(@PathVariable("id") Long id, @RequestBody KorisnikDTO dto){
		Korisnik obj = new Korisnik(dto);
		KorisnikDTO returnTip = korService.updateKorisnika(obj, id);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody Login login){
		
		System.out.println("login()");
		
		String email = login.getEmail();
		String lozinka = login.getLozinka();
		String jwt = korService.login(email, lozinka);
		return (jwt!=null) ? new ResponseEntity<String>(jwt, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	/*
	 * OSTALE OPERACIJE
	 */
	
	
	/*
	 * U zaglavlju prima id korisnika koji salje zahtev, telo sadrzi email korisnika kome se salje zahtev
	 */
	@PostMapping("/invitefriend/{id}")
	public ResponseEntity<String> posaljiZahtev(@PathVariable("id") Long idKorisnika, @RequestBody String email)
	{
		System.out.println("posaljiZahtev()");
		System.out.println(email);
		
		String retVal = korService.posaljiZahtev(idKorisnika, email);
		
		return (!retVal.equals("SUCCESS")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	/*
	 * Prima id korisnika od koga je zahtev i email prijatelja kome je poslat (mora u paru da bi izvukli zahtev iz baze)
	 */
	@PostMapping("/acceptrequest/{id}")
	public ResponseEntity<String> prihvatiZahtev(@PathVariable("id") Long idKorisnika, @RequestBody String email)
	{
		System.out.println("prihvatiZahtev()");
		
		String retVal = korService.prihvatiZahtev(idKorisnika, email);
		
		return (!retVal.equals("SUCCESS")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
}
