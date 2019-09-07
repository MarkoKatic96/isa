package com.isa.hoteli.hoteliservice.integration;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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



@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelIntegrationTest {

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
	public void getHotelsSuccess() throws Exception {		
		mockMvc.perform(get("/hotel/test/all")).andExpect(status().isOk())/*.andExpect(jsonPath("$", hasSize(3)))*/;
	}
	
	@Test
	public void getHotelInfoSuccess() throws Exception {		
		mockMvc.perform(get("/hotel/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getHotelByIdSuccess() throws Exception {		
		mockMvc.perform(get("/hotel/1")).andExpect(status().isOk());
	}
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testCreateHotel() throws Exception {
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		String s = objectMapper.writeValueAsString(hotel);
		this.mockMvc.perform(post("/hotel/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}*/
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testDeleteHotel() throws Exception {
		mockMvc.perform(delete("/hotel/1")).andExpect(status().isOk());
	}*/
}
