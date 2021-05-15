package com.elhadj.health.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.istack.NotNull;

public class CustomUserDetailsImpl implements CustomUserDetails {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private long userId;
	private Collection<? extends GrantedAuthority> grantedAuthorities;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	

	public CustomUserDetailsImpl(@NotNull String username, String password, long userId,
			@NotNull Collection<? extends GrantedAuthority> grantedAuthorities) {
		super();
		this.username = username;
		this.password = password;
		this.userId = userId;
		this.grantedAuthorities = grantedAuthorities;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.isEnabled = true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public long getUserId() {
		return userId;
	}
}
