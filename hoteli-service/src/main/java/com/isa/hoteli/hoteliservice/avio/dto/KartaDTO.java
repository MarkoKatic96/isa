package com.isa.hoteli.hoteliservice.avio.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KartaDTO 
{
	
	private long idKarte;
	private float cena;
	private int ocena;
	private boolean brzaRezervacija;
	private float popust;
	private String brojPasosa;

	private LetDTO let;
	
	private LocalDateTime vremeRezervisanja;
	private KorisnikDTO korisnik;
	private KorisnikDTO korisnikKojiSaljePozivnicu;
}