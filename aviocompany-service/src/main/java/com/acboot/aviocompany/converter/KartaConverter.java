package com.acboot.aviocompany.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.KartaDTO;
import com.acboot.aviocompany.model.Karta;

@Component
public class KartaConverter 
{
	@Autowired
	private LetConverter letConv;
	
	public KartaDTO convertToDTO(Karta model)
	{
		KartaDTO dto = new KartaDTO();
		
		dto.setIdKarte(model.getIdKarte());
		dto.setCena(model.getCena());
		dto.setOcena(model.getOcena());
		dto.setBrzaRezervacija(model.isBrzaRezervacija());
		dto.setPopust(model.getPopust());
		dto.setLet(letConv.convertToDTO(model.getLet()));
		
		return dto;
	}
	
	public Karta convertFromDTO(KartaDTO dto)
	{
		Karta model = new Karta();
		
		model.setIdKarte(dto.getIdKarte());
		model.setCena(dto.getCena());
		model.setOcena(dto.getOcena());
		model.setBrzaRezervacija(dto.isBrzaRezervacija());
		model.setPopust(dto.getPopust());
		model.setLet(letConv.convertFromDTO(dto.getLet()));
		
		return model;
		
	}
}