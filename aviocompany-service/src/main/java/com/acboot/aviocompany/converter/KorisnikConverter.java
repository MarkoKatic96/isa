package com.acboot.aviocompany.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.DestinacijaDTO;
import com.acboot.aviocompany.dto.KorisnikDTO;
import com.acboot.aviocompany.model.Destinacija;
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
		dto.setZaduzenZaId(model.getZaduzenZaId());
		dto.setPrviPutLogovan(model.isPrviPutLogovan());
		
		List<KorisnikDTO> korList = new ArrayList<KorisnikDTO>();
		
		for(Korisnik kor : model.getPrijateljiKorisnika())
		{
			korList.add(this.convertToDTO(kor));
		}
		
		dto.setPrijateljiKorisnika(korList);
		
		List<KorisnikDTO> zkorList = new ArrayList<KorisnikDTO>();
		
		for(Korisnik kor : model.getZahteviKorisnika())
		{
			zkorList.add(this.convertToDTO(kor));
		}
		
		dto.setZahteviKorisnika(zkorList);
		
		List<KorisnikDTO> pozList = new ArrayList<KorisnikDTO>();
		
		for(Korisnik kor : model.getPozivniceKorisnika())
		{
			pozList.add(this.convertToDTO(kor));
		}
		
		dto.setPozivniceKorisnika(pozList);

		
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
		model.setZaduzenZaId(dto.getZaduzenZaId());
		model.setPrviPutLogovan(dto.isPrviPutLogovan());
		
		
		List<Korisnik> korList = new ArrayList<Korisnik>();
		
		if(dto.getPrijateljiKorisnika() != null)
		{
			for(KorisnikDTO kor : dto.getPrijateljiKorisnika())
			{
				korList.add(this.convertFromDTO(kor));
			}
		}
		model.setPrijateljiKorisnika(korList);
		
		
		List<Korisnik> zkorList = new ArrayList<Korisnik>();
		
		if(dto.getZahteviKorisnika() != null)
		{
			for(KorisnikDTO kor : dto.getZahteviKorisnika())
			{
				zkorList.add(this.convertFromDTO(kor));
			}
		}
		model.setZahteviKorisnika(zkorList);
		
		
		List<Korisnik> pozList = new ArrayList<Korisnik>();
		
		if(dto.getPozivniceKorisnika() != null)
		{
			for(KorisnikDTO kor : dto.getPozivniceKorisnika())
			{
				pozList.add(this.convertFromDTO(kor));
			}
		}
		
		model.setPozivniceKorisnika(pozList);
		
		
		return model;
	}
}
