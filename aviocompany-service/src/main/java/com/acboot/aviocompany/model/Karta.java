package com.acboot.aviocompany.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Karta
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idKarte;
	
	@NotNull
	private float cena;
	
	@NotNull
	private short ocena;
	
	@NotNull
	private boolean brzaRezervacija;
	
	@NotNull
	private float popust;
	
	@ManyToOne
    @JoinColumn(name="id_leta", nullable=false)
    private Let let;
	
}
