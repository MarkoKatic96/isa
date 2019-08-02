package com.acboot.aviocompany.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.DodatnaUslugaConverter;
import com.acboot.aviocompany.dto.DodatnaUslugaDTO;
import com.acboot.aviocompany.model.DodatnaUsluga;
import com.acboot.aviocompany.repository.DodatnaUslugaRepository;

@Service
public class DodatnaUslugaService 
{
	@Autowired
	DodatnaUslugaRepository uslugaRepo;
	
	@Autowired
	DodatnaUslugaConverter uslugaConv;
	
	
	public DodatnaUslugaDTO findById(Long id)
	{
		Optional<DodatnaUsluga> dest = uslugaRepo.findById(id);
		
		if(dest.isPresent())
			return uslugaConv.convertToDTO(dest.get());
		else
			return null;
	}
	
	public List<DodatnaUslugaDTO> findAll()
	{
		Optional<List<DodatnaUsluga>> list = Optional.of(uslugaRepo.findAll());
		
		List<DodatnaUslugaDTO> listDto = new ArrayList<DodatnaUslugaDTO>();
		
		if(list.isPresent())
		{
			for(DodatnaUsluga dest : list.get())
				listDto.add(uslugaConv.convertToDTO(dest));
			
			return listDto;
		}
		else
			return null;
	}
	
	public DodatnaUslugaDTO saveOne(DodatnaUslugaDTO dto)
	{
		Optional<DodatnaUsluga> dest = uslugaRepo.findById(dto.getIdDodatneUsluge());
						
		
		if(dest.isPresent())
			return null;
		else
		{			
			uslugaRepo.save(uslugaConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	public DodatnaUslugaDTO updateOne(Long id, DodatnaUslugaDTO dto)
	{
		Optional<DodatnaUsluga> dest = uslugaRepo.findById(id);
		
		if(dest.isPresent())
		{
			dest.get().setIdDodatneUsluge(uslugaConv.convertFromDTO(dto).getIdDodatneUsluge());
			dest.get().setNaziv(uslugaConv.convertFromDTO(dto).getNaziv());
			
			uslugaRepo.save(dest.get());
			
			return uslugaConv.convertToDTO(dest.get());
		}
		else
			return null;
	}
	
	public boolean deleteOne(Long id)
	{
		Optional<DodatnaUsluga> dest = uslugaRepo.findById(id);
		
		if(dest.isPresent())
		{
			uslugaRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
	
}
