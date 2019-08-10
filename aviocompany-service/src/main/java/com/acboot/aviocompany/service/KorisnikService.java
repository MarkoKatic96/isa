package com.acboot.aviocompany.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.converter.KartaConverter;
import com.acboot.aviocompany.converter.KorisnikConverter;
import com.acboot.aviocompany.dto.KartaDTO;
import com.acboot.aviocompany.dto.KorisnikDTO;
import com.acboot.aviocompany.model.Karta;
import com.acboot.aviocompany.model.Korisnik;
import com.acboot.aviocompany.model.Let;
import com.acboot.aviocompany.repository.KartaRepository;
import com.acboot.aviocompany.repository.KorisnikRepository;
import com.acboot.aviocompany.repository.LetRepository;
import com.acboot.aviocompany.security.JwtTokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KorisnikService
{
	@Autowired
	private KorisnikRepository korisnikRepo;
	
	@Autowired
	private KorisnikConverter korisnikConv;
	
	@Autowired
	private LetRepository letRepo;
	
	@Autowired
	private KartaRepository kartaRepo;
	
	@Autowired
	private KartaConverter kartaConv;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private JwtTokenUtils jwtTokenProvider;
	
	public String posaljiZahtev(Long idKorisnika, String email)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		Optional<Korisnik> prijatelj = korisnikRepo.getUserByEmail(email);
		
		if(!prijatelj.isPresent())
			return "EMAIL_ERR";
		
		if(korisnik.get().getZahteviKorisnika().contains(prijatelj.get()))
			return "EXISTS_ERR";
		
		if(korisnik.get().getPrijateljiKorisnika().contains(prijatelj.get()))
			return "EXISTS_ERR";
		
		List<Korisnik> insert = new ArrayList<Korisnik>();
		insert.add(prijatelj.get());
		
		korisnik.get().setZahteviKorisnika(insert);
		korisnikRepo.save(korisnik.get());
		
		
		try {
			mailService.sendNotificaitionAsync(korisnik.get(), prijatelj.get());
		} catch (MailException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "SUCCESS";
	}

	/*
	 * Prihvatanje zahteva korisnika za prijateljstvo (BACA CONCURENTMODIFICATIONEXCEPTION)
	 */
	public String prihvatiZahtev(Long idTrenutni, Long idPosiljalac) 
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idTrenutni);
		Optional<Korisnik> prijateljKojiSalje = korisnikRepo.findById(idPosiljalac);
		
