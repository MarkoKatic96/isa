package com.acboot.aviocompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.acboot.aviocompany.model.Korisnik;


@Service
public class MailService
{

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
	 */
	@Autowired
	private Environment env;

	/*
	 * Anotacija za oznacavanje asinhronog zadatka
	 * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
	 */
	@Async
	public void sendNotificaitionAsync(Korisnik korisnik, Korisnik prijatelj) throws MailException, InterruptedException {

		//Simulacija duze aktivnosti da bi se uocila razlika
		//Thread.sleep(10000);
		System.out.println("Slanje emaila...");
		

		//String link = "https://localhost:8443/#/registracija/aktiviranjeNaloga/" + korisnik.getId(); 
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(prijatelj.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Zahtev za prijateljstvo");
		mail.setText("Pozdrav " + prijatelj.getIme() + ",\n\nImate novi zahtev za prijateljstvo. "
				+ "Zahtev je poslao " + korisnik.getIme() + " " + korisnik.getPrezime() + ", \nEmail adresa: " + korisnik.getEmail() + ".\n\n"
				+ "Zahtev mozete prihvatiti ili odbiti.\n\n"
				+ "Link za aktivaciju: http://localhost:3000/reservation/30"
				+ " \n\n\n Vas MegaTravel");
		javaMailSender.send(mail);
		
		//stilizovano
//		mail.setText("<h1>Pozdrav <strong>" + agent.getIme() + "</strong></h1>,\n\nUspešno ste se registrovali na MegaTravel servis."
//				+ "Od ovoga trenutka možete aktivno postavljati oglase za izdavanje smeštaja na <a href=\"www.megatravel.com\"> našem sajtu</a>.\n"
//				+ "Parametri za prijavu: \nEmail:" + agent.getEmail() + "\nLozinka: " + agent.getLozinka());
//		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
}