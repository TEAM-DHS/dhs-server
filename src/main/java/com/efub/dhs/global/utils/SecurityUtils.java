package com.efub.dhs.global.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getName() == null) {
			throw new AuthenticationException("There is no authentication information for the current user.") {
			};
		}
		return authentication.getName();
	}
}
