package com.acboot.aviocompany.security;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.acboot.aviocompany.model.Korisnik;
import com.acboot.aviocompany.service.KorisnikService;

//@Service
public class UserDetails// implements UserDetailsService
{
//	
//	@Autowired
//	private KorisnikService korServ;
//
//	@Override
//	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
//			throws UsernameNotFoundException 
//	{
//		Korisnik korisnik = korServ.getUserByEmail(email);
//		
//		if(korisnik == null)
//		{
//			throw new UsernameNotFoundException("User '" + email + "' not found");
//		}
//		
//		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//		grantedAuthorities.add(new SimpleGrantedAuthority(korisnik.getRola().toString()));
//		
//		//novi ulogovani korisnik sa svojim autoritetima
//		return new User(korisnik.getEmail(), korisnik.getLozinka(), true, true, true, true, grantedAuthorities);
//		return null;
//		
//	}

}
