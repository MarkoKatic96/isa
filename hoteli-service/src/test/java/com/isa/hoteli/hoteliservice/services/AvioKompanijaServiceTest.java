package com.isa.hoteli.hoteliservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.avio.converter.AvioKompanijaConverter;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.repository.AvioKompanijaRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;


@RunWith(SpringRunner.class)
public class AvioKompanijaServiceTest 
{
	private List<AvioKompanija> kompanije = new ArrayList<AvioKompanija>();
	private AvioKompanija kompanija1 = new AvioKompanija(1l, "a", "a", "a", null, null);
	private AvioKompanija kompanija2 = new AvioKompanija(2l, "b", "b", "b", null, null);
	private Optional<Float> srednjaOcena;
	private Optional<List<AvioKompanija>> kompanijeOpt;
	
	private Optional<AvioKompanija> kompanijaOpt;
	
	@Mock
	private AvioKompanijaRepository avioRepo;
	
	@InjectMocks
	private AvioKompanijaService avioService;
	
	@Before
	public void setUp()
	{
		kompanije.add(kompanija1);
		kompanije.add(kompanija2);
	}
	
	@Test
	public void findByIdSuccess()
	{
		when(avioRepo.findById(1l)).thenReturn(kompanijaOpt);
		AvioKompanija kompanijaM = avioService.findById(1l);
		assertEquals(kompanijaM, kompanijaOpt.get());
		verify(avioRepo, times(1)).findById(1l);
		verifyNoMoreInteractions(avioRepo);
	}
	
//	@Test
//	public void findAllSuccess()
//	{
//		when(avioRepo.findAll()).thenReturn(kompanije);
//		List<AvioKompanijaDTO> kompanijeM = avioService.getAvioCompanies();
//		assertEquals(kompanije, kompanijeM);
//		verify(avioRepo, times(1)).findAll();
//		verifyNoMoreInteractions(avioRepo);
//	}
	
	@Test
	public void saveOneSuccess()
	{
		when(avioRepo.save(kompanija1)).thenReturn(kompanija1);
		AvioKompanija kompanijaM = avioService.saveOne(kompanija1);
		assertNotNull(kompanijaM);
		verify(avioRepo, times(1)).save(kompanija1);
		verifyNoMoreInteractions(avioRepo);
	}
	
//	@Test
//	public void updateOneSuccess()
//	{
//		when(avioRepo.getOne(1l)).thenReturn(kompanija1);
//		when(avioRepo.save(kompanija1)).thenReturn(kompanija1);
//		AvioKompanijaDTO kompanijaM = avioService.updateOne(1l, avioConv.convertToDTO(kompanija1));
//		assertNotNull(kompanijaM);
//		verify(avioRepo, times(1)).getOne(1l);
//		verify(avioRepo, times(1)).save(kompanija1);
//		verifyNoMoreInteractions(avioRepo);
//	}
	
//	@Test
//	public void updateOneFailed()
//	{
//		when(avioRepo.getOne(1l)).thenReturn(null);
//		avioService.updateOne(1l, avioConv.convertToDTO(kompanija1));
//		verify(avioRepo, times(1)).getOne(1l);
//		verifyNoMoreInteractions(avioRepo);
//	}
	
	@Test
	public void deleteOneSuccess()
	{
		doNothing().when(avioRepo).deleteById(1l);
		boolean res = avioService.deleteOne(1l);
		assertEquals(true, res);
		verify(avioRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(avioRepo);
	}
	
	@Test
	public void getSrednjaOcenaAviokompanijeSuccess()
	{
		when(avioRepo.findAverageRating(1l)).thenReturn(srednjaOcena);
		Float avg = avioService.getSrednjaOcenaAvioKompanije(1l);
		assertEquals(srednjaOcena, avg);
		verify(avioRepo, times(1));
		verifyNoMoreInteractions(avioRepo);
	}
	
	
	
	
}
