package com.acboot.aviocompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinacijaDTO
{
	private long idDestinacije;
	private String naziv;
	private String informacije;
	
//    private AvioKompanijaDTO aviokompanija;
	
	
//	@OneToOne(mappedBy = "destinacijaPoletanja")
//    private Let poletanje;
//	
//	@OneToOne(mappedBy = "destinacijaSletanja")
//    private Let sletanje;
//	
//	@ManyToMany(mappedBy = "destinacijePresedanja")
//	private List<Let> letovi;
}
