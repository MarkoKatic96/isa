package com.isa.hoteli.hoteliservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.model.Login;
import com.isa.hoteli.hoteliservice.model.Rola;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.KorisnikService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/korisnik")
public class KorisnikController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	JwtTokenUtils jwtTokenUtils;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<KorisnikDTO>> getUsers(HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.MASTER_ADMIN)) {
			List<KorisnikDTO> dto = new ArrayList<>();
			List<Korisnik> lista = korisnikService.getUsers();
			for (Korisnik item : lista) {
				dto.add(new KorisnikDTO(item));
			}
			return new ResponseEntity<List<KorisnikDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getUserById(@PathVariable("id") Long id){
		if(korisnikService.getUserById(id)!=null) {
			KorisnikDTO dto = new KorisnikDTO(korisnikService.getUserById(id));
			return new ResponseEntity<KorisnikDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/all/{email}", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getUserByEmail(@PathVariable("email") String email){
		if(korisnikService.getUserByEmail(email)!=null) {
			KorisnikDTO dto = new KorisnikDTO(korisnikService.getUserByEmail(email));
			return new ResponseEntity<KorisnikDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<KorisnikDTO> createUser(@RequestBody KorisnikDTO dto){
		Korisnik obj = new Korisnik(dto);
		obj.setRola(Rola.KORISNIK);
		KorisnikDTO returnType = korisnikService.createUser(obj);
		if(returnType!=null) {
			return new ResponseEntity<>(returnType, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.POST)
	public ResponseEntity<KorisnikDTO> createAdmin(@RequestBody KorisnikDTO dto, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.MASTER_ADMIN)) {
			Korisnik obj = new Korisnik(dto);
			obj.setAktiviran(true);
			KorisnikDTO returnType = korisnikService.createUser(obj);
			if(returnType!=null) {
				return new ResponseEntity<>(returnType, HttpStatus.OK);
			}
		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
		return new ResponseEntity<String>(korisnikService.deleteUser(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<KorisnikDTO> updateUserById(@PathVariable("id") Long id, @RequestBody KorisnikDTO dto){
		Korisnik obj = new Korisnik(dto);
		KorisnikDTO returnTip = korisnikService.updateUser(obj, id);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody Login login){
		String email = login.getEmail();
		String lozinka = login.getLozinka();
		String jwt = korisnikService.login(email, lozinka);
		return (jwt!=null) ? new ResponseEntity<String>(jwt, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
