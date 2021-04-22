package es.ucm.fdi.iw.gotour.control;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.TourOfertado;
import es.ucm.fdi.iw.gotour.model.User;

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
    public String searchTours(Model model, HttpSession session,@RequestParam String pais
                                        ,@RequestParam String ciudad
                                        ,@RequestParam String lugar
                                        ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaini
                                        ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechafin){
        List<Tour> busqueda = entityManager.createNamedQuery("ToursBySearch")
            .setParameter("paisParam", pais)
            .setParameter("ciudadParam", ciudad)
            .setParameter("lugarParam", lugar)
            .setParameter("fechaIniParam", fechaini)
            .setParameter("fechaFinParam", fechafin).getResultList();      	
        model.addAttribute("busqueda", busqueda);	
        return index(model, session);
    }

    

    @GetMapping(value="tour/{id}/apuntarse")
    public String apuntarse(@PathVariable("id") Long id, Model model, HttpSession session){
        Tour t = entityManager.createNamedQuery("Tour.getTour", Tour.class)
                .setParameter("id", id)
                .getSingleResult();
                List<String> etiquetas = entityManager.createNamedQuery("TourOfertado.getEtiquetas")
                .setParameter("id", id)
                .getResultList();
        long id_guia = t.getDatos().getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
                .setParameter("user_id", id_guia)
                .getResultList();
        model.addAttribute("tour",t);
        model.addAttribute("etiquetas",etiquetas);
        model.addAttribute("idiomas",idiomas);

        return "apuntarse";
    }
    @PostMapping(value="tour/{id_tour}/apuntarse")
    @Transactional
    public String apuntado(@PathVariable("id_tour") Long id_tour, Model model, HttpSession session, @RequestParam int asistentes){
        Tour t = entityManager.createNamedQuery("Tour.getTour", Tour.class)
                .setParameter("id", id_tour)
                .getSingleResult();
        User u = (User)session.getAttribute("u");
        t.setActTuristas(t.getActTuristas() + asistentes);
        List<User> turistas = t.getTuristas();
        turistas.add(u);
        t.setTuristas(turistas);
        // entityManager.persist(u);
        entityManager.persist(t);
        entityManager.flush();
        return "tour";
    }

    @GetMapping("/")            // <-- en qué URL se expone, y por qué métodos (GET)        
    public String index(        // <-- da igual, sólo para desarrolladores
            Model model, HttpSession session)        // <-- hay muchos, muchos parámetros opcionales
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

    
	@PostMapping("/tour")
	@Transactional
    public String nuevoTour(@RequestParam String pais,
                            @RequestParam String ciudad,
                            @RequestParam String lugar,
                            @RequestParam String titulo,
                            @RequestParam String descripcion,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String fechaIni,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String fechaFin,
                            @RequestParam int maxTuristas,
                            @RequestParam double precio,
                            Model model, HttpSession session){

        TourOfertado tourO = new TourOfertado();
        Tour tour = new Tour();

        tourO.setPais(pais);
        tourO.setCiudad(ciudad);
        tourO.setLugar(lugar);
        tourO.setTitulo(titulo);
        tourO.setDescripcion(descripcion);
        tourO.setMaxTuristas(maxTuristas);
        tourO.setPrecio(precio);
        tourO.setDisponible(true);

        tour.setFechaIni(fechaIni);
        tour.setFechaFin(fechaFin);
        tour.setActTuristas(0);

        User guia = entityManager.find(User.class, ((User)session.getAttribute("u")).getId());
        guia.getTourOfertados().add(tour);
        tourO.setGuia(guia);
        tourO.getInstancias().add(tour);
        tour.setDatos(tourO);
        

        entityManager.persist(tourO);
        entityManager.flush();
        long idTour = tourO.getInstancias().get(0).getId();

        //return tourOfertado(idTour, model);
        return "redirect:/tour/" + idTour;
    }

	@GetMapping("/crearTour")
    public String crearTour(Model model, HttpSession session)
    {
        return "crearTour";
    }

    // Un getMapping por vista que queramos en el proyecto. Y un template por vista

    @GetMapping("/guia")
    public String guia(Model model)
    {
        model.addAttribute("user", "");   
        return "guia";
    }

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

    @GetMapping("/leeme")
    public String leeme(Model model, HttpSession session )
    {  
        return "leeme";
    }
}