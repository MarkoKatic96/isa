package com.acboot.aviocompany.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.KartaConverter;
import com.acboot.aviocompany.dto.KartaDTO;
import com.acboot.aviocompany.model.Karta;
import com.acboot.aviocompany.repository.KartaRepository;

@Service
public class KartaService
{
	@Autowired
	KartaRepository kartaRepo;
	
	@Autowired
	KartaConverter kartaConv;
	
	
	public KartaDTO findById(Long id)
	{
		Optional<Karta> karta = kartaRepo.findById(id);
		
		if(karta.isPresent())
			return kartaConv.convertToDTO(karta.get());
		else
			return null;
	}
	
	public List<KartaDTO> findAll()
	{
		Optional<List<Karta>> list = Optional.of(kartaRepo.findAll());
		
		List<KartaDTO> listDto = new ArrayList<KartaDTO>();
		
		if(list.isPresent())
		{
			for(Karta karta : list.get())
				listDto.add(kartaConv.convertToDTO(karta));
			
			return listDto;
		}
		else
			return null;
	}
	
	public KartaDTO saveOne(KartaDTO dto)
	{
		Optional<Karta> karta = kartaRepo.findById(dto.getIdKarte());
						
		if(karta.isPresent())
			return null;
		else
		{			
			kartaRepo.save(kartaConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	public KartaDTO updateOne(Long id, KartaDTO dto)
	{
		Optional<Karta> karta = kartaRepo.findById(id);
		
		if(karta.isPresent())
		{
			karta.get().setIdKarte(kartaConv.convertFromDTO(dto).getIdKarte());
			karta.get().setCena(kartaConv.convertFromDTO(dto).getCena());
			karta.get().setOcena(kartaConv.convertFromDTO(dto).getOcena());
			karta.get().setBrzaRezervacija(kartaConv.convertFromDTO(dto).isBrzaRezervacija());
			karta.get().setPopust(kartaConv.convertFromDTO(dto).getPopust());
			karta.get().setLet(kartaConv.convertFromDTO(dto).getLet());
			
			kartaRepo.save(karta.get());
			
			return kartaConv.convertToDTO(karta.get());
		}
		else
			return null;
	}
	
	public boolean deleteOne(Long id)
	{
		Optional<Karta> karta = kartaRepo.findById(id);
		
		if(karta.isPresent())
		{
			kartaRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
}
