package com.isa.hoteli.hoteliservice.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.repository.KartaRepository;
import com.isa.hoteli.hoteliservice.avio.service.KartaService;


@RunWith(SpringRunner.class)
public class KartaServiceTest
{
	private Karta karta1 = new Karta(1l, 100, 5, false, 0, "a", null, LocalDateTime.now(), null, null);
	private Karta karta2 = new Karta(2l, 200, 1, true, 0, "b", null, LocalDateTime.now(), null, null);
	private List<Karta> karte = new ArrayList<>();
	
	@Mock
	private KartaRepository kartaRepo;
	
	@InjectMocks
	private KartaService kartaService;
	
	@Before
	public void setUp() {
		karte.add(karta1);
		karte.add(karta2);
	}
	
	@Test
	public void traziByIdSuccess()
	{
		when(kartaRepo.getOne(1l)).thenReturn(karta1);
		Karta kartaM = kartaService.traziById(1l);
		assertEquals(kartaM, karta1);
		verify(kartaRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void traziSveSuccess()
	{
		when(kartaRepo.findAll()).thenReturn(karte);
		List<Karta> karteM = kartaService.traziSve();
		assertEquals(karteM, karte);
		verify(kartaRepo, times(1)).findAll();
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void saveOneSuccess()
	{
		when(kartaRepo.save(karta1)).thenReturn(karta1);
		Karta kartaM = kartaService.saveOne(karta1);
		assertEquals(kartaM, karta1);
		verify(kartaRepo, times(1)).save(karta1);
	}
	
	@Test
	public void updateOneSuccess()
	{
		when(kartaRepo.getOne(1l)).thenReturn(karta1);
		when(kartaRepo.save(karta1)).thenReturn(karta1);
		Karta kartaM = kartaService.updateOne(1l, karta1);
		assertEquals(kartaM, karta1);
		verify(kartaRepo, times(1)).getOne(1l);
		verify(kartaRepo, times(1)).save(karta1);
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void updateOneFailed()
	{
		when(kartaRepo.getOne(1l)).thenReturn(null);
		kartaService.updateOne(1l, karta1);
		verify(kartaRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void deleteOneSuccess()
	{
		when(kartaRepo.getOne(1l)).thenReturn(karta1);
		doNothing().when(kartaRepo).deleteById(1l);
		boolean res = kartaService.deleteOne(1l);
		assertEquals(true, res);
		verify(kartaRepo, times(1)).getOne(1l);
		verify(kartaRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(kartaRepo);
	}
}
