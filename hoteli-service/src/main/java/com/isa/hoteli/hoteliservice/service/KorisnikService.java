package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.model.Korisnik;
import com.isa.hoteli.hoteliservice.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@Component
public class KorisnikService {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private JwtTokenUtils jwtTokenProvider;
	
	@Autowired
	private KorisnikService korisnikService;
	
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
	
	public String login(String email, String lozinka) {
		Korisnik k = korisnikRepository.getUserByEmail(email);
		if(k==null) {
			return null;
		}
		try {
			if(k.getLozinka().equals(lozinka)) {
				/*if (k.isBlokiran() || !k.isRegistrovan()) {
					return null;
				}*/
				String jwt = jwtTokenProvider.createToken(email);
				ObjectMapper mapper = new ObjectMapper();
				return mapper.writeValueAsString(jwt);
			}
		}catch (Exception e) {
			return null;
		}
		
		return null;
	}
	
	public Korisnik zaTokene(HttpServletRequest req) {
		String token = jwtTokenProvider.resolveToken(req);
		String email = jwtTokenProvider.getUsername(token);
		Korisnik k = korisnikService.getUserByEmail(email);
		return k;
	}

}
