package es.ucm.fdi.iw.gotour;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import es.ucm.fdi.iw.gotour.model.User;

/**
 * Called when a user is first authenticated (via login).
 * Called from SecurityConfig; see https://stackoverflow.com/a/53353324
 * 
 * Adds a "u" variable to the session when a user is first authenticated.
 * Important: the user is retrieved from the database, but is not refreshed at each request. 
 * You should refresh the user's information if anything important changes; for example, after
 * updating the user's profile.
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired 
    private HttpSession session;
    
    @Autowired
    private EntityManager entityManager;    
    
	private static Logger log = LogManager.getLogger(LoginSuccessHandler.class);
	
    /**
     * Called whenever a user authenticates correctly.
     */
    @Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	    String username = ((org.springframework.security.core.userdetails.User)
				authentication.getPrincipal()).getUsername();
	    
	    // add a 'u' session variable, accessible from thymeleaf via ${session.u}
	    log.info("Storing user info for {} in session {}", username, session.getId());
		User u = entityManager.createNamedQuery("User.byUsername", User.class)
		        .setParameter("username", username)
		        .getSingleResult();
		
		log.info("EL ID DEL USUARIO ES {}", u.getId());		
		List<String> idiomas= entityManager.createNamedQuery("User.haslanguajes")
			.setParameter("user_id", u.getId()).getResultList();
		log.info("LA LISTA DE IDIOMAS OBTENIDOS ES {}", idiomas);
		u.setIdiomasHablados(idiomas);		
		session.setAttribute("u", u);
		/*long unread = entityManager.createNamedQuery("Mensaje.countUnread", Long.class)
		        .setParameter("userId", u.getId())
		        .getSingleResult();	
		session.setAttribute("unread", unread);*/
		
		// add a 'ws' session variable
		session.setAttribute("ws", request.getRequestURL().toString()
				.replaceFirst("[^:]*", "ws")		// http[s]://... => ws://...
				.replaceFirst("/[^/]*$", "/ws"));	// .../foo		 => .../ws
		
		// redirects to 'admin' or 'user/{id}', depending on the user
		response.sendRedirect(u.hasRole(User.Role.ADMIN) ? 
				"admin/" :
				"user/" + u.getId() + "/perfil/");
				//"/");
	}
}
