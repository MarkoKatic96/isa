package com.acboot.aviocompany.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.LetConverter;
import com.acboot.aviocompany.dto.LetDTO;
import com.acboot.aviocompany.model.Let;
import com.acboot.aviocompany.repository.LetRepository;

@Service
public class LetService
{

	@Autowired
	LetRepository letRepo;
	
	@Autowired
	LetConverter letConv;
	
	
	public LetDTO findById(Long id)
	{
		Optional<Let> let = letRepo.findById(id);
		
		if(let.isPresent())
			return letConv.convertToDTO(let.get());
		else
			return null;
	}
	
	public List<LetDTO> findAll()
	{
		Optional<List<Let>> list = Optional.of(letRepo.findAll());
		
		List<LetDTO> listDto = new ArrayList<LetDTO>();
		
		if(list.isPresent())
		{
			for(Let let : list.get())
				listDto.add(letConv.convertToDTO(let));
			
			return listDto;
		}
		else
			return null;
	}
	
	public LetDTO saveOne(LetDTO dto)
	{
		Optional<Let> let = letRepo.findById(dto.getIdLeta());
						
		if(let.isPresent())
			return null;
		else
		{			
			letRepo.save(letConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	public LetDTO updateOne(Long id, LetDTO dto)
	{
		Optional<Let> let = letRepo.findById(id);
		
		if(let.isPresent())
		{
			let.get().setIdLeta(letConv.convertFromDTO(dto).getIdLeta());
			let.get().setBrojLeta(letConv.convertFromDTO(dto).getBrojLeta());
			let.get().setVremePoletanja(letConv.convertFromDTO(dto).getVremePoletanja());
			let.get().setVremeSletanja(letConv.convertFromDTO(dto).getVremeSletanja());
			let.get().setDuzinaPutovanja(letConv.convertFromDTO(dto).getDuzinaPutovanja());
			let.get().setBrojPresedanja(letConv.convertFromDTO(dto).getBrojPresedanja());
			let.get().setProsecnaOcena(letConv.convertFromDTO(dto).getProsecnaOcena());
			let.get().setTipPuta(letConv.convertFromDTO(dto).getTipPuta());
			let.get().setBrojOsoba(letConv.convertFromDTO(dto).getBrojOsoba());
			let.get().setUkupanPrihod(letConv.convertFromDTO(dto).getUkupanPrihod());
			
			let.get().setAviokompanija(letConv.convertFromDTO(dto).getAviokompanija());
			let.get().setDestinacijaPoletanja(letConv.convertFromDTO(dto).getDestinacijaPoletanja());
			let.get().setDestinacijaSletanja(letConv.convertFromDTO(dto).getDestinacijaSletanja());
			let.get().setDestinacijePresedanja(letConv.convertFromDTO(dto).getDestinacijePresedanja());
			let.get().setTipoviPrtljagaPoLetu(letConv.convertFromDTO(dto).getTipoviPrtljagaPoLetu());
			let.get().setKlaseKojeLetSadrzi(letConv.convertFromDTO(dto).getKlaseKojeLetSadrzi());
			let.get().setDodatneUslugeKojeLetSadrzi(letConv.convertFromDTO(dto).getDodatneUslugeKojeLetSadrzi());
			
			
			letRepo.save(let.get());
			
			return letConv.convertToDTO(let.get());
		}
		else
			return null;
	}
	
	public boolean deleteOne(Long id)
	{
		Optional<Let> let = letRepo.findById(id);
		
		if(let.isPresent())
		{
			letRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
}
