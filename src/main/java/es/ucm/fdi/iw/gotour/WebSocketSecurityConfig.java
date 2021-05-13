package es.ucm.fdi.iw.gotour;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import javax.servlet.http.HttpSession;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.Tour;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Similar a SecurityConfig, pero para websockets con STOMP.
 * 
 * @author mfreire
 */
@Configuration
public class WebSocketSecurityConfig
      extends AbstractSecurityWebSocketMessageBrokerConfigurer { 
    @Autowired 
	private EntityManager entityManager;
	private static final Logger log = LogManager.getLogger(WebSocketSecurityConfig.class);
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages, HttpSession session) {
        messages
            .simpSubscribeDestMatchers("/topic/admin")	// solo admines pueden suscribirse a este topic
            	.hasRole(User.Role.ADMIN.toString())
            .anyMessage().authenticated(); 				// todo tiene que proceder de sesiones autenticadas

       /* List<Tour> tours = entityManager.createNamedQuery("AllTours").getResultList(); 
        for (Tour t : tours) {
            long topic_id = t.getId();
            List<User> turistas = t.turistas;
            long user_id = ((User)session.getAttribute("u")).getId();
            boolean encontrado = false;
            if(t.getDatos().getGuia().getId() == user_id){
                encontrado = true;
            }else{
                for (User u : turistas) {
                    if(u.getId() == user_id){
                        encontrado = true;
                        break;
                    }
                }
            }
            if(encontrado){
                messages
                .simpSubscribeDestMatchers("/topic/"+topic_id+"/tour");
            }
           
        }*/
       
    }
}