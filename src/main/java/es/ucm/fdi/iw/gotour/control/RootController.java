package es.ucm.fdi.iw.gotour.control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import es.ucm.fdi.iw.gotour.model.*;

/**
 * Landing-page controller
 * 
 * @author mfreire
 */
@Controller
public class RootController {

	private static final Logger log = LogManager.getLogger(RootController.class);
    @Autowired 
	private EntityManager entityManager;
    private AuthenticationManager authman;

	@GetMapping("/chat")
	public String chat(Model model, HttpServletRequest request) {
		return "chat";
	}
	
	@GetMapping("/error")
	public String error(Model model) {
		return "error";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

    @GetMapping("/registro")
	public String registro(Model model) {
		return "registro";
	}

    @PostMapping("/")
    public String searchTours(Model model,@RequestParam String pais
                                        ,@RequestParam String ciudad
                                        ,@RequestParam String lugar
                                        ,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaini
                                        ,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechafin){
        List<Tour> busqueda = entityManager.createNamedQuery("ToursBySearch")
            .setParameter("paisParam", pais)
            .setParameter("ciudadParam", ciudad)
            .setParameter("lugarParam", lugar)
            .setParameter("fechaIniParam", fechaini)
            .setParameter("fechaFinParam", fechafin).getResultList();      	
        model.addAttribute("busqueda", busqueda);	
        return index(model);
    }

    @GetMapping(value="tour/{id}")
	public String tourOfertado(@PathVariable long id, Model model) {
        Tour u = entityManager.createNamedQuery("Tour.getTour", Tour.class)
		        .setParameter("id", id)
		        .getSingleResult();
        List<String> etiquetas = entityManager.createNamedQuery("TourOfertado.getEtiquetas")
                .setParameter("id", id)
                .getResultList();
        long id_guia = u.getDatos().getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
                .setParameter("user_id", id_guia)
                .getResultList();
        model.addAttribute("tour",u);
        model.addAttribute("etiquetas",etiquetas);
        model.addAttribute("idiomas",idiomas);
		return "tour";
	}

    @GetMapping("/")            // <-- en qué URL se expone, y por qué métodos (GET)        
    public String index(        // <-- da igual, sólo para desarrolladores
            Model model)        // <-- hay muchos, muchos parámetros opcionales
    {
        List<Tour> tours = entityManager.createNamedQuery("AllTours").getResultList();        
        // dumps them via log
        log.info("Dumping table {}", "tour");
        for (Object o : tours) {
            log.info("\t{}", o);
        }        
        // adds them to model
        model.addAttribute("tours", tours);			
        return "index";
    }


    // Un getMapping por vista que queramos en el proyecto. Y un template por vista

    @GetMapping("/guia")
    public String guia(Model model)
    {
        model.addAttribute("user", "");   
        return "guia";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session)
    {
        User requester = (User)session.getAttribute("u");
        User user = (User)entityManager.createNamedQuery("User.byId")
            .setParameter("id", requester.getId()).getSingleResult();
        user.setTourofrecidos(entityManager.createNamedQuery("User.getToursOfrecidos")
            .setParameter("guia_id", requester.getId()).getResultList());
        user.setReviewsrecibidas(entityManager.createNamedQuery("User.getReviewsRecibidas")
            .setParameter("dest", requester.getId()).getResultList());
        /*for (int i=0; i<user.getTourofrecidos().size(); i++){
            int datos_id = (int)entityManager.createNamedQuery("Tour.byId")
                .setParameter("id", user.getTourofrecidos().get(i).getId()).getSingleResult();
            user.getTourofrecidos().get(i).setDatos((TourOfertado)entityManager.createNamedQuery("TourOfrecido.byId")
                .setParameter("id", datos_id).getSingleResult());
        }*/
        model.addAttribute("user", user); 
        model.addAttribute("propio", true);
        return "perfil";
    }

    /*
    @GetMapping("/perfil/{username}")
    public String perfil(@PathVariable String username, Model model)
    {  
        Date membresia = new Date();
        int id_usuario = 1;
        int id_sesion = 1;
        String[] tourD1 = {"Visita guiada del Coliseo", "Roma", "4 huecos disponibles"};
        String[] tourD2 = {"Madrid del siglo XIX", "Madrid", "9 huecos disponibles"};
        String[] tourC1 = {"Paseo cultural por Santillana del Mar", "Santillana del Mar"};
        String[] resenya1 = {"3 estrellas", "pedro", "Visita guiada del Coliseo", "Muy buen tour. El guía es muy agradable."};
        model.addAttribute("nombre", "Juan");
        model.addAttribute("apellidos", "Shánchez Pisuerga");
        if (id_usuario == id_sesion) model.addAttribute("propio", true);
        else model.addAttribute("propio", false);
        model.addAttribute("membresia", membresia);
        model.addAttribute("rol", "guia");
        model.addAttribute("tourD1",tourD1);
        model.addAttribute("tourD2",tourD2);
        model.addAttribute("tourC1",tourC1);
        model.addAttribute("resenya1",resenya1);
        model.addAttribute("estrellas", 3);
    }*/

    @GetMapping("/busqueda")
    public String busqueda(Model model)
    {  
        return "busqueda";
    }

    @GetMapping("/tour")
    public String tour(Model model)
    {  
        return "tour";
    }

    @GetMapping("/administracion")
    public String administracion(Model model)
    {  
        return "administracion";
    }

    @GetMapping("/leeme")
    public String leeme(Model model)
    {  
        return "leeme";
    }
}