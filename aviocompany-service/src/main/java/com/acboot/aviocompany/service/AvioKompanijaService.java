package com.acboot.aviocompany.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.AvioKompanijaConverter;
import com.acboot.aviocompany.dto.AvioKompanijaDTO;
import com.acboot.aviocompany.dto.BrojKarataDnevnoDTO;
import com.acboot.aviocompany.model.AvioKompanija;
import com.acboot.aviocompany.model.Destinacija;
import com.acboot.aviocompany.model.Karta;
import com.acboot.aviocompany.model.Let;
import com.acboot.aviocompany.repository.AvioKompanijaRepository;
import com.acboot.aviocompany.repository.DestinacijaRepository;
import com.acboot.aviocompany.repository.KartaRepository;

@Service
public class AvioKompanijaService 
{
	@Autowired
	AvioKompanijaRepository avioRepo;
	
	@Autowired
	AvioKompanijaConverter avioConv;
	
	@Autowired
	private DestinacijaRepository destRepo;
	
	@Autowired
	private LetService letService;
	
	@Autowired
	private KartaRepository kartaRepo;
	
	
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
	
	public boolean addDefaultDestination(Long id)
	{
		Optional<AvioKompanija> avio = avioRepo.findById(id);
		Optional<Destinacija> dest = destRepo.findById((long) 1);
		
		List<Destinacija> lista = new ArrayList<Destinacija>();
		lista.add(dest.get());
		
		avio.get().setDestinacijeNaKojimaPosluje(lista);
		avioRepo.save(avio.get());
		
		return true;
	}
	
	public AvioKompanijaDTO updateOne(Long id, AvioKompanijaDTO dto)
	{
		Optional<AvioKompanija> avio = avioRepo.findById(id);
		
		if(avio.isPresent())
		{
			//avio.get().setIdAvioKompanije(avioConv.convertFromDTO(dto).getIdAvioKompanije());
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

	
	public List<BrojKarataDnevnoDTO> getBrojProdatihKarataDnevno(Long id)
	{
		List<BrojKarataDnevnoDTO> returnList = new ArrayList<BrojKarataDnevnoDTO>();
		
		LocalDate date = LocalDate.now();
		
		//treba izvuci broj karata za taj i taj dan
		List<Karta> karte = kartaRepo.findAll();
		
		//ide 4 dana unazad i danasnji dan
		int suma = 0;
		for(int i=5; i>0; i--)
		{
			BrojKarataDnevnoDTO obj = new BrojKarataDnevnoDTO();
			LocalDate datum = date.minusDays(i-1);
			
			for(Karta karta : karte)
			{
				if(karta.getVremeRezervisanja().getYear() == datum.getYear() && karta.getVremeRezervisanja().getMonth() == datum.getMonth() && 
						karta.getVremeRezervisanja().getDayOfYear() == datum.getDayOfYear() && karta.getLet().getAviokompanija().getIdAvioKompanije() == id)
				{
					suma++;
				}
			}
			
			obj.setDatum(datum);
			obj.setBrojKarata(suma);
			returnList.add(obj);
			suma = 0;
		}
		
		return returnList;
	}
	
	public List<BrojKarataDnevnoDTO> getBrojProdatihKarataNedeljno(Long id)
	{
		List<BrojKarataDnevnoDTO> returnList = new ArrayList<BrojKarataDnevnoDTO>();
		
		LocalDate date = LocalDate.now();
		
		List<Karta> karte = kartaRepo.findAll();
		
		int suma = 0;
		for(int i=5; i>0; i--)
		{
			BrojKarataDnevnoDTO obj = new BrojKarataDnevnoDTO();
			LocalDate datum = date.minusWeeks(i-1);
			
			for(Karta karta : karte)
			{
				if(karta.getVremeRezervisanja().getYear() == datum.getYear() && karta.getLet().getAviokompanija().getIdAvioKompanije() == id)
				{
					suma++;
				}
			}
			
			obj.setDatum(datum);
			obj.setBrojKarata(suma);
			returnList.add(obj);
			suma = 0;
		}
		
		return returnList;
	}
	
	public List<BrojKarataDnevnoDTO> getBrojProdatihKarataMesecno(Long id)
	{
		List<BrojKarataDnevnoDTO> returnList = new ArrayList<BrojKarataDnevnoDTO>();
		
		LocalDate date = LocalDate.now();
		
		List<Karta> karte = kartaRepo.findAll();
		
		int suma = 0;
		for(int i=5; i>0; i--)
		{
			BrojKarataDnevnoDTO obj = new BrojKarataDnevnoDTO();
			LocalDate datum = date.minusMonths(i-1);
			
			for(Karta karta : karte)
			{
				if(karta.getVremeRezervisanja().getYear() == datum.getYear() && karta.getVremeRezervisanja().getMonth() == datum.getMonth()
						&& karta.getLet().getAviokompanija().getIdAvioKompanije() == id)
				{
					suma++;
				}
			}
			
			obj.setDatum(datum);
			obj.setBrojKarata(suma);
			returnList.add(obj);
			suma = 0;
		}
		
		return returnList;
	}

	public Float getPrihodZaOdredjeniPeriod(Long id, LocalDate datumOd, LocalDate datumDo)
	{
		float prihod = 0;
		//prebacujemo u localdatetime
		LocalDateTime datum1 = datumOd.atStartOfDay();
		LocalDateTime datum2 = datumDo.atStartOfDay();
		
		List<Karta> karte = kartaRepo.findAll();
		
		for(Karta karta : karte)
		{
			if(karta.getLet().getAviokompanija().getIdAvioKompanije() == id)
			{
				if(karta.getVremeRezervisanja().isAfter(datum1) && karta.getVremeRezervisanja().isBefore(datum2))
				{
					prihod += karta.getCena();
				}
			}
			else
				continue;
		}
		
		return prihod;
	}
	
}
