package com.acboot.aviocompany.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.KartaConverter;
import com.acboot.aviocompany.converter.KorisnikConverter;
import com.acboot.aviocompany.dto.KartaDTO;
import com.acboot.aviocompany.dto.KorisnikDTO;
import com.acboot.aviocompany.dto.SlanjePozivniceZaRezervacijuDTO;
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
	
	@Autowired
	private MailService mailService;
	
	
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
	
	public Boolean brzaRezervacijaJedneKarte(Long idKorisnika, Long idKarte)
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
				karta.get().setBrojPasosa(korisnik.get().getBrojPasosa());
				karta.get().getLet().setBrojOsoba(karta.get().getLet().getBrojOsoba()+1);
				karta.get().getLet().setUkupanPrihod(karta.get().getLet().getUkupanPrihod() + karta.get().getCena() - (karta.get().getCena() * karta.get().getPopust()/100));
				try {
					mailService.sendNotificaitionAsync(korisnik.get(), null, "RESERVATION");
				} catch (MailException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				kartaRepo.save(karta.get());
			}
			else
				return false;
		}
		else
			return false;
		
		return true;
	}
	
	public boolean postaviKartuNaBrzuRezervaciju(Long idKarte, Integer popust)
	{
		Optional<Karta> karta = kartaRepo.findById(idKarte);
		
		karta.get().setBrzaRezervacija(true);
		karta.get().setPopust(popust);
		
		kartaRepo.save(karta.get());
		
		return true;
	}

	public List<KartaDTO> getAllNerezervisaneKarte() 
	{
		List<Karta> karte = kartaRepo.findAll();
		
		List<KartaDTO> retList = new ArrayList<KartaDTO>();
		
		for(Karta karta : karte)
		{
			if(karta.isBrzaRezervacija() && karta.getKorisnik().getId() == 1)
			{
				retList.add(kartaConv.convertToDTO(karta));
			}
		}
		
		return retList;
	}

	/*
	 * Rezervacija vise karata 
	 */
	public String rezervisiViseKarata(Long idKorisnika, SlanjePozivniceZaRezervacijuDTO pozivnica)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		List<KorisnikDTO> prijatelji = pozivnica.getListaPrijatelja();
		List<KartaDTO> karte = pozivnica.getListaKarata();
		List<String> brojeviPasosa = pozivnica.getBrojeviPasosa();
		
		//ako postoji neka osoba da nije u prijateljima
		for(KorisnikDTO dto : prijatelji)
		{
			//nez zasto ovakav uslov al radi tako da ne diraj nista :D
			if(korisnik.get().getPrijateljiKorisnika().contains(korisnikConv.convertFromDTO(dto)))
				return "NOT_FRIEND_ERR";
		}
			
		
		//za te prijatelje koje dobijemo ovde (npr rezervisali smo 5 karata a pozvali 2 prijatelja, preostale 3 ce ici na nas)
		//jedan prijatelj = jedna rezervacija

		
		//izbacujemo duplikate iz prijatelja (nisam uspeo u reactu)
		Set<KorisnikDTO> dist = new LinkedHashSet<KorisnikDTO>(); 
		dist.addAll(prijatelji); 
		prijatelji.clear(); 
		prijatelji.addAll(dist);
		
		LocalDateTime date = LocalDateTime.now();
		
		for(KartaDTO karta : karte)
		{
			if(prijatelji.isEmpty())
				break;
				
				for(KorisnikDTO prijatelj : prijatelji)
				{
						if(karta.getKorisnik().getId() == 1)
						{
							karta.setKorisnik(prijatelj);
							karta.setBrojPasosa(prijatelj.getBrojPasosa());
							karta.setKorisnikKojiSaljePozivnicu(korisnikConv.convertToDTO(korisnik.get()));
							//necemo ovde rezervisati vreme, to cemo tek kad prijatelj prihvati rezervaciju
							//karta.setVremeRezervisanja(date); 
							kartaRepo.save(kartaConv.convertFromDTO(karta));
							
							//slanje mail-a sa linkom
							try {
								mailService.sendNotificaitionAsync(korisnik.get(), korisnikConv.convertFromDTO(prijatelj), "FR_INV");
							} catch (MailException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							prijatelji.remove(prijatelj);
							if(prijatelji.isEmpty())
							{
								break;
							}
						}
				}
		}
		
		
		//sad pravimo novu listu karata koje su preostale, dakle koje nisu otisle kao pozivnice
		
		List<KartaDTO> novaListaKarata = new ArrayList<KartaDTO>();
		
		for(KartaDTO karta : karte)
		{
			if(karta.getKorisnik().getId() == 1)
			{
				novaListaKarata.add(karta);
			}
		}
		
		boolean flag = false;
		//Jedna ide na nas, ostale idu na prosledjene brojeve pasosa
			for(KartaDTO dto : novaListaKarata)
			{
				Karta card = kartaConv.convertFromDTO(dto);
				if(!flag)
				{
					//postavljamo broj zauzetih mesta za konkretan let
					Optional<Let> let = letRepo.findById(dto.getLet().getIdLeta());
					let.get().setBrojOsoba(let.get().getBrojOsoba() + karte.size());
					letRepo.save(let.get());
					
					//prvi broj pasosa ide na korisnika
					card.setBrojPasosa(korisnik.get().getBrojPasosa());
					card.setKorisnik(korisnik.get()); 
					card.setVremeRezervisanja(date);
					kartaRepo.save(card);
				}				
				card.setKorisnik(korisnik.get()); 
				card.setVremeRezervisanja(date);
				
				for(String pasos : brojeviPasosa)
				{
					card.setBrojPasosa(pasos);
					brojeviPasosa.remove(pasos);
					break;
				}
				
				kartaRepo.save(card);
				flag = true;
			}
			
			try {
				mailService.sendNotificaitionAsync(korisnik.get(), null, "RESERVATION");
			} catch (MailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		}
		
		return retVal;
	}

	

	

}

