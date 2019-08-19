package com.isa.hoteli.hoteliservice.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;
import com.isa.hoteli.hoteliservice.repository.HotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;

@Component
public class RezervacijeService {
	
	@Autowired
	private RezervacijeRepository rezervacijeRepository;
	
	@Autowired
	private HotelskaSobaRepository hotelskaSobaRepository;
	
	public List<Rezervacije> getRezervations(){
		return rezervacijeRepository.findAll();
	}
	
	public List<Rezervacije> getReservationsFromHotelRoom(Long id){
		return rezervacijeRepository.getAllFromHotelRoom(id);
	}
	
	public List<Rezervacije> getReservationsFromUser(Long id){
		return rezervacijeRepository.findByKorisnik(id);
	}
	
	public Rezervacije getReservationById(Long id){
		return rezervacijeRepository.getOne(id);
	}
	
	public RezervacijeDTO createReservation(Rezervacije obj) {
		Optional<HotelskaSoba> hs = hotelskaSobaRepository.findById(obj.getHotelskaSoba().getId());
		List<Rezervacije> lista = rezervacijeRepository.findKonfliktRezervacije(obj.getHotelskaSoba().getId(), obj.getDatumOd(), obj.getDatumDo());
		if(hs.isPresent() && lista.isEmpty()) {
			obj.setHotel(hs.get().getHotel());
			return new RezervacijeDTO(rezervacijeRepository.save(obj));
		}
		return null;
	}
	
	public String deleteReservation(Long id) {
		Rezervacije rezervacije = rezervacijeRepository.getOne(id);
		if(rezervacije!=null) {
			java.util.Date sadasnjost = new java.util.Date();//koji je danas datum
			Date datumOd = rezervacije.getDatumOd();//datum kada pocinje rezervacija
			Calendar c = Calendar.getInstance(); 
			c.setTime(sadasnjost); 
			c.add(Calendar.DATE, 2);//dodamo max dana za otkazivanje na danas
			sadasnjost = c.getTime();
			if(sadasnjost.before(datumOd)) {
				rezervacijeRepository.deleteById(id);
				return "Uspesno obrisana rezervacija sa id: " + id;
			}
			return null;
		}
		return null;
	}
	
	public RezervacijeDTO updateReservation(Rezervacije obj, Long id) {
		Optional<Rezervacije> obj1 = rezervacijeRepository.findById(id);
		if(obj1.isPresent()) {
			obj1.get().setKorisnik(obj.getKorisnik());
			obj1.get().setDatumDo(obj.getDatumDo());
			obj1.get().setDatumOd(obj.getDatumOd());
			obj1.get().setUkupnaCena(obj.getUkupnaCena());
			obj1.get().setBrojOsoba(obj.getBrojOsoba());
			obj1.get().setHotelskaSoba(obj.getHotelskaSoba());
			rezervacijeRepository.save(obj1.get());
			return new RezervacijeDTO(obj1.get());
		}
		return null;
	}
	
	public int dnevnaPosecenost(Long id, Date date) {
		if( rezervacijeRepository.dnevnaPosecenost(id, date)!=null) {
			return rezervacijeRepository.dnevnaPosecenost(id, date);
		}
		return 0;
	}
	
	public int nedeljnaPosecenost(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate nedeljaKasnije = ld.plusWeeks(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( nedeljaKasnije );
		if(rezervacijeRepository.nedeljnaMesecnaPosecenost(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljnaMesecnaPosecenost(id, date, datumDo);
		}
		return 0;
	}
	
	public int mesecnaPosecenost(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate mesecKasnije = ld.plusMonths(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( mesecKasnije );
		if(rezervacijeRepository.nedeljnaMesecnaPosecenost(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljnaMesecnaPosecenost(id, date, datumDo);
		}
		return 0;
	}
	
	public float nedeljniPrihod(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate nedeljaKasnije = ld.plusWeeks(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( nedeljaKasnije );
		if(rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo);
		}
		return 0;
	}
	
	public float mesecniPrihod(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate mesecKasnije = ld.plusMonths(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( mesecKasnije );
		if(rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo);
		}
		return 0;
	}
	
	public float godisnjiPrihod(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate godinaKasnije = ld.plusYears(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( godinaKasnije );
		if(rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo);
		}
		return 0;
	}

}
