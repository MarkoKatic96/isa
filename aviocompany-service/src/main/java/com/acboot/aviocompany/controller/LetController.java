package com.acboot.aviocompany.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acboot.aviocompany.dto.LetDTO;
import com.acboot.aviocompany.service.LetService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/flight")
@Api(tags = "")
public class LetController
{
	@Autowired
	private LetService letService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<LetDTO> getLet(@PathVariable Long id)
	{
		System.out.println("getLet()");
		
		LetDTO LetDto = letService.findById(id);
		
		return (LetDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<LetDTO>(LetDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<LetDTO>> getAllLetovi()
	{
		System.out.println("getAllLetovi()");
		
		List<LetDTO> listDto = letService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<LetDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<LetDTO> addLet(@RequestBody LetDTO dto)
	{
		System.out.println("addLet()");
		
		return (letService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<LetDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<LetDTO> updateLet(@PathVariable("id") Long id, @RequestBody LetDTO dto)
	{
		System.out.println("updateLet()");
		
		LetDTO avio = letService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<LetDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteLet(@PathVariable("id") Long id)
	{
		System.out.println("deleteLet()");
		
		return (!letService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}
	
	
	
	
	  /////////////////////////////////
	 /////////////////////////////////
	/////////////////////////////////
	
	
	
	
	
	/* (Y)
	 * Pretraga po vremenu poletanja
	 */
	@GetMapping("/searchbydate/{time1}/{time2}")
	public ResponseEntity<List<LetDTO>> searchLetoviPoVremenu(@PathVariable("time1") String time1, @PathVariable("time2") String time2)
	{
		System.out.println("searchLetoviPoVremenu()");
		
		DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
		
		String vreme1 = LocalDateTime.parse(time1).format(format);
		String vreme2 = LocalDateTime.parse(time2).format(format);
		List<LetDTO> letovi = letService.searchLetoviPoVremenu(LocalDateTime.parse(vreme1), LocalDateTime.parse(vreme2));
	
		return (letovi.isEmpty()) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<List<LetDTO>>(letovi, HttpStatus.OK);
	}
	
	
//	/* (Y)
//	 * Trazimo prosecnu ocenu za jedan let
//	 * Prosledjujemo id leta
//	 */
//	@GetMapping("/getavgrating/{id}")
//	@ApiOperation(value = "Get average rating for one flight.", notes = "Returns average.", httpMethod = "GET")
//	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
//						   @ApiResponse(code = 404, message = "NOT_FOUND")
//	})
//	public ResponseEntity<Float> getAverageRating(@PathVariable("id") Long id)
//	{
//		Float avg = service.getAvgRating(id);
//		
//		return(avg != null) ? new ResponseEntity<Float>(avg, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	}
//	
//	
//	/*
//	 * Pretraga letova po destinaciji poletanja i sletanja (preko id-jeva)
//	 */
//	@GetMapping("/getbydest/{fromDest}/{toDest}")
//	@ApiOperation(value = "Get flights by destination.", httpMethod = "GET")
//	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
//						   @ApiResponse(code = 400, message = "BAD_REQUEST")
//	})
//	public ResponseEntity<List<FlightDTO>> getFlightsByDestination(@PathVariable("fromDest") Long takeOffDestination, @PathVariable("toDest") Long landingDestination)
//	{
////		Destination fromDest = new Destination();
////		fromDest.setName(takeOffDestination);
////		Destination toDest = new Destination();
////		toDest.setName(landingDestination);
//		
//		List<FlightDTO> flights = service.getFlightsByDestination(takeOffDestination, landingDestination);
//		
//		return(!flights.isEmpty()) ? new ResponseEntity<List<FlightDTO>>(flights, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
//	}
//	
//	/*
//	 * Pretraga letova po tipu leta
//	 */
//	@GetMapping("/getbytype/{type}")
//	@ApiOperation(value = "Get flights by type.", httpMethod = "GET")
//	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
//						   @ApiResponse(code = 400, message = "BAD_REQUEST")
//	})
//	public ResponseEntity<List<FlightDTO>> getFlightsByType(@PathVariable("type") String type)
//	{		
//		List<FlightDTO> flights = service.getFlightsByType(type);
//		
//		return(!flights.isEmpty()) ? new ResponseEntity<List<FlightDTO>>(flights, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
//	}
//	
//	/*
//	 * Pretraga letova po broju preostalih karata (broju osoba) -> npr ako korisnik hoce da rezervise za jos {broj} ljudi
//	 */
//	@GetMapping("/getbyticketnum/{number}")
//	@ApiOperation(value = "Get flights by tickets left.", httpMethod = "GET")
//	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
//						   @ApiResponse(code = 400, message = "BAD_REQUEST")
//	})
//	public ResponseEntity<List<FlightDTO>> getFlightsByTicketNumber(@PathVariable("number") String number)
//	{		
//		Integer num = Integer.parseInt(number);
//		List<FlightDTO> flights = service.getFlightsByTicketNumber(num);
//		
//		return(!flights.isEmpty()) ? new ResponseEntity<List<FlightDTO>>(flights, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
//	}
//	
//	/*
//	 * Pretraga letova po klasi koji podrzava, odredjuje se na osnovu karata koje se rezervisu za konkretan let
//	 */
//	@GetMapping("/getbyclass/{klasa}")
//	@ApiOperation(value = "Get flights by type.", httpMethod = "GET")
//	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
//						   @ApiResponse(code = 400, message = "BAD_REQUEST")
//	})
//	public ResponseEntity<List<FlightDTO>> getFlightsByClass(@PathVariable("klasa") String klasa)
//	{		
//		List<FlightDTO> flights = service.getFlightsByClass(klasa);
//		
//		return(!flights.isEmpty()) ? new ResponseEntity<List<FlightDTO>>(flights, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
//	}
//	
	
	
	
	
	
	
	
	
	
	
	

}