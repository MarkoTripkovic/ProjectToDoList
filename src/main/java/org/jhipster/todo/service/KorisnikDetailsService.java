package org.jhipster.todo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jhipster.todo.domain.Korisnik;
import org.jhipster.todo.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component("korisnikDetailsService")
public class KorisnikDetailsService implements UserDetailsService {
	@Inject
	KorisnikRepository korisnikRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikRepository.findByUsername(username);
		 /*return korisnik.map(user -> {
	            
	            List<GrantedAuthority> garantedAuthorities = user.getAuthorities().stream()
	                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
	                .collect(Collectors.toList());
	            return new org.springframework.security.core.userdetails.User(user.getUsername(),
	                user.getPassword(),
	                grantedAuthorities);
	            GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(user.getRola().getName());
	        }).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the " +
	        "database"));*/
		String password = passwordEncoder.encode(korisnik.getPassword());
		GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(korisnik.getRola().getName());
		return new CustomUserDetails(korisnik.getUsername(),password,grantedAuthorities);

}
}