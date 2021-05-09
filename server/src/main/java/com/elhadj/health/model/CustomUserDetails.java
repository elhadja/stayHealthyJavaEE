package com.elhadj.health.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
	public long getUserId();
}
