package com.acboot.aviocompany.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acboot.aviocompany.dto.DestinacijaDTO;
import com.acboot.aviocompany.dto.DodatnaUslugaDTO;
import com.acboot.aviocompany.dto.KlasaDTO;
import com.acboot.aviocompany.dto.LetDTO;
import com.acboot.aviocompany.dto.PrtljagDTO;
import com.acboot.aviocompany.model.Destinacija;
import com.acboot.aviocompany.model.DodatnaUsluga;
import com.acboot.aviocompany.model.Klasa;
import com.acboot.aviocompany.model.Let;
import com.acboot.aviocompany.model.Prtljag;

@Component
public class LetConverter
{
	@Autowired
	private AvioKompanijaConverter avioConv;
	
	@Autowired
	private DestinacijaConverter destConv;
	
	@Autowired
	private PrtljagConverter prtljagConv;
	
	@Autowired
	private KlasaConverter klasaConv;
	
	@Autowired
	private DodatnaUslugaConverter uslugaConv;
	
	
	public LetDTO convertToDTO(Let model)
	{
		LetDTO dto = new LetDTO();
		
		dto.setIdLeta(model.getIdLeta());
		dto.setBrojLeta(model.getBrojLeta());
		dto.setVremePoletanja(model.getVremePoletanja());
		dto.setVremeSletanja(model.getVremeSletanja());
		dto.setDuzinaPutovanja(model.getDuzinaPutovanja());
		dto.setBrojPresedanja(model.getBrojPresedanja());
		dto.setProsecnaOcena(model.getProsecnaOcena());
		dto.setTipPuta(model.getTipPuta());
		dto.setBrojOsoba(model.getBrojOsoba());
		dto.setBrojMesta(model.getBrojMesta());
		dto.setUkupanPrihod(model.getUkupanPrihod());
		
		dto.setAviokompanija(avioConv.convertToDTO(model.getAviokompanija()));
		dto.setDestinacijaPoletanja(destConv.convertToDTO(model.getDestinacijaPoletanja()));
		dto.setDestinacijaSletanja(destConv.convertToDTO(model.getDestinacijaSletanja()));
		
		//DESTINACIJE PRESEDANJA
		List<DestinacijaDTO> destList = new ArrayList<DestinacijaDTO>();
		
		for(Destinacija dest : model.getDestinacijePresedanja())
		{
			destList.add(destConv.convertToDTO(dest));
		}
		
		dto.setDestinacijePresedanja(destList);
		
		
		//TIPOVI PRTLJAGA
		List<PrtljagDTO> prtljagList = new ArrayList<PrtljagDTO>();
		
		for(Prtljag prtljag : model.getTipoviPrtljagaPoLetu())
		{
			prtljagList.add(prtljagConv.convertToDTO(prtljag));
		}
		
		dto.setTipoviPrtljagaPoLetu(prtljagList);
		
		
		//KLASE LETA
		List<KlasaDTO> klasaList = new ArrayList<KlasaDTO>();
		
		for(Klasa klasa : model.getKlaseKojeLetSadrzi())
		{
			klasaList.add(klasaConv.convertToDTO(klasa));
		}
		
		dto.setKlaseKojeLetSadrzi(klasaList);
		
		
		//DODATNE USLUGE LETA
		List<DodatnaUslugaDTO> uslugaList = new ArrayList<DodatnaUslugaDTO>();
		
		for(DodatnaUsluga usluga : model.getDodatneUslugeKojeLetSadrzi())
		{
			uslugaList.add(uslugaConv.convertToDTO(usluga));
		}
		
		dto.setDodatneUslugeKojeLetSadrzi(uslugaList);
		
		return dto;
	}
	
	
	
	public Let convertFromDTO(LetDTO dto)
	{
		Let model = new Let();
		
		model.setIdLeta(dto.getIdLeta());
		model.setBrojLeta(dto.getBrojLeta());
		model.setVremePoletanja(dto.getVremePoletanja());
		model.setVremeSletanja(dto.getVremeSletanja());
		model.setDuzinaPutovanja(dto.getDuzinaPutovanja());
		model.setBrojPresedanja(dto.getBrojPresedanja());
		model.setProsecnaOcena(dto.getProsecnaOcena());
		model.setTipPuta(dto.getTipPuta());
		model.setBrojOsoba(dto.getBrojOsoba());
		model.setBrojMesta(dto.getBrojMesta());
		model.setUkupanPrihod(dto.getUkupanPrihod());
		
		model.setAviokompanija(avioConv.convertFromDTO(dto.getAviokompanija()));
		model.setDestinacijaPoletanja(destConv.convertFromDTO(dto.getDestinacijaPoletanja()));
		model.setDestinacijaSletanja(destConv.convertFromDTO(dto.getDestinacijaSletanja()));
		
		//DESTINACIJE PRESEDANJA
		List<Destinacija> destList = new ArrayList<Destinacija>();
		
		for(DestinacijaDTO dest : dto.getDestinacijePresedanja())
		{
			destList.add(destConv.convertFromDTO(dest));
		}
		
		model.setDestinacijePresedanja(destList);
		
		
//		//TIPOVI PRTLJAGA
//		List<Prtljag> prtljagList = new ArrayList<Prtljag>();
//		
//		for(PrtljagDTO prtljag : dto.getTipoviPrtljagaPoLetu())
//		{
//			prtljagList.add(prtljagConv.convertFromDTO(prtljag));
//		}
//		
//		model.setTipoviPrtljagaPoLetu(prtljagList);
		
		
		//KLASE LETA
		List<Klasa> klasaList = new ArrayList<Klasa>();
		
		for(KlasaDTO klasa : dto.getKlaseKojeLetSadrzi())
		{
			klasaList.add(klasaConv.convertFromDTO(klasa));
		}
		
		model.setKlaseKojeLetSadrzi(klasaList);
		
		
		//DODATNE USLUGE LETA
		List<DodatnaUsluga> uslugaList = new ArrayList<DodatnaUsluga>();
		
		for(DodatnaUslugaDTO usluga : dto.getDodatneUslugeKojeLetSadrzi())
		{
			uslugaList.add(uslugaConv.convertFromDTO(usluga));
		}
		
		model.setDodatneUslugeKojeLetSadrzi(uslugaList);
		
		return model;
		
	}

}
