package com.acboot.aviocompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrtljagDTO
{
	private long idPrtljaga;
	private float tezina;
	private String opis;
}
