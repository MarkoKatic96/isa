package com.isa.hoteli.hoteliservice.avio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.hoteli.hoteliservice.avio.converter.DestinacijaConverter;
import com.isa.hoteli.hoteliservice.avio.dto.DestinacijaDTO;
import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;
import com.isa.hoteli.hoteliservice.avio.repository.AvioKompanijaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.DestinacijaRepository;


@Service
public class DestinacijaService 
{
	@Autowired
	DestinacijaRepository destRepo;
	
	@Autowired
	DestinacijaConverter destConv;
	
	@Autowired
	private AvioKompanijaRepository avioRepo;
	
	
	public DestinacijaDTO findById(Long id)
	{
		Optional<Destinacija> dest = destRepo.findById(id);
		
		if(dest.isPresent())
			return destConv.convertToDTO(dest.get());
		else
			return null;
	}
	
	public List<DestinacijaDTO> findAll()
	{
		Optional<List<Destinacija>> list = Optional.of(destRepo.findAll());
		
		List<DestinacijaDTO> listDto = new ArrayList<DestinacijaDTO>();
		
		if(list.isPresent())
		{
			for(Destinacija dest : list.get())
				listDto.add(destConv.convertToDTO(dest));
			
			return listDto;
		}
		else
			return null;
	}
	
	public List<DestinacijaDTO> getAllDestinacijeByAvioKompanija(Long idAvioKompanije)
	{
		Optional<AvioKompanija> avio = avioRepo.findById(idAvioKompanije);
		
		List<DestinacijaDTO> listDto = new ArrayList<DestinacijaDTO>();
		
		
			for(Destinacija dest : avio.get().getDestinacijeNaKojimaPosluje())
			{
				listDto.add(destConv.convertToDTO(dest));
			}
				
			return listDto;
	}
	
	public DestinacijaDTO saveOne(DestinacijaDTO dto)
	{
		Optional<Destinacija> dest = destRepo.findById(dto.getIdDestinacije());
						
		
		if(dest.isPresent())
			return null;
		else
		{			
			destRepo.save(destConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	public DestinacijaDTO updateOne(Long id, DestinacijaDTO dto)
	{
		Optional<Destinacija> dest = destRepo.findById(id);
		
		if(dest.isPresent())
		{
			dest.get().setIdDestinacije(destConv.convertFromDTO(dto).getIdDestinacije());
			dest.get().setNaziv(destConv.convertFromDTO(dto).getNaziv());
			dest.get().setInformacije(destConv.convertFromDTO(dto).getInformacije());
			
			destRepo.save(dest.get());
			
			return destConv.convertToDTO(dest.get());
		}
		else
			return null;
	}
	
	public boolean deleteOne(Long id)
	{
		Optional<Destinacija> dest = destRepo.findById(id);
		
		if(dest.isPresent())
		{
			destRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}

	
	
}
