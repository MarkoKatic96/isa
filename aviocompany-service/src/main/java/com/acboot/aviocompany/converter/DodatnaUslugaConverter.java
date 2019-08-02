package com.acboot.aviocompany.converter;

import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.DodatnaUslugaDTO;
import com.acboot.aviocompany.model.DodatnaUsluga;

@Component
public class DodatnaUslugaConverter
{

	public DodatnaUslugaDTO convertToDTO(DodatnaUsluga model)
	{
		DodatnaUslugaDTO dto = new DodatnaUslugaDTO();
		
		dto.setIdDodatneUsluge(model.getIdDodatneUsluge());
		dto.setNaziv(model.getNaziv());
		
		return dto;
	}
	
	public DodatnaUsluga convertFromDTO(DodatnaUslugaDTO dto)
	{
		DodatnaUsluga model = new DodatnaUsluga();
		
		model.setIdDodatneUsluge(dto.getIdDodatneUsluge());
		model.setNaziv(dto.getNaziv());
		
		return model;
		
	}
}