//		if(!prijateljKojiSalje.isPresent())
//			return "EMAIL_ERR";
		
		if(korisnik.get().getPrijateljiKorisnika().contains(prijateljKojiSalje.get()))
			return "EXISTS_ERR";
		
		List<Korisnik> insert = new ArrayList<Korisnik>();
		insert.add(prijateljKojiSalje.get());
		
		korisnik.get().setPrijateljiKorisnika(insert);
		korisnikRepo.save(korisnik.get());
		
		//brisanje iz liste zahteva
		
		List<Korisnik> zahteviKorisnika = korisnik.get().getZahteviKorisnika();
		
		List<Korisnik> sviZahtevi = korisnik.get().getZahteviKorisnika();
		
		for(Korisnik prijateljZaht : zahteviKorisnika)
		{
			System.out.println(prijateljZaht.getIme());
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				sviZahtevi.remove(prijateljZaht);
				korisnik.get().setZahteviKorisnika(sviZahtevi);
				korisnikRepo.save(korisnik.get());
			}
		}
		
		return "SUCCESS";
	}
	
	//moze i jednostavniji nacin al model nije ok
	public String odbijZahtev(Long idTrenutni, Long idPosiljalac) 
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idTrenutni);
		Optional<Korisnik> prijateljKojiSalje = korisnikRepo.findById(idPosiljalac);
		
		//brisanje iz liste zahteva
		

		List<Korisnik> prijateljiKorisnika = korisnik.get().getZahteviKorisnika();
		
		List<Korisnik> sviPrijatelji = korisnik.get().getZahteviKorisnika();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika)
		{
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				System.out.println("USAO U IF 1");
				sviPrijatelji.remove(prijateljZaht);
				korisnik.get().setZahteviKorisnika(sviPrijatelji);
				korisnikRepo.save(korisnik.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		List<Korisnik> prijateljiKorisnika2 = korisnik.get().getKorisniciZaht();
		
		List<Korisnik> sviPrijatelji2 = korisnik.get().getKorisniciZaht();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika2)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 2");
				sviPrijatelji2.remove(prijateljZaht);
				korisnik.get().setKorisniciZaht(sviPrijatelji2);
				korisnikRepo.save(korisnik.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		
		List<Korisnik> prijateljiKorisnika3 = prijateljKojiSalje.get().getZahteviKorisnika();
		
		List<Korisnik> sviPrijatelji3 = prijateljKojiSalje.get().getZahteviKorisnika();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika3)
		{
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				System.out.println("USAO U IF 3");
				sviPrijatelji3.remove(prijateljZaht);
				prijateljKojiSalje.get().setZahteviKorisnika(sviPrijatelji3);
				korisnikRepo.save(prijateljKojiSalje.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		List<Korisnik> prijateljiKorisnika4 = prijateljKojiSalje.get().getKorisniciZaht();
		
		List<Korisnik> sviPrijatelji4 = prijateljKojiSalje.get().getKorisniciZaht();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika4)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 4");
				sviPrijatelji4.remove(prijateljZaht);
				prijateljKojiSalje.get().setKorisniciZaht(sviPrijatelji4);
				korisnikRepo.save(prijateljKojiSalje.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		return "SUCCESS";
	}
	
	
	public String obrisiPrijatelja(Long idTrenutni, Long idZaBrisanje)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idTrenutni);
		Optional<Korisnik> prijateljKojiSalje = korisnikRepo.findById(idZaBrisanje);
		
		List<Korisnik> prijateljiKorisnika = korisnik.get().getPrijateljiKorisnika();
		
		List<Korisnik> sviPrijatelji = korisnik.get().getPrijateljiKorisnika();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika)
		{
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				System.out.println("USAO U IF 1");
				sviPrijatelji.remove(prijateljZaht);
				korisnik.get().setPrijateljiKorisnika(sviPrijatelji);
				korisnikRepo.save(korisnik.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		List<Korisnik> prijateljiKorisnika2 = korisnik.get().getKorisnici();
		
		List<Korisnik> sviPrijatelji2 = korisnik.get().getKorisnici();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika2)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 2");
				sviPrijatelji2.remove(prijateljZaht);
				korisnik.get().setKorisnici(sviPrijatelji2);
				korisnikRepo.save(korisnik.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		
		List<Korisnik> prijateljiKorisnika3 = prijateljKojiSalje.get().getPrijateljiKorisnika();
		
		List<Korisnik> sviPrijatelji3 = prijateljKojiSalje.get().getPrijateljiKorisnika();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika3)
		{
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				System.out.println("USAO U IF 3");
				sviPrijatelji3.remove(prijateljZaht);
				prijateljKojiSalje.get().setPrijateljiKorisnika(sviPrijatelji3);
				korisnikRepo.save(prijateljKojiSalje.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		List<Korisnik> prijateljiKorisnika4 = prijateljKojiSalje.get().getKorisnici();
		
		List<Korisnik> sviPrijatelji4 = prijateljKojiSalje.get().getKorisnici();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika4)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 4");
				sviPrijatelji4.remove(prijateljZaht);
				prijateljKojiSalje.get().setKorisnici(sviPrijatelji4);
				korisnikRepo.save(prijateljKojiSalje.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		return "ERROR";
		
	}
	
	/*
	 * OCENA KORISNIKA NAKON ZAVRSENOG LETA
	 */
	public String oceniLet(Long idKorisnika, Long idKarte, Integer ocena)
	{
		Optional<Karta> karta = kartaRepo.findById(idKarte);

		Let let = karta.get().getLet();
		
		LocalDateTime date = LocalDateTime.now();
		
		if(let.getVremeSletanja().isBefore(date))
		{
			karta.get().setOcena(ocena);
			kartaRepo.save(karta.get());
			
			//uzimamo sve ocene
			List<Integer> ocene = new ArrayList<Integer>();
			for(Karta kartaTemp : let.getKarteLeta())
			{
				if(kartaTemp.getOcena() > 0) //default je 0
				{
					ocene.add(kartaTemp.getOcena());
				}
			}
			
			float prosecna = 0;
			float suma = 0;
			float uk = 0;
			//racunamo prosecnu vrednost od njih
			for(int i=0; i<ocene.size(); i++)
			{
				suma += ocene.get(i);
				uk = i+1;
			}
			
			prosecna = suma/uk;
			System.out.println("PROSECNA: " + zaokruzi(prosecna,2));
			let.setProsecnaOcena(zaokruzi(prosecna,2));
			letRepo.save(let);
			
		}
			
		else
			return "FLIGHT_IS_ON";
		
			
		return "SUCCESS";
	}
	
	private float zaokruzi(float broj, int preciznost) {
	    return (float) (Math.round(broj * Math.pow(10, preciznost)) / Math.pow(10, preciznost));
	}
	
	
	
	public List<Korisnik> getUsers()
	{
		return korisnikRepo.findAll();
	}
	
	public Korisnik getUserById(Long id)
	{
		return korisnikRepo.getOne(id);
	}
	
	public Korisnik getUserByEmail(String email)
	{
		return korisnikRepo.getUserByEmail(email).get();
	}
	
	public KorisnikDTO createKorisnika(Korisnik kor)
	{
		if(korisnikRepo.getUserByEmail(kor.getEmail()) == null)
			return new KorisnikDTO(korisnikRepo.save(kor));
		
		return null;
	}
	
	public String deleteKorisnika(Long id) {
		korisnikRepo.deleteById(id);
		return "Uspesno obrisan korisnik sa id: " + id;
	}
	
	public KorisnikDTO updateKorisnika(Korisnik obj, Long id)
	{
		Optional<Korisnik> obj1 = korisnikRepo.findById(id);
		if(obj1.isPresent())
		{
			Optional<Korisnik> k = korisnikRepo.getUserByEmail(obj.getEmail());
		
			if(!k.isPresent() || k.get().getId() == id)
			{
				obj1.get().setEmail(obj.getEmail());
				obj1.get().setLozinka(obj.getLozinka());
				obj1.get().setIme(obj.getIme());
				obj1.get().setPrezime(obj.getPrezime());
				obj1.get().setGrad(obj.getGrad());
				obj1.get().setTelefon(obj.getTelefon());
				korisnikRepo.save(obj1.get());
				
				return new KorisnikDTO(obj1.get());
			}
		}
		
		return null;
	}
	
	public String login(String email, String lozinka)
	{
		Optional<Korisnik> k = korisnikRepo.getUserByEmail(email);
		if (k == null)
			return null;

		try 
		{
			if (k.get().getLozinka().equals(lozinka))
			{
				String jwt = jwtTokenProvider.createToken(email);
				ObjectMapper mapper = new ObjectMapper();

				return mapper.writeValueAsString(jwt);
			}

		} catch (JsonProcessingException e) 
		{
			return null;
		}
		
		return null;
	}
	
	public Korisnik zaTokene(HttpServletRequest req) {
		String token = jwtTokenProvider.resolveToken(req);
		String email = jwtTokenProvider.getUsername(token);
		Korisnik k = this.getUserByEmail(email);
		return k;
	}

	public List<KartaDTO> getAllRezervisaneKarteZaTogKorisnika(Long idKorisnika)
	{
		List<Karta> karte = kartaRepo.findAll();
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		List<KartaDTO> karteRet = new ArrayList<KartaDTO>();
		
		for(Karta karta : karte)
		{
			if(karta.getKorisnik().equals(korisnik.get()))
			{
				karteRet.add(kartaConv.convertToDTO(karta));
			}
			else
				continue;
		}
		
		return karteRet;
	}

	/*
	 * Uzima zahteve koji su poslati OVOM korisniku (a ne koje je ovaj korisnik poslao)
	 */
	public List<KorisnikDTO> getAllZahteviZaPrijateljstvo(Long idKorisnika)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		List<KorisnikDTO> korisniciRet = new ArrayList<KorisnikDTO>();
		
		for(Korisnik kor : korisnik.get().getKorisniciZaht())
		{
			korisniciRet.add(korisnikConv.convertToDTO(kor));
		}
		
		return korisniciRet;
	}

	public List<KorisnikDTO> getaAllPrijatelji(Long idKorisnika)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
	
		List<KorisnikDTO> korisniciRet = new ArrayList<KorisnikDTO>();
		
		for(Korisnik kor : korisnik.get().getPrijateljiKorisnika())
		{
			korisniciRet.add(korisnikConv.convertToDTO(kor));
		}
		
		//mora i sa druge strane
		for(Korisnik kor : korisnik.get().getKorisnici())
		{
			korisniciRet.add(korisnikConv.convertToDTO(kor));
		}
		
		return korisniciRet;
	}

	

	

	
}
