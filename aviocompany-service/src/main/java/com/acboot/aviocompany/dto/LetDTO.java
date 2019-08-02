package com.acboot.aviocompany.dto;

import java.util.Date;
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
	private Date vremePoletanja;
	private Date vremeSletanja;
	private int duzinaPutovanja; 
	private int brojPresedanja;
	private float prosecnaOcena;
	private String tipPuta;
	private short brojOsoba;
	private float ukupanPrihod;
	
    private AvioKompanijaDTO aviokompanija;
    private DestinacijaDTO destinacijaPoletanja;
    private DestinacijaDTO destinacijaSletanja;
	private List<DestinacijaDTO> destinacijePresedanja;
    private List<PrtljagDTO> tipoviPrtljagaPoLetu;
	private List<KlasaDTO> klaseKojeLetSadrzi;
	private List<DodatnaUslugaDTO> dodatneUslugeKojeLetSadrzi;
}