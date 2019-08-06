package com.acboot.aviocompany.converter;

import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.KorisnikDTO;
import com.acboot.aviocompany.model.Korisnik;

@Component
public class KorisnikConverter 
{
	public KorisnikDTO convertToDTO(Korisnik model)
	{
		KorisnikDTO dto = new KorisnikDTO();
		
		dto.setId(model.getId());
		dto.setEmail(model.getEmail());
		dto.setLozinka(model.getLozinka());
		dto.setIme(model.getIme());
		dto.setPrezime(model.getPrezime());
		dto.setGrad(model.getGrad());
		dto.setTelefon(model.getTelefon());
		dto.setAktiviran(model.isAktiviran());
		dto.setRola(model.getRola());
		dto.setPrviPutLogovan(model.isPrviPutLogovan());
		
		
		return dto;
	}
	
	public Korisnik convertFromDTO(KorisnikDTO dto)
	{
		Korisnik model = new Korisnik();
		
		model.setId(dto.getId());
		model.setEmail(dto.getEmail());
		model.setLozinka(dto.getLozinka());
		model.setIme(dto.getIme());
		model.setPrezime(dto.getPrezime());
		model.setGrad(dto.getGrad());
		model.setTelefon(dto.getTelefon());
		model.setAktiviran(dto.isAktiviran());
		model.setRola(dto.getRola());
		model.setPrviPutLogovan(dto.isPrviPutLogovan());
		
		return model;
		
	}
}
