package com.isa.hoteli.hoteliservice.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.repository.CenaNocenjaRepository;
import com.isa.hoteli.hoteliservice.repository.HotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;
import com.isa.hoteli.hoteliservice.service.HotelskaSobaService;

@RunWith(SpringRunner.class)
public class HotelskaSobaServiceTest {

	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a");
	private List<HotelskaSoba> sobe = new ArrayList<>();
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private HotelskaSoba soba2 = new HotelskaSoba(2l, 2, 2, 2, 400, hotel1, null);

	
	@Mock
	private HotelskaSobaRepository hsr;
	
	@Mock 
	private CenaNocenjaRepository cnr;
	
	@Mock
	private RezervacijeRepository rr;
	
	@InjectMocks
	private HotelskaSobaService hss;
	
	@Before
	public void setUp() {
		sobe.add(soba1);
		sobe.add(soba2);
	}
	
	@Test
	public void getAllSuccess() {
		when(hsr.findAll()).thenReturn(sobe);
		List<HotelskaSoba> rooms = hss.getRooms();
		assertEquals(sobe, rooms);
		verify(hsr, times(1)).findAll();
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void getAllFromHotelSuccess() {
		when(hsr.getAllFromHotel(1l)).thenReturn(sobe);
		List<HotelskaSoba> rooms = hss.getRoomsFromHotel(1l);
		assertEquals(sobe, rooms);
		verify(hsr, times(1)).getAllFromHotel(1l);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void getAllFreeFromHotelSuccess() {
		when(hsr.getAllFromHotel(1l)).thenReturn(sobe);
		when(rr.findKonfliktRezervacije(1l, datumOd, datumDo)).thenReturn(new ArrayList<>());
		when(rr.findKonfliktRezervacije(2l, datumOd, datumDo)).thenReturn(new ArrayList<>());;
		List<HotelskaSoba> rooms = hss.getFreeRoomsFromHotel(1l, datumOd, datumDo);
		assertEquals(sobe, rooms);
		verify(hsr, times(1)).getAllFromHotel(1l);
		verify(rr, times(1)).findKonfliktRezervacije(1l, datumOd, datumDo);
		verify(rr, times(1)).findKonfliktRezervacije(2l, datumOd, datumDo);
		verifyNoMoreInteractions(rr);
		verifyNoMoreInteractions(hsr);
	}
	
}
