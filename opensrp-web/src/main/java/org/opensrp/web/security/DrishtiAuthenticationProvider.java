package org.opensrp.web.security;

import static java.text.MessageFormat.format;

import java.util.List;

import org.apache.activemq.transport.stomp.Stomp.Headers.Connect;
import org.json.JSONException;
import org.opensrp.api.domain.User;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;

@Component
public class DrishtiAuthenticationProvider implements AuthenticationProvider {
	
	private static Logger logger = LoggerFactory.getLogger(DrishtiAuthenticationProvider.class.toString());
	
	public static final String INVALID_CREDENTIALS = "The username or password you entered is incorrect. Please enter the correct credentials.";
	
	public static final String USER_NOT_FOUND = "The user details not found. Please try again later.";
	
	public static final String USER_NOT_ACTIVATED = "The user has been registered but not activated. Please contact your local administrator.";
	
	public static final String INTERNAL_ERROR = "Failed to authenticate user due to internal server error. Please try again later.";
	
	//private AllOpenSRPUsers allOpenSRPUsers;
	private PasswordEncoder passwordEncoder;
	
	private OpenmrsUserService openmrsUserService;
	
	@Autowired
	public DrishtiAuthenticationProvider(OpenmrsUserService openmrsUserService,
	    @Qualifier("shaPasswordEncoder") PasswordEncoder passwordEncoder) {
		this.openmrsUserService = openmrsUserService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = getDrishtiUser(authentication, authentication.getName());
		// get user after authentication
		if (user == null) {
			throw new BadCredentialsException(USER_NOT_FOUND);
		}
		
		if (user.getVoided() != null && user.getVoided()) {
			throw new BadCredentialsException(USER_NOT_ACTIVATED);
		}
		
		return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(),
		        getRolesAsAuthorities(user));
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)
		        && authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private List<SimpleGrantedAuthority> getRolesAsAuthorities(User user) {
		return Lambda.convert(user.getRoles(), new Converter<String, SimpleGrantedAuthority>() {
			
			@Override
			public SimpleGrantedAuthority convert(String role) {
				return new SimpleGrantedAuthority("ROLE_OPENMRS");
			}
		});
	}
	
	public User getDrishtiUser(Authentication authentication, String username) {
		User user = null;
		
		checkIsAuthenticated(authentication.getName(), authentication.getCredentials().toString());
		
		try {
			boolean response = openmrsUserService.deleteSession(authentication.getName(),
			    authentication.getCredentials().toString());
			user = openmrsUserService.getUser(username);
			if (!response) {
				logger.error(format("{0}. Exception: {1}", INTERNAL_ERROR, "Unable to clear session"));
				
			}
		}
		catch (Exception e) {
			logger.error(format("{0}. Exception: {1}", INTERNAL_ERROR, e));
			e.printStackTrace();
			throw new BadCredentialsException(INTERNAL_ERROR);
		}
		return user;
	}
	
	private void checkIsAuthenticated(String username, String password) {
		try {
			Boolean isAuthenticated = openmrsUserService.authenticate(username, password);
			if (isAuthenticated == null) {
				throw new BadCredentialsException(INTERNAL_ERROR);
			} else if (!isAuthenticated) {
				throw new BadCredentialsException(INVALID_CREDENTIALS);
			}
		}
		catch (Exception e) {
			logger.error(format("{0}. Exception: {1}", INTERNAL_ERROR, e));
			e.printStackTrace();
			throw new BadCredentialsException(INTERNAL_ERROR);
		}
	}
}
