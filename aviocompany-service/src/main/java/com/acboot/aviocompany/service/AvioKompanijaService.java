package com.acboot.aviocompany.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.AvioKompanijaConverter;
import com.acboot.aviocompany.dto.AvioKompanijaDTO;
import com.acboot.aviocompany.model.AvioKompanija;
import com.acboot.aviocompany.model.Let;
import com.acboot.aviocompany.repository.AvioKompanijaRepository;

@Service
public class AvioKompanijaService 
{
	@Autowired
	AvioKompanijaRepository avioRepo;
	
	@Autowired
	AvioKompanijaConverter avioConv;
	
	@Autowired
	private LetService letService;
	
	
	public AvioKompanijaDTO findById(Long id)
	{
		Optional<AvioKompanija> avio = avioRepo.findById(id);
		
		if(avio.isPresent())
			return avioConv.convertToDTO(avio.get());
		else
			return null;
	}
	
	public List<AvioKompanijaDTO> findAll()
	{
		Optional<List<AvioKompanija>> list = Optional.of(avioRepo.findAll());
		
		List<AvioKompanijaDTO> listDto = new ArrayList<AvioKompanijaDTO>();
		
		if(list.isPresent())
		{
			for(AvioKompanija avio : list.get())
				listDto.add(avioConv.convertToDTO(avio));
			
			return listDto;
		}
		else
			return null;
	}
	
	public AvioKompanijaDTO saveOne(AvioKompanijaDTO dto)
	{
		Optional<AvioKompanija> avio = avioRepo.findById(dto.getIdAvioKompanije());
						
		
		if(avio.isPresent())
			return null;
		else
		{			
			avioRepo.save(avioConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	public AvioKompanijaDTO updateOne(Long id, AvioKompanijaDTO dto)
	{
		Optional<AvioKompanija> avio = avioRepo.findById(id);
		
		if(avio.isPresent())
		{
//			avio.get().setIdAvioKompanije(avioConv.convertFromDTO(dto).getIdAvioKompanije());
			avio.get().setNaziv(avioConv.convertFromDTO(dto).getNaziv());
			avio.get().setAdresa(avioConv.convertFromDTO(dto).getAdresa());
			avio.get().setOpis(avioConv.convertFromDTO(dto).getOpis());
			
//			if(!avio.get().getDestinacijeNaKojimaPosluje().isEmpty())
				avio.get().setDestinacijeNaKojimaPosluje(avioConv.convertFromDTO(dto).getDestinacijeNaKojimaPosluje());
			
			avioRepo.save(avio.get());
			
			return avioConv.convertToDTO(avio.get());
		}
		else
			return null;
	}
	
	
	public boolean deleteOne(Long id)
	{
		Optional<AvioKompanija> avio = avioRepo.findById(id);
		
		if(avio.isPresent())
		{
			List<Let> letovi = avio.get().getLetovi();
			
			for(Let let : letovi)
			{
				letService.deleteOne(let.getIdLeta());
			}
			
			avioRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
	
	
	
	/*
	 * OSTALE OPERACIJE
	 */
	

	public Float getSrednjaOcenaAvioKompanije(Long id)
	{
		Optional<Float> avg = avioRepo.findAverageRating(id);
		
		if(avg.isPresent())
		{
			return avg.get();
		}
		
		return null;
	}
	
}
