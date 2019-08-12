package com.acboot.aviocompany.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.AvioKompanijaDTO;
import com.acboot.aviocompany.dto.DestinacijaDTO;
import com.acboot.aviocompany.model.AvioKompanija;
import com.acboot.aviocompany.model.Destinacija;

@Component
public class AvioKompanijaConverter
{
	@Autowired
	private DestinacijaConverter destConv;
	
	public AvioKompanijaDTO convertToDTO(AvioKompanija model)
	{
		AvioKompanijaDTO dto = new AvioKompanijaDTO();
		
		dto.setIdAvioKompanije(model.getIdAvioKompanije());
		dto.setNaziv(model.getNaziv());
		dto.setAdresa(model.getAdresa());
		dto.setOpis(model.getOpis());
		
		List<DestinacijaDTO> destList = new ArrayList<DestinacijaDTO>();
		
		for(Destinacija dest : model.getDestinacijeNaKojimaPosluje())
		{
			destList.add(destConv.convertToDTO(dest));
		}
		
		dto.setDestinacijeNaKojimaPosluje(destList);
		
		return dto;
	}
	
	public AvioKompanija convertFromDTO(AvioKompanijaDTO dto)
	{
		AvioKompanija model = new AvioKompanija();
		
		model.setIdAvioKompanije(dto.getIdAvioKompanije());
		model.setNaziv(dto.getNaziv());
		model.setAdresa(dto.getAdresa());
		model.setOpis(dto.getOpis());
		
		List<Destinacija> destList = new ArrayList<Destinacija>();
		
		for(DestinacijaDTO dest : dto.getDestinacijeNaKojimaPosluje())
		{
			destList.add(destConv.convertFromDTO(dest));
		}
		
		model.setDestinacijeNaKojimaPosluje(destList);
		
		return model;
		
	}

}
