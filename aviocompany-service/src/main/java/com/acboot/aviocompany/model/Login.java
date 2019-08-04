package com.acboot.aviocompany.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //ne treba ovo ovako, treba dto login objekat da se kreira, ima dosta da se menja
	
	private String email;
	private String lozinka;
}
