package com.buddy.chat.filter;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.buddy.chat.service.UserService;
import com.buddy.chat.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserService userService;

	private static final Set<String> EXCLUDE_URL_PATTERNS = Stream.of("/api/v1/auth/", "/api/v1/verification/", "/public/")
			.collect(Collectors.toSet());

	private String resolveToken(@NonNull HttpServletRequest request) {
		String bearerToken = request.getHeader("authorization");

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			String accessToken = bearerToken.substring(7);
			if (accessToken == null)
				return null;
			return bearerToken.substring(7);
		}

		return null;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		if (ignore(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		final String accessToken = resolveToken(request);
		if (accessToken == null || accessToken.equals("null")) {
			SecurityContextHolder.clearContext();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return; // Stop further filter execution and return
		}
		
		try {
			String username = jwtUtil.extractUsername(accessToken);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userService.loadUserByUsername(username);

				if (jwtUtil.isTokenValid(accessToken)) { // If accessToken is valid (not expired, right username, ...)
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(token);
				} 
			}
			
		} catch (ExpiredJwtException e) {
			System.out.println("TOKEN IS EXPIRED!!!");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
			SecurityContextHolder.clearContext();
			return;
		}

		filterChain.doFilter(request, response);
	}

	private boolean ignore(String path) {
		return EXCLUDE_URL_PATTERNS.stream().anyMatch(path::startsWith);
	}

}