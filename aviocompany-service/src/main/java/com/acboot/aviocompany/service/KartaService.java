package com.acboot.aviocompany.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.KartaConverter;
import com.acboot.aviocompany.converter.KorisnikConverter;
import com.acboot.aviocompany.dto.KartaDTO;
import com.acboot.aviocompany.model.Karta;
import com.acboot.aviocompany.model.Korisnik;
import com.acboot.aviocompany.model.Let;
import com.acboot.aviocompany.repository.KartaRepository;
import com.acboot.aviocompany.repository.KorisnikRepository;
import com.acboot.aviocompany.repository.LetRepository;

@Service
public class KartaService
{
	@Autowired
	private KartaRepository kartaRepo;
	
	@Autowired
	private KartaConverter kartaConv;
	
	@Autowired
	private KorisnikRepository korisnikRepo;
	
	@Autowired
	private KorisnikConverter korisnikConv;
	
	@Autowired
	private LetRepository letRepo;
	
	
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
			dto.setOcena(0);
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
			karta.get().setVremeRezervisanja(kartaConv.convertFromDTO(dto).getVremeRezervisanja());
			karta.get().setKorisnik(kartaConv.convertFromDTO(dto).getKorisnik());
			
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



	////////////////////////
	
	public Boolean rezervisiJednuKartu(Long idKorisnika, Long idKarte)
	{
		System.out.println("USAO U SERVIS");
		Optional<Karta> karta = kartaRepo.findById(idKarte);
		System.out.println(karta.get().getCena());
		
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		System.out.println(korisnik.get().getEmail());
		
		LocalDateTime date = LocalDateTime.now();
		
		if(karta.isPresent() && korisnik.isPresent())
		{
			if(karta.get().getKorisnik().getId() == 1 && karta.get().getVremeRezervisanja().getYear() < 2002) //ako nije rezervisana
			{
				Long id = 9999L;
				//rezervisacemo je
				
				karta.get().setVremeRezervisanja(date);
				karta.get().setKorisnik(korisnik.get());
				karta.get().getLet().setBrojOsoba(karta.get().getLet().getBrojOsoba()+1);
				
				kartaRepo.save(karta.get());
			}
			else
				return false;
		}
		else
			return false;
		
		return true;
	}

	/*
	 * Rezervacija vise karata (nisam testirao)
	 */
	public String rezervisiViseKarata(Long idKorisnika, List<KartaDTO> karte)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		LocalDateTime date = LocalDateTime.now();
		
			for(KartaDTO dto : karte)
			{
				if(dto.getKorisnik().getId() != 1 || dto.getVremeRezervisanja().getYear() > 2002)
					return "POSTOJI vec rezervisana karta za let broj " + dto.getLet().getBrojLeta();
				
				dto.setKorisnik(korisnikConv.convertToDTO(korisnik.get()));
				dto.setVremeRezervisanja(date);
				
				kartaRepo.save(kartaConv.convertFromDTO(dto));
			}
			
			return "REZERVISANE";
	}
	
	
	public boolean obrisiRezervacijuJedneKarte(Long idKorisnika, Long idKarte) 
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		Optional<Karta> karta = kartaRepo.findById(idKarte);
		
		LocalDateTime date = LocalDateTime.now();
		
		if(karta.get().getLet().getVremePoletanja().getHour() > 3 || karta.get().getLet().getVremePoletanja().getHour() < 24)
		{
			//nije najsrecniji uslov, ispravi ako stignes
			if((karta.get().getLet().getVremePoletanja().getHour() - 3) > date.getHour() && karta.get().getLet().getVremePoletanja().getDayOfYear() == date.getDayOfYear() && 
					karta.get().getLet().getVremePoletanja().getYear() == date.getYear() && karta.get().getLet().getVremePoletanja().getMonthValue() == date.getMonthValue())
			{
				//moze otkazati
				karta.get().setKorisnik(korisnikRepo.findById((long) 1).get());
				karta.get().setVremeRezervisanja(LocalDateTime.of(2000, 10, 10, 10, 10));
				karta.get().getLet().setBrojOsoba(karta.get().getLet().getBrojOsoba()-1);
				kartaRepo.save(karta.get());
				
				return true;
			}
			else
			{
				System.out.println("IZASAO NA DRUGOM");
				return false;
			}
				
				
		}
		else
			System.out.println("IZASAO NA PRVOM");
		
		return false;
	}

	/*
	 * Vraca sve nerezervisane karte za jedan let
	 */
	public List<KartaDTO> getAllNerezervisaneKarte(Long idLeta)
	{
		List<Karta> karte = kartaRepo.findAll();
		Optional<Let> let = letRepo.findById(idLeta);
		
		List<KartaDTO> retVal = new ArrayList<KartaDTO>();
		
		for(Karta karta : karte)
		{
			if(karta.getKorisnik().getId() == 1 && karta.getLet().equals(let.get()))
			{
				retVal.add(kartaConv.convertToDTO(karta));
			}
			else
				continue;
		}
		
		return retVal;
	}

	

}

