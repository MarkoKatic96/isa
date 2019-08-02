package com.acboot.aviocompany.model;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Let
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idLeta;
	
	@NotNull
	private long brojLeta;
	
	@NotNull
	private Date vremePoletanja;
	
	@NotNull
	private Date vremeSletanja;
	
	@NotNull
	private int duzinaPutovanja; //u km
	
	@NotNull
	private int brojPresedanja;
	
	private float prosecnaOcena;
	
	@NotNull
	private String tipPuta;
	
	@NotNull
	private short brojOsoba;
	
	private float ukupanPrihod;
	
	
	
	/*
	 * AVIOKOMPANIJA
	 */
	@ManyToOne
    @JoinColumn(name="avio_kompanija", nullable=false)
    private AvioKompanija aviokompanija;
	
	
	/*
	 * DESTINACIJE
	 */
	@ManyToOne
    @JoinColumn(name="destinacija_poletanja", nullable=false)
    private Destinacija destinacijaPoletanja;
	
	@ManyToOne
    @JoinColumn(name="destinacija_sletanja", nullable=false)
    private Destinacija destinacijaSletanja;
	
	@ManyToMany
	@JoinTable(
	  name = "destinacije_presedanja", 
	  joinColumns = @JoinColumn(name = "let_id"), 
	  inverseJoinColumns = @JoinColumn(name = "destinacija_id"))
	private List<Destinacija> destinacijePresedanja;
	
	/*
	 * PRTLJAG
	 */
	@ManyToMany
	@JoinTable(
	  name = "tipovi_prtljaga_po_letu", 
	  joinColumns = @JoinColumn(name = "let_id"), 
	  inverseJoinColumns = @JoinColumn(name = "prtljag_id"))
	private List<Prtljag> tipoviPrtljagaPoLetu;
	
	/*
	 * KLASE
	 */
	@ManyToMany
	@JoinTable(
	  name = "klase_leta", 
	  joinColumns = @JoinColumn(name = "let_id"), 
	  inverseJoinColumns = @JoinColumn(name = "klasa_id"))
	private List<Klasa> klaseKojeLetSadrzi;
	
	
	/*
	 * DODATNE USLUGE
	 */
	@ManyToMany
	@JoinTable(
	  name = "dodatne_usluge_leta", 
	  joinColumns = @JoinColumn(name = "let_id"), 
	  inverseJoinColumns = @JoinColumn(name = "dodatna_usluga_id"))
	private List<DodatnaUsluga> dodatneUslugeKojeLetSadrzi;
	
	
	/*
	 * KARTE
	 */
	@OneToMany(mappedBy="let")
    private List<Karta> karteLeta;
	
}
