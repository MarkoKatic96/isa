package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.controller.LetController;
import com.isa.hoteli.hoteliservice.avio.dto.LetDTO;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.avio.service.LetService;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(LetController.class)
public class LetControllerTest 
{
	private String route = "/flight";
	
	private List<Let> letovi = new ArrayList<>();
	private Let let1 = new Let(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null, null);
	private Let let2 = new Let(2l, 2l, LocalDateTime.now(), LocalDateTime.now(), 20, 20, 1, "b", 20, 20, 20, 20, null, null, null, null, null, null, null, null);
	private Korisnik korisnik = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_AVIO_KOMPANIJE, 1l, true, "a", null, null, null, null, null, null);
	private Boolean created;
	
	private MockHttpServletRequest request = new MockHttpServletRequest();

	//DTO
	private List<LetDTO> letoviDto = new ArrayList<>();
	private LetDTO let1Dto = new LetDTO(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null);
	private LetDTO let2Dto = new LetDTO(2l, 2l, LocalDateTime.now(), LocalDateTime.now(), 20, 20, 1, "b", 20, 20, 20, 20, null, null, null, null, null, null, null);

	@MockBean
	private LetService letService;
	
	@MockBean
	private KorisnikService korisnikService;
	
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
		letovi.add(let2);
		
		letoviDto.add(let1Dto);
		letoviDto.add(let2Dto);
		request.addParameter("parameterName", "someValue");
		
	}
	
	
	@Test
	public void getLetSuccess() throws Exception
	{
		when(letService.findById(1l)).thenReturn(let1Dto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().isOk()).
				andReturn();
		LetDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), LetDTO.class);
		assertEquals(dto, let1Dto);
		verify(letService, times(1)).findById(1l);
		verifyNoMoreInteractions(letService);
	}
	
	@Test
	public void getLetFailed() throws Exception
	{
		when(letService.findById(1l)).thenReturn(null);
		this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(letService, times(1)).findById(1l);
		verifyNoMoreInteractions(letService);
	}
	
	@Test
	public void getAllLetoviSuccess() throws Exception
	{
		when(letService.findAll()).thenReturn(letoviDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getall")).
				andExpect(status().isOk()).
				andReturn();
		List<LetDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<LetDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Let dest : letovi)
		{
			letoviDto.add(new LetDTO(dest));
		}
		assertThat(letoviDto.equals(dtos));
		verify(letService, times(1)).findAll();
		verifyNoMoreInteractions(letService);
	}
	
	//ispravi metodu, nije standardna da vraca objekat vec vraca true/false
//	@Test
//	public void addLetSuccess() throws Exception
//	{
//		when(letService.saveOne(let1Dto)).thenReturn(created);
//		String s = objectMapper.writeValueAsString(let1Dto);
//		MvcResult result = this.mockMvc.
//				perform(post(this.route + "/add/").content(s).contentType(MediaType.APPLICATION_JSON)).
//				andExpect(status().isCreated()).
//				andReturn();
//		String dto = objectMapper.readValue(result
//				.getResponse()
//				.getContentAsString(), String.class);
//		assertTrue(dto, created);
//		verify(letService, times(1)).saveOne(Mockito.any(Let.class));
//		verifyNoMoreInteractions(letService);
//	}
	
	@Test
	public void addLetFailed() throws Exception
	{
		String s = objectMapper.writeValueAsString(let1Dto);
		this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(letService, times(1)).saveOne(Mockito.any(LetDTO.class));
		verifyNoMoreInteractions(letService);
	}
	
	@Test
	public void updateLetSuccess() throws Exception
	{
		when(letService.updateOne(Mockito.any(Long.class), Mockito.any(LetDTO.class))).thenReturn(let1Dto);
		String s = objectMapper.writeValueAsString(let1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(let1Dto));
		verify(letService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(LetDTO.class));
		verifyNoMoreInteractions(letService);
		
	}
	
	@Test
	public void updateLetFailed() throws Exception
	{
		when(letService.updateOne(Mockito.any(Long.class), Mockito.any(LetDTO.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(let1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		assertThat(result.equals(let1Dto));
		verify(letService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(LetDTO.class));
		verifyNoMoreInteractions(letService);
		
	}
	
	@Test
	public void deleteLetSuccess() throws Exception
	{
		when(letService.deleteOne(1l)).thenReturn(true);
		MvcResult result = this.mockMvc.perform(delete(this.route + "/delete/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(true));
		verify(letService, times(1)).deleteOne(1l);
		verifyNoMoreInteractions(letService);
	}

}