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

import com.isa.hoteli.hoteliservice.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.model.Rola;
import com.isa.hoteli.hoteliservice.service.KorisnikService;

@RestController
@RequestMapping("/korisnik")
public class KorisnikController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<KorisnikDTO>> getUsers(){
		List<KorisnikDTO> dto = new ArrayList<>();
		List<Korisnik> lista = korisnikService.getUsers();
		for (Korisnik item : lista) {
			dto.add(new KorisnikDTO(item));
		}
		return new ResponseEntity<List<KorisnikDTO>>(dto, HttpStatus.OK);
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

}
