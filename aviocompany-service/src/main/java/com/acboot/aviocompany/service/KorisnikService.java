package com.acboot.aviocompany.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.dto.KorisnikDTO;
import com.acboot.aviocompany.model.Korisnik;
import com.acboot.aviocompany.repository.KorisnikRepository;
import com.acboot.aviocompany.security.JwtTokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KorisnikService
{
//	@Autowired
//	private KorisnikRepository korisnikRepo;
//	
//	@Autowired
//	private JwtTokenUtils jwtTokenProvider;
//	
//	@Autowired
//	private KorisnikService korService;
//	
//	public List<Korisnik> getUsers()
//	{
//		return korisnikRepo.findAll();
//	}
//	
//	public Korisnik getUserById(Long id)
//	{
//		return korisnikRepo.getOne(id);
//	}
//	
//	public Korisnik getUserByEmail(String email)
//	{
//		return korisnikRepo.getUserByEmail(email).get();
//	}
//	
//	public KorisnikDTO createKorisnika(Korisnik kor)
//	{
//		if(korisnikRepo.getUserByEmail(kor.getEmail()) == null)
//			return new KorisnikDTO(korisnikRepo.save(kor));
//		
//		return null;
//	}
//	
//	public String deleteKorisnika(Long id) {
//		korisnikRepo.deleteById(id);
//		return "Uspesno obrisan korisnik sa id: " + id;
//	}
//	
//	public KorisnikDTO updateKorisnika(Korisnik obj, Long id)
//	{
//		Optional<Korisnik> obj1 = korisnikRepo.findById(id);
//		if(obj1.isPresent())
//		{
//			Optional<Korisnik> k = korisnikRepo.getUserByEmail(obj.getEmail());
//		
//			if(!k.isPresent() || k.get().getId() == id)
//			{
//				obj1.get().setEmail(obj.getEmail());
//				obj1.get().setLozinka(obj.getLozinka());
//				obj1.get().setIme(obj.getIme());
//				obj1.get().setPrezime(obj.getPrezime());
//				obj1.get().setGrad(obj.getGrad());
//				obj1.get().setTelefon(obj.getTelefon());
//				korisnikRepo.save(obj1.get());
//				
//				return new KorisnikDTO(obj1.get());
//			}
//		}
//		
//		return null;
//	}
//	
//	public String login(String email, String lozinka)
//	{
//		Optional<Korisnik> k = korisnikRepo.getUserByEmail(email);
//		if (k == null)
//			return null;
//
//		try 
//		{
//			if (k.get().getLozinka().equals(lozinka))
//			{
//				String jwt = jwtTokenProvider.createToken(email);
//				ObjectMapper mapper = new ObjectMapper();
//
//				return mapper.writeValueAsString(jwt);
//			}
//
//		} catch (JsonProcessingException e) 
//		{
//			return null;
//		}
//		
//		return null;
//	}
//	
//	public Korisnik zaTokene(HttpServletRequest req) {
//		String token = jwtTokenProvider.resolveToken(req);
//		String email = jwtTokenProvider.getUsername(token);
//		Korisnik k = korService.getUserByEmail(email);
//		return k;
//	}
}
