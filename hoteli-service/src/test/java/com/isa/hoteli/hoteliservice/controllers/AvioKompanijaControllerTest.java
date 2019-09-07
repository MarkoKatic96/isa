package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.controller.AvioKompanijaController;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(AvioKompanijaController.class)
public class AvioKompanijaControllerTest 
{
	private String route = "/aviocompany";
	
	private List<AvioKompanija> kompanije = new ArrayList<>();
	private AvioKompanija kompanija1 = new AvioKompanija(1l, "a", "a", "a");
	private AvioKompanija kompanija2 = new AvioKompanija(2l, "b", "b", "b");
	private Float srednjaOcena;
	
	private LocalDate datumOd = LocalDate.now();
	private LocalDate datumDo = LocalDate.now();
	
	private List<Destinacija> letovi = new ArrayList<>();
	
	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	
	//DTO
	private List<AvioKompanijaDTO> kompanijeDto = new ArrayList<>();
	private AvioKompanijaDTO kompanija1Dto = new AvioKompanijaDTO(1l, "a", "a", "a");
	private AvioKompanijaDTO kompanija2Dto = new AvioKompanijaDTO(2l, "b", "b", "b");
	
	//treba mokovati svakakva cudesa iz kontrolera da ne baca failed to load app context
	
	@MockBean
	private AvioKompanijaService avioService;
	
	@MockBean
	private KorisnikService korisnikService;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@MockBean
	private KorisnikRepository korisnikRepo;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		kompanije.add(kompanija1);
		kompanije.add(kompanija2);
		kompanijeDto.add(kompanija1Dto);
		kompanijeDto.add(kompanija2Dto);
		request.addParameter("parameterName", "someValue");
		
	}
	
	
	@Test
	public void getAvioKompanijaSuccess() throws Exception
	{
		when(avioService.findById(1l)).thenReturn(kompanija1);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().isOk()).
				andReturn();
		AvioKompanija dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), AvioKompanija.class);
		assertEquals(dto, kompanija1);
		verify(avioService, times(1)).findById(1l);
		verifyNoMoreInteractions(avioService);
	}
	
	@Test
	public void getAvioKompanijaFailed() throws Exception
	{
		when(avioService.findById(1l)).thenReturn(null);
		this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(avioService, times(1)).findById(1l);
		verifyNoMoreInteractions(avioService);
	}
	
}
