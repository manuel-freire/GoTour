package es.ucm.fdi.iw.gotour.control;

import java.io.File;


import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.ucm.fdi.iw.gotour.LocalData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.util.List;
import java.time.LocalDateTime;
import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.Mensaje;
import es.ucm.fdi.iw.gotour.model.Review;

/**
 * Admin-only controller
 * @author mfreire
 */
@Controller()
@RequestMapping("tour")
public class TourController {
	
	private static final Logger log = LogManager.getLogger(TourController.class);
	
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private LocalData localData;
	
	@Autowired
	private Environment env;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
    
    @GetMapping(value="/{id}")
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

    @GetMapping(value="/{id}/pago")
	public String pago(@PathVariable long id, Model model) {
        Tour t = entityManager.find(Tour.class, id);
        List<String> etiquetas = entityManager.createNamedQuery("TourOfertado.getEtiquetas")
        .setParameter("id", id)
        .getResultList();
        long id_guia = t.getDatos().getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
        .setParameter("user_id", id_guia)
        .getResultList();
        model.addAttribute("tour",t);
        model.addAttribute("idiomas",idiomas);
		return "pago";
	}
    @GetMapping(value="/{id}/review")
	public String review(@PathVariable long id, Model model) {
        Tour t = entityManager.find(Tour.class, id);
        List<String> etiquetas = entityManager.createNamedQuery("TourOfertado.getEtiquetas")
        .setParameter("id", id)
        .getResultList();
        long id_guia = t.getDatos().getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
        .setParameter("user_id", id_guia)
        .getResultList();
        model.addAttribute("tour",t);
        model.addAttribute("idiomas",idiomas);
		return "review";
	}

    @GetMapping("/{id}/chat")
	public String chat(@PathVariable long id, Model model, HttpServletRequest request, HttpSession session) {
        Tour t = entityManager.find(Tour.class, id);
        List<User> turistas = t.Turistas;
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
        model.addAttribute("tour_id",id);
        model.addAttribute("user_id",user_id);
		return encontrado ? "chat" : "/index";
	}
    
    @PostMapping("/{id}/msg")
	@ResponseBody
	@Transactional
	public String postMsg(@PathVariable long id, 
			@RequestBody JsonNode o, Model model, HttpSession session) 
		throws JsonProcessingException {
		
		String text = o.get("message").asText();
		Tour tour = entityManager.find(Tour.class, id);
        log.info("El tour con id {} es {}",id, tour.getId());
		User sender = entityManager.find(
				User.class, ((User)session.getAttribute("u")).getId());
        
		//model.addAttribute("user", u);
		
		// construye mensaje, lo guarda en BD
		Mensaje m = new Mensaje();
		//m.setRecipient(u);
		m.setSender(sender);
		m.setDateSent(LocalDateTime.now());
		m.setText(text);
        m.setTour(tour);
		entityManager.persist(m);
		entityManager.flush(); // to get Id before commit
		
		// construye json
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		rootNode.put("from", sender.getUsername());
		//rootNode.put("to", u.getUsername());
		rootNode.put("text", text);
		rootNode.put("id", m.getId());
        rootNode.put("id_sender", sender.getId());
		String json = mapper.writeValueAsString(rootNode);
		
		log.info("Sending a Mensaje to {} with contents '{}'", id, json);

		messagingTemplate.convertAndSend("/topic/1/tour", json);
		return "{\"result\": \"mensaje sent.\"}";
	}	
	

	@PostMapping("/{id}/inscribirse")
    @Transactional
	public String inscribirse(@PathVariable("id") long id,Model model,@RequestParam int turistas,HttpSession session) {
        Tour t = entityManager.find(Tour.class, id); // mejor que PreparedQueries que sólo buscan por ID
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
            ((User)session.getAttribute("u")).getId());
        model.addAttribute("asistentes", turistas);
        t.addTurista(u, turistas);
        tourOfertado(id, model);
		return "pago";
	}

    @PostMapping("/{id}/valorar")
    @Transactional
    public String valorar(@PathVariable("id") long id, Model model, @RequestParam int valoracion, @RequestParam String textoReview, HttpSession session){
        Tour t = entityManager.find(Tour.class, id); // mejor que PreparedQueries que sólo buscan por ID
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
            ((User)session.getAttribute("u")).getId());
        Review r = new Review();
        log.info("SE PROCEDE A CREAR LA REVIEW");
        r.setCreador(u);
        r.setDestinatario(t.getDatos().getGuia());
        r.setPuntuacion(valoracion);
        r.setTexto(textoReview);
        r.setTourValorado(t);
        log.info("La review actual es {}", r);
        entityManager.persist(r);
        entityManager.flush();
        tourOfertado(id, model);
        return "tour";
    }

    @PostMapping("/{id}/pagar")
    @Transactional
	public String pagar(@PathVariable("id") long id,Model model,@RequestParam int numTarjeta, @RequestParam String caducidadTarjeta,
                        @RequestParam int numSecreto,HttpSession session) {
                            //Aunque le pido los datos de tarjeta en el formulario realmente no hago nada con ellos
        Tour t = entityManager.find(Tour.class, id);
        User u = entityManager.find(User.class,
            ((User)session.getAttribute("u")).getId());
        if(numTarjeta!= u.getNumTarjeta()){
            u.setNumTarjeta(numTarjeta);
            u.setCaducidadTarjeta(caducidadTarjeta);
            u.setNumSecreto(numSecreto);
        }
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

}
