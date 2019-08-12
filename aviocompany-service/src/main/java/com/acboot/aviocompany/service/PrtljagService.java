package com.acboot.aviocompany.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.PrtljagConverter;
import com.acboot.aviocompany.dto.PrtljagDTO;
import com.acboot.aviocompany.model.Prtljag;
import com.acboot.aviocompany.repository.PrtljagRepository;

@Service
public class PrtljagService
{
	@Autowired
	PrtljagRepository prtljagRepo;
	
	@Autowired
	PrtljagConverter prtljagConv;
	
	
	public PrtljagDTO findById(Long id)
	{
		Optional<Prtljag> prtljag = prtljagRepo.findById(id);
		
		if(prtljag.isPresent())
			return prtljagConv.convertToDTO(prtljag.get());
		else
			return null;
	}
	
	public List<PrtljagDTO> findAll()
	{
		Optional<List<Prtljag>> list = Optional.of(prtljagRepo.findAll());
		
		List<PrtljagDTO> listDto = new ArrayList<PrtljagDTO>();
		
		if(list.isPresent())
		{
			for(Prtljag prtljag : list.get())
				listDto.add(prtljagConv.convertToDTO(prtljag));
			
			return listDto;
		}
		else
			return null;
	}
	
	public PrtljagDTO saveOne(PrtljagDTO dto)
	{
		Optional<Prtljag> prtljag = prtljagRepo.findById(dto.getIdPrtljaga());
						
		if(prtljag.isPresent())
			return null;
		else
		{			
			prtljagRepo.save(prtljagConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	public PrtljagDTO updateOne(Long id, PrtljagDTO dto)
	{
		Optional<Prtljag> prtljag = prtljagRepo.findById(id);
		
		if(prtljag.isPresent())
		{
			prtljag.get().setIdPrtljaga(prtljagConv.convertFromDTO(dto).getIdPrtljaga());
			prtljag.get().setTezina(prtljagConv.convertFromDTO(dto).getTezina());
			prtljag.get().setOpis(prtljagConv.convertFromDTO(dto).getOpis());
			
			prtljagRepo.save(prtljag.get());
			
			return prtljagConv.convertToDTO(prtljag.get());
		}
		else
			return null;
	}
	
	public boolean deleteOne(Long id)
	{
		Optional<Prtljag> prtljag = prtljagRepo.findById(id);
		
		if(prtljag.isPresent())
		{
			prtljagRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
}