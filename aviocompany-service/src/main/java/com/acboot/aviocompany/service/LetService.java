package com.acboot.aviocompany.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.LetConverter;
import com.acboot.aviocompany.dto.KlasaDTO;
import com.acboot.aviocompany.dto.LetDTO;
import com.acboot.aviocompany.dto.PretragaDTO;
import com.acboot.aviocompany.dto.PrtljagDTO;
import com.acboot.aviocompany.model.AvioKompanija;
import com.acboot.aviocompany.model.Karta;
import com.acboot.aviocompany.model.Klasa;
import com.acboot.aviocompany.model.Korisnik;
import com.acboot.aviocompany.model.Let;
import com.acboot.aviocompany.model.Prtljag;
import com.acboot.aviocompany.repository.AvioKompanijaRepository;
import com.acboot.aviocompany.repository.KartaRepository;
import com.acboot.aviocompany.repository.KorisnikRepository;
import com.acboot.aviocompany.repository.LetRepository;

@Service
public class LetService
{

	@Autowired
	LetRepository letRepo;
	
	@Autowired
	LetConverter letConv;
	
	@Autowired
	private AvioKompanijaRepository avioRepo;
	
	@Autowired
	private KorisnikRepository korisnikRepo;
	
	@Autowired
	private KartaService kartaService;
	
	@Autowired
	private KartaRepository kartaRepo;
	
	
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
	
	/*
	 * ADMIN
	 */
	public List<LetDTO> getAllLetoviZaOdredjenogAdmina(Long idKompanije)
	{
		List<LetDTO> retList = new ArrayList<LetDTO>();
		Optional<AvioKompanija> avio = avioRepo.findById(idKompanije);
		
		for(Let let : letRepo.findAll())
		{
			if(let.getAviokompanija().equals(avio.get()))
			{
				retList.add(letConv.convertToDTO(let));
			}
			else
				continue;
		}
		
		return retList;
	}
	
	public Boolean saveOne(LetDTO dto)
	{
		
		Optional<Korisnik> korisnik = korisnikRepo.findById((long) 1);
		if(letRepo.existsByBrojLeta(dto.getBrojLeta()))
			return false;
		
		Let let = new Let();
		let = letConv.convertFromDTO(dto);
				
			letRepo.save(let);
			Let lett = letRepo.findByBrojLeta(dto.getBrojLeta());
			for(int i=0; i<lett.getBrojMesta(); i++)
			{
				Karta karta = new Karta();
				karta.setBrzaRezervacija(false);
				karta.setCena(letConv.convertFromDTO(dto).getCenaKarte());
				karta.setKorisnik(korisnik.get());
				karta.setKorisnikKojiSaljePozivnicu(korisnik.get());
				karta.setOcena(0);
				karta.setPopust(0);
				karta.setBrojPasosa("0");
				karta.setVremeRezervisanja(LocalDateTime.of(2000, 10, 10, 10, 10));
				karta.setLet(lett);
				kartaRepo.save(karta);
			}
			
			return true;
		
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
			List<Karta> karte = kartaRepo.findAll();
			
			for(Karta karta : karte)
			{
				if(karta.getLet().equals(let.get()))
				{
					kartaRepo.delete(karta);
				}
			}
			
			letRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
	
	
	
	/**
	 * OSTALE OPERACIJE
	 */
	
	
	/*
	 * Postavljanje klasa za odredjeni let
	 */
	public LetDTO addKlaseLeta(LetDTO dto)
	{
		Optional<Let> let = letRepo.findById(letConv.convertFromDTO(dto).getIdLeta());

		let.get().setKlaseKojeLetSadrzi(letConv.convertFromDTO(dto).getKlaseKojeLetSadrzi());
		letRepo.save(let.get());

		return letConv.convertToDTO(let.get());
	}	
	
	/*
	 * Vraca id kompanije za odredjeni let
	 */
	public Long getIdKompanije(Integer idLeta)
	{
		Long idKompanije = null;
		
		for(Let let : letRepo.findAll())
		{
			if(let.getIdLeta() == idLeta)
				idKompanije = let.getAviokompanija().getIdAvioKompanije();
			else
				continue;
		}
		
		return idKompanije;
	}
	
	
	
	
	  /////////////////////////////////
	 ///////////PRETRAGA//////////////
	/////////////////////////////////
	
	/**
	 * GENERALNA PRETRAGA
	 * @return -> spisak letova koji zadovoljavaju kriterijume
	 */
	public List<LetDTO> searchLetove(PretragaDTO dto)
	{
		List<Let> SVI_LETOVI = letRepo.findAll();
		
		List<LetDTO> ZA_PODUDARANJE = new ArrayList<LetDTO>();
		for(Let let : SVI_LETOVI)
		{
			ZA_PODUDARANJE.add(letConv.convertToDTO(let));
		}
		
		List<LetDTO> retVal = new ArrayList<LetDTO>();
		for(Let let : SVI_LETOVI)
		{
			retVal.add(letConv.convertToDTO(let));
		}
		
		ArrayList<LetDTO> letoviDate = new ArrayList<LetDTO>();
		ArrayList<LetDTO> letoviDestination = new ArrayList<LetDTO>();
		ArrayList<LetDTO> letoviType = new ArrayList<LetDTO>();
		ArrayList<LetDTO> letoviNumber = new ArrayList<LetDTO>();
		ArrayList<LetDTO> letoviClass = new ArrayList<LetDTO>();
		ArrayList<LetDTO> letoviLuggage = new ArrayList<LetDTO>();
		
		//treba naci presek svih ovih
		
		//mora se uneti vreme od-do, ne moze samo jedno ili drugo (zbog query)
		if(dto.getTime1() != null && dto.getTime2() != null)
		{
			Optional<List<Let>> letovi = letRepo.findFlightsByDate(dto.getTime1(), dto.getTime2());
			
			if(letovi.isPresent())
			{
				for(Let let : letovi.get())
				{
					letoviDate.add(letConv.convertToDTO(let));
				}
			}
			retVal.retainAll(letoviDate);
		}
	
		
		if(dto.getTakeOffDestination() != null && dto.getLandingDestination() != null)
		{
			Optional<List<Let>> letovi = letRepo.findFlightsByDestination(dto.getTakeOffDestination(), dto.getLandingDestination());
			
			if(letovi.isPresent())
			{
				for(Let let : letovi.get())
				{
					letoviDestination.add(letConv.convertToDTO(let));
				}
			}
			retVal.retainAll(letoviDestination);
		}
		
		//ako se izostavi ovaj parametar ne vraca nista, popravi to
		//samo ovo je osetljivo na null exception wtff
		if(dto.getType() != null || !dto.getType().equals(""))
		{
			Optional<List<Let>> letovi = letRepo.findFlightsByType(dto.getType());
			
			if(letovi.isPresent())
			{
				for(Let let : letovi.get())
				{
					letoviType.add(letConv.convertToDTO(let));
				}
			}
			retVal.retainAll(letoviType);
		}
		
		if(dto.getNumber() != null)
		{
			List<Let> letoviBroj = letRepo.findAll();
			
			for(Let let : letoviBroj)
			{
				if((letConv.convertToDTO(let).getBrojMesta() - letConv.convertToDTO(let).getBrojOsoba()) >= dto.getNumber())
					letoviNumber.add(letConv.convertToDTO(let));
			}
			retVal.retainAll(letoviNumber);
		}
		
		if(!dto.getKlase().isEmpty())
		{
			List<Let> letoviKlasa = letRepo.findAll();
			
			for(Let let : letoviKlasa)
			{
				for(Klasa klasa : let.getKlaseKojeLetSadrzi())
				{
					for(KlasaDTO str : dto.getKlase())
					{
						if(klasa.getNaziv().equals(str.getNaziv()))
						{
							letoviClass.add(letConv.convertToDTO(let));
						}
					}
				}
			}
			retVal.retainAll(letoviClass);
		}
		
		if(!dto.getPrtljag().isEmpty())
		{
			List<Let> letoviPrtljag = letRepo.findAll();
			
			for(Let let : letoviPrtljag)
			{
				for(Prtljag prtljag : let.getTipoviPrtljagaPoLetu())
				{
					for(PrtljagDTO str : dto.getPrtljag())
					{
						if(prtljag.getOpis().equals(str.getOpis()))
						{
							letoviLuggage.add(letConv.convertToDTO(let));
						}
					}
				}
			}
			retVal.retainAll(letoviLuggage);
		}
		
		return retVal;
	}
	
	

	/*
	 * Pretraga po datumu od-do
	 */
	public List<LetDTO> searchLetoviPoVremenu(LocalDateTime time1, LocalDateTime time2) 
	{
		Optional<List<Let>> letovi = letRepo.findFlightsByDate(time1, time2);
		
		ArrayList<LetDTO> letDtos = new ArrayList<LetDTO>();
		
		if(letovi.isPresent())
		{
			for(Let let : letovi.get())
			{
				letDtos.add(letConv.convertToDTO(let));
			}
			return letDtos;
		}
		
		return Collections.emptyList();
	}

	
	/*
	 * Pretraga po polaznim i odredisnim destinacijama
	 */
	public List<LetDTO> searchLetoviPoDestinaciji(Long takeOffDestination, Long landingDestination)
	{
		Optional<List<Let>> letovi = letRepo.findFlightsByDestination(takeOffDestination, landingDestination);
		
		ArrayList<LetDTO> dtos = new ArrayList<LetDTO>();
		
		if(letovi.isPresent())
		{
			for(Let let : letovi.get())
			{
				dtos.add(letConv.convertToDTO(let));
			}
		}
		return dtos;
	}
	
	
	/*
	 * Pretraga letova po tipu leta
	 */
	public List<LetDTO> searchLetoviPoTipu(String type)
	{
		Optional<List<Let>> letovi = letRepo.findFlightsByType(type);
		
		ArrayList<LetDTO> dtos = new ArrayList<LetDTO>();
		
		if(letovi.isPresent())
		{
			for(Let let : letovi.get())
			{
				dtos.add(letConv.convertToDTO(let));
			}
		}
		
		return dtos;
	}

	
	/*
	 * Pretraga po broju preostalih slobodnih mesta
	 */
	public List<LetDTO> searchLetoviPoBrojuMesta(Integer number)
	{
		List<Let> letovi = letRepo.findAll();
		
		List<LetDTO> dtos = new ArrayList<LetDTO>();
		
		for(Let let : letovi)
		{
			if((letConv.convertToDTO(let).getBrojMesta() - letConv.convertToDTO(let).getBrojOsoba()) >= number)
				dtos.add(letConv.convertToDTO(let));
				
		}
		
		return dtos;
	}

	/*
	 * Pretraga po klasama koje let podrzava
	 */
	public List<LetDTO> searchLetoviPoKlasama(List<KlasaDTO> klase)
	{
		List<Let> letovi = letRepo.findAll();
		
		List<LetDTO> dtos = new ArrayList<LetDTO>();
		
		for(Let let : letovi)
		{
			for(Klasa klasa : let.getKlaseKojeLetSadrzi())
			{
				for(KlasaDTO str : klase)
				{
					if(klasa.getNaziv().equals(str.getNaziv()))
					{
						dtos.add(letConv.convertToDTO(let));
					}
				}
			}
		}
		
		return dtos;
	}
	
	
	
	/*
	 * ADMIN OPERACIJE
	 */
	
	
	/*
	 * Pronalazi prosecnu ocenu za jedan let
	 * Trebalo bi da se poziva nakon svakog ocenjivanja od strane korisnika
	 * Tako da u ril tajmu podesi prosecnu ocenu
	 */
	public Float getSrednjaOcenaLeta(Long id)
	{
		Optional<Float> avg = letRepo.findAverageRating(id);
		
		if(avg.isPresent())
		{
			Let let = letRepo.findById(id).get();
			let.setProsecnaOcena(avg.get());
			letRepo.save(let);
			
			return avg.get();
		}
		
		return null;
	}

	

	

	

}
