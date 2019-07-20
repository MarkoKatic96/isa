package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.repository.KorisnikRepository;

@Component
public class KorisnikService {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	public List<Korisnik> getUsers(){
		return korisnikRepository.findAll();
	}
	
	public Korisnik getUserById(Long id){
		return korisnikRepository.getOne(id);
	}
	
	public Korisnik getUserByEmail(String email){
		return korisnikRepository.getUserByEmail(email);
	}
	
	public KorisnikDTO createUser(Korisnik obj) {
		if(korisnikRepository.getUserByEmail(obj.getEmail())==null) {
			return new KorisnikDTO(korisnikRepository.save(obj));
		}
		return null;//korisnik sa istim emailom vec postoji
	}
	
	public String deleteUser(Long id) {
		korisnikRepository.deleteById(id);
		return "Uspesno obrisan korisnik sa id: " + id;
	}
	
	public KorisnikDTO updateUser(Korisnik obj, Long id) {
		Optional<Korisnik> obj1 = korisnikRepository.findById(id);
		if(obj1.isPresent()) {
			obj1.get().setEmail(obj.getEmail());
			obj1.get().setLozinka(obj.getLozinka());
			obj1.get().setIme(obj.getIme());
			obj1.get().setPrezime(obj.getPrezime());
			obj1.get().setGrad(obj.getGrad());
			obj1.get().setAktiviran(obj.isAktiviran());
			obj1.get().setTelefon(obj.getTelefon());
			obj1.get().setRola(obj.getRola());
			korisnikRepository.save(obj1.get());
			return new KorisnikDTO(obj1.get());
		}
		return null;
	}

}
