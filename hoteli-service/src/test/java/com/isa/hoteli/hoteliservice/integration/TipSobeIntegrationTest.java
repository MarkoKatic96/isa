package com.isa.hoteli.hoteliservice.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.TipSobe;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TipSobeIntegrationTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getTypesSuccess() throws Exception {		
		mockMvc.perform(get("/tip_sobe/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getTypesFromHotelSuccess() throws Exception {		
		mockMvc.perform(get("/tip_sobe/all/2")).andExpect(status().isOk())/*.andExpect(jsonPath("$", hasSize(1)))*/;
	}
	
	@Test
	public void getTypeByIdSuccess() throws Exception {		
		mockMvc.perform(get("/tip_sobe/5")).andExpect(status().isOk());
	}
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testDeleteType() throws Exception {
		mockMvc.perform(delete("/tip_sobe/8")).andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateType() throws Exception {
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		TipSobe ts = new TipSobe(7l, "b", hotel);
		String s = objectMapper.writeValueAsString(ts);
		this.mockMvc.perform(put("/tip_sobe/7").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateType() throws Exception {
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		TipSobe ts = new TipSobe(7l, "b", hotel);
		String s = objectMapper.writeValueAsString(ts);
		this.mockMvc.perform(post("/tip_sobe/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}*/

}
