package com.acboot.aviocompany.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LetDTO 
{
	private long idLeta;
	private long brojLeta;
	private LocalDateTime vremePoletanja;
	private LocalDateTime vremeSletanja;
	private int duzinaPutovanja; 
	private int brojPresedanja;
	private float prosecnaOcena;
	private String tipPuta;
	private int brojOsoba;
	private int brojMesta;
	private float ukupanPrihod;
	private float cenaKarte;
	
    private AvioKompanijaDTO aviokompanija;
    private DestinacijaDTO destinacijaPoletanja;
    private DestinacijaDTO destinacijaSletanja;
	private List<DestinacijaDTO> destinacijePresedanja;
    private List<PrtljagDTO> tipoviPrtljagaPoLetu;
	private List<KlasaDTO> klaseKojeLetSadrzi;
	private List<DodatnaUslugaDTO> dodatneUslugeKojeLetSadrzi;
}
