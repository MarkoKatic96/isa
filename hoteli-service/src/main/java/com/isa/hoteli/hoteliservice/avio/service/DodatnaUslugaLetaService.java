package com.isa.hoteli.hoteliservice.avio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.hoteli.hoteliservice.avio.converter.DodatnaUslugaLetaConverter;
import com.isa.hoteli.hoteliservice.avio.dto.DodatnaUslugaLetaDTO;
import com.isa.hoteli.hoteliservice.avio.model.DodatnaUslugaLeta;
import com.isa.hoteli.hoteliservice.avio.repository.DodatnaUslugaLetaRepository;

@Service
public class DodatnaUslugaLetaService 
{
	@Autowired
	DodatnaUslugaLetaRepository uslugaRepo;
	
	@Autowired
	DodatnaUslugaLetaConverter uslugaConv;
	
	
	public DodatnaUslugaLetaDTO findById(Long id)
	{
		Optional<DodatnaUslugaLeta> dest = uslugaRepo.findById(id);
		
		if(dest.isPresent())
			return uslugaConv.convertToDTO(dest.get());
		else
			return null;
	}
	
	public List<DodatnaUslugaLetaDTO> findAll()
	{
		Optional<List<DodatnaUslugaLeta>> list = Optional.of(uslugaRepo.findAll());
		
		List<DodatnaUslugaLetaDTO> listDto = new ArrayList<DodatnaUslugaLetaDTO>();
		
		if(list.isPresent())
		{
			for(DodatnaUslugaLeta dest : list.get())
				listDto.add(uslugaConv.convertToDTO(dest));
			
			return listDto;
		}
		else
			return null;
	}
	
	public DodatnaUslugaLetaDTO saveOne(DodatnaUslugaLetaDTO dto)
	{
		Optional<DodatnaUslugaLeta> dest = uslugaRepo.findById(dto.getIdDodatneUsluge());
						
		
		if(dest.isPresent())
			return null;
		else
		{			
			uslugaRepo.save(uslugaConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	public DodatnaUslugaLetaDTO updateOne(Long id, DodatnaUslugaLetaDTO dto)
	{
		Optional<DodatnaUslugaLeta> dest = uslugaRepo.findById(id);
		
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
		Optional<DodatnaUslugaLeta> dest = uslugaRepo.findById(id);
		
		if(dest.isPresent())
		{
			uslugaRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
	
}
