package org.jhipster.todo.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private String username;
	private String password;
	private GrantedAuthority grantedAuthorities;
	
	

	public CustomUserDetails(String username, String password,GrantedAuthority grantedAuthorities) {
		super();
		this.username = username;
		this.password = password;
		this.grantedAuthorities = grantedAuthorities;
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public GrantedAuthority getGrantedAuthorities() {
		return grantedAuthorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
