package com.acboot.aviocompany.converter;

import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.PrtljagDTO;
import com.acboot.aviocompany.model.Prtljag;

@Component
public class PrtljagConverter 
{
	
	public PrtljagDTO convertToDTO(Prtljag model)
	{
		PrtljagDTO dto = new PrtljagDTO();
		
		dto.setIdPrtljaga(model.getIdPrtljaga());
		dto.setTezina(model.getTezina());
		dto.setDuzina(model.getDuzina());
		dto.setSirina(model.getSirina());
		dto.setVisina(model.getVisina());
		
		return dto;
	}
	
	public Prtljag convertFromDTO(PrtljagDTO dto)
	{
		Prtljag model = new Prtljag();
		
		model.setIdPrtljaga(dto.getIdPrtljaga());
		model.setTezina(dto.getTezina());
		model.setDuzina(dto.getDuzina());
		model.setSirina(dto.getSirina());
		model.setVisina(dto.getVisina());
		
		return model;
		
	}
}
