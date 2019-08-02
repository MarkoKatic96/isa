package com.acboot.aviocompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DodatnaUslugaDTO 
{
	private long idDodatneUsluge;
	private String naziv;
	
	
//	@ManyToMany(mappedBy = "dodatne_usluge_koje_let_sadrzi")
//	private List<Let> letovi;
	
}
