package com.acboot.aviocompany.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvioKompanijaDTO
{
	private long idAvioKompanije;
	private String naziv;
	private String adresa;
	private String opis;
	
    private List<DestinacijaDTO> destinacijeNaKojimaPosluje;
}