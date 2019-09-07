package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.controller.AvioKompanijaController;
import com.isa.hoteli.hoteliservice.avio.controller.DestinacijaController;
import com.isa.hoteli.hoteliservice.avio.controller.KartaController;
import com.isa.hoteli.hoteliservice.avio.dto.KartaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.KartaService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(KartaController.class)
public class KartaControllerTest 
{
	private String route = "/ticket";
	
	private LocalDateTime vremeRez = LocalDateTime.now();
	
	private List<Karta> karte = new ArrayList<>();
	private Karta karta1 = new Karta(1l, 10, 5, false, 0, "a", null, vremeRez, null, null);
	private Karta karta2 = new Karta(2l, 20, 1, false, 0, "b", null, vremeRez, null, null);
	private Korisnik korisnik = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_AVIO_KOMPANIJE, 1l, true, "a", null, null, null, null, null, null);

	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	private Let let1 = new Let(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null, null);
	private List<Let> letovi = new ArrayList<>();
	
	//DTO
	private List<KartaDTO> karteDto = new ArrayList<>();
	private KartaDTO karta1Dto = new KartaDTO(1l, 10, 5, false, 0, "a", null, vremeRez, null, null);
	private KartaDTO karta2Dto = new KartaDTO(2l, 20, 1, false, 0, "b", null, vremeRez, null, null);

	@MockBean
	private KartaService kartaService;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		letovi.add(let1);
		karte.add(karta1);
		karte.add(karta2);
		
		karteDto.add(karta1Dto);
		karteDto.add(karta2Dto);
		request.addParameter("parameterName", "someValue");
		
	}
	
	
	@Test
	public void getKartaSuccess() throws Exception
	{
		when(kartaService.findById(1l)).thenReturn(karta1Dto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().isOk()).
				andReturn();
		KartaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), KartaDTO.class);
		assertEquals(dto, karta1Dto);
		verify(kartaService, times(1)).findById(1l);
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void getKartaFailed() throws Exception
	{
		when(kartaService.findById(1l)).thenReturn(null);
		this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(kartaService, times(1)).findById(1l);
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void getAllKarteSuccess() throws Exception
	{
		when(kartaService.findAll()).thenReturn(karteDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getall")).
				andExpect(status().isOk()).
				andReturn();
		List<KartaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<KartaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Karta dest : karte)
		{
			karteDto.add(new KartaDTO(dest));
		}
		assertThat(karteDto.equals(dtos));
		verify(kartaService, times(1)).findAll();
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void addKartaSuccess() throws Exception
	{
		when(kartaService.saveOne(Mockito.any(KartaDTO.class))).thenReturn(karta1Dto);
		String s = objectMapper.writeValueAsString(karta1Dto);
		MvcResult result = this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		KartaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), KartaDTO.class);
		assertEquals(dto, karta1Dto);
		verify(kartaService, times(1)).saveOne(Mockito.any(KartaDTO.class));
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void addKartaFailed() throws Exception
	{
		String s = objectMapper.writeValueAsString(karta1Dto);
		this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(kartaService, times(1)).saveOne(Mockito.any(KartaDTO.class));
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void updateKartaSuccess() throws Exception
	{
		when(kartaService.updateOne(Mockito.any(Long.class), Mockito.any(KartaDTO.class))).thenReturn(karta1Dto);
		String s = objectMapper.writeValueAsString(karta1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(karta1Dto));
		verify(kartaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(KartaDTO.class));
		verifyNoMoreInteractions(kartaService);
		
	}
	
	@Test
	public void updateKartaFailed() throws Exception
	{
		when(kartaService.updateOne(Mockito.any(Long.class), Mockito.any(KartaDTO.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(karta1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		assertThat(result.equals(karta1Dto));
		verify(kartaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(KartaDTO.class));
		verifyNoMoreInteractions(kartaService);
		
	}
	
	@Test
	public void deleteKartaSuccess() throws Exception
	{
		when(kartaService.deleteOne(1l)).thenReturn(true);
		MvcResult result = this.mockMvc.perform(delete(this.route + "/delete/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(true));
		verify(kartaService, times(1)).deleteOne(1l);
		verifyNoMoreInteractions(kartaService);
	}
	
	
}