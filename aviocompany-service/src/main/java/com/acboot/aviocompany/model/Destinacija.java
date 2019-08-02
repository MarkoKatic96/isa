package com.acboot.aviocompany.model;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destinacija
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDestinacije;
	
	@NotNull
	private String naziv;
	
	@NotNull
	private String informacije;
	
	@ManyToMany(mappedBy = "klaseKojeLetSadrzi")
	private List<Let> destinacijeNaKojimaPosluje;
	
	
	@OneToMany(mappedBy = "destinacijaPoletanja")
    private List<Let> poletanje;
	
	@OneToMany(mappedBy = "destinacijaSletanja")
    private List<Let> sletanje;
	
	@ManyToMany(mappedBy = "destinacijePresedanja")
	private List<Let> letovi;
}
