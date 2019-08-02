package com.acboot.aviocompany.converter;

import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.KlasaDTO;
import com.acboot.aviocompany.model.Klasa;

@Component
public class KlasaConverter
{
	public KlasaDTO convertToDTO(Klasa model)
	{
		KlasaDTO dto = new KlasaDTO();
		
		dto.setIdKlase(model.getIdKlase());
		dto.setNaziv(model.getNaziv());
		
		return dto;
	}
	
	public Klasa convertFromDTO(KlasaDTO dto)
	{
		Klasa model = new Klasa();
		
		model.setIdKlase(dto.getIdKlase());
		model.setNaziv(dto.getNaziv());
		
		return model;
		
	}
}
