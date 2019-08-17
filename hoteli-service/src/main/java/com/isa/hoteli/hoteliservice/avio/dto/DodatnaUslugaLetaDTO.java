package com.isa.hoteli.hoteliservice.avio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DodatnaUslugaLetaDTO 
{
	private long idDodatneUsluge;
	private String naziv;
	
	
//	@ManyToMany(mappedBy = "dodatne_usluge_koje_let_sadrzi")
//	private List<Let> letovi;
	
}
