package com.acboot.aviocompany.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DodatnaUsluga
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDodatneUsluge;
	
	@NotNull
	private String naziv;
	
	
	@ManyToMany(mappedBy = "dodatneUslugeKojeLetSadrzi")
	private List<Let> letovi;
	
}
