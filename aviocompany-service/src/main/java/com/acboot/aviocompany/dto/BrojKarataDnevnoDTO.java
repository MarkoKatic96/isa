package com.acboot.aviocompany.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrojKarataDnevnoDTO
{
	private LocalDate datum;
	private int brojKarata;
}
