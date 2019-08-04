package com.acboot.aviocompany.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PretragaDTO
{
	private LocalDateTime time1;
	private LocalDateTime time2;
	private Long takeOffDestination;
	private Long landingDestination;
	private String type;
	private Integer number; //broj preostalih mesta
	private List<KlasaDTO> klase; //pretraga po klasama
}
