package com.acboot.aviocompany.dto;

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
	private short ocena;
	private boolean brzaRezervacija;
	private float popust;

	private LetDTO let;
	
	private LocalDateTime vremeRezervisanja;
	private KorisnikDTO korisnik;
}
