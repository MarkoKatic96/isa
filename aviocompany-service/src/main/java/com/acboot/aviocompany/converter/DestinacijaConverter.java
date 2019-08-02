package com.acboot.aviocompany.converter;

import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.DestinacijaDTO;
import com.acboot.aviocompany.model.Destinacija;

@Component
public class DestinacijaConverter
{
	
	public DestinacijaDTO convertToDTO(Destinacija model)
	{
		DestinacijaDTO dto = new DestinacijaDTO();
		
		dto.setIdDestinacije(model.getIdDestinacije());
		dto.setNaziv(model.getNaziv());
		dto.setInformacije(model.getInformacije());
		
		return dto;
	}
	
	public Destinacija convertFromDTO(DestinacijaDTO dto)
	{
		Destinacija model = new Destinacija();
		
		model.setIdDestinacije(dto.getIdDestinacije());
		model.setNaziv(dto.getNaziv());
		model.setInformacije(dto.getInformacije());
		
		return model;
		
	}
}
