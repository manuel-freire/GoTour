package es.ucm.fdi.iw.gotour.control;

import java.io.File;


import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.iw.gotour.LocalData;
import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.TourOfertado;
import es.ucm.fdi.iw.gotour.model.User;
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
	
    @GetMapping(value="/{id}")
    @Transactional
	public String tourOfertado(@PathVariable long id, Model model, HttpSession session) {
        TourOfertado tour = entityManager.find(TourOfertado.class, id);
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
        ((User)session.getAttribute("u")).getId());
        List<Tour> instancias =  new ArrayList<>(tour.getInstancias());
        List<String> etiquetas = entityManager.createNamedQuery("TourOfertado.getEtiquetas")
                .setParameter("id", id)
                .getResultList();
        long id_guia = tour.getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
                .setParameter("user_id", id_guia)
                .getResultList();

        
        Map<Long, Boolean> valorados = new HashMap<Long, Boolean>();
        for(Tour t: tour.getInstancias()){
            for(Review r: t.getReviews()){
                if(u.getReviewsHechas().contains(r)){
                    valorados.put(t.getId(), true);
                    log.info("EL VALOR DE REVISADO ES {}" ,valorados.get(tour.getId()));
                }
            }
        
        }
        model.addAttribute("valorados", valorados);
        model.addAttribute("tour",tour);
        model.addAttribute("etiquetas",etiquetas);
        model.addAttribute("idiomas",idiomas);
        session.setAttribute("u", u);
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
	public String review(@PathVariable long id, Model model, HttpSession session) {
        Tour t = entityManager.find(Tour.class, id);
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
        ((User)session.getAttribute("u")).getId());
        long id_guia = t.getDatos().getGuia().getId();
        List<String> idiomas = entityManager.createNamedQuery("User.haslanguajes")
        .setParameter("user_id", id_guia)
        .getResultList();
        model.addAttribute("tour",t);
        model.addAttribute("idiomas",idiomas);
		return "review";
	}


	@PostMapping("/{id}/inscribirse")
    @Transactional
	public String inscribirse(@PathVariable("id") long id,Model model,@RequestParam int turistas,HttpSession session) {
        Tour t = entityManager.find(Tour.class, id); // mejor que PreparedQueries que sólo buscan por ID
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
            ((User)session.getAttribute("u")).getId());
        model.addAttribute("asistentes", turistas);
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
        t.addTurista(u, turistas);
        session.setAttribute("u", u);
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
        return "index";
    }

    @GetMapping("/{id}/reviewUser")
    @Transactional
    public String valorarUser(@PathVariable("id") long id, Model model, HttpSession session){
        Tour t = entityManager.find(Tour.class, id); // mejor que PreparedQueries que sólo buscan por ID
        model.addAttribute("tour", t);
        User guia = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
            ((User)session.getAttribute("u")).getId());
        Map<Long, Boolean> valoradosu = new HashMap<Long, Boolean>();
        for(User u: t.getTuristas()){
            for(Review r: guia.getReviewsHechas()){
                if(r.getDestinatario() == u && r.getTourValorado() == t){  
                    valoradosu.put(u.getId(), true);
                }
            }
        }
        model.addAttribute("valoradosu", valoradosu);
        return "reviewUsuarios";
    }
    @PostMapping("/{id}/valorarUser/{iduser}")
    @Transactional
    public String puntuar(@PathVariable("id") long id, @PathVariable("iduser") long iduser,Model model, @RequestParam int valoracion, @RequestParam String textoReview, HttpSession session){
        Tour t = entityManager.find(Tour.class, id);
        User u = entityManager.find(User.class,      // IMPORTANTE: tiene que ser el de la BD, no vale el de la sesión
            ((User)session.getAttribute("u")).getId());
        User objetivo = entityManager.find(User.class, iduser);
        Review r = new Review();
        log.info("SE PROCEDE A CREAR LA REVIEW");
        r.setCreador(u);
        r.setDestinatario(objetivo);
        r.setPuntuacion(valoracion);
        r.setTexto(textoReview);
        r.setTourValorado(t);
        log.info("La review actual es {}", r);
        entityManager.persist(r);
        entityManager.flush();
        model.addAttribute("tour", t);
        return "redirect:/tour/"+ id + "/reviewUser";
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

    
    @GetMapping("/{id}/crearInstancia")
    @Transactional
    public String crearInstancia(@PathVariable("id") long id, Model model, HttpSession session)
    {
        TourOfertado tour = entityManager.find(TourOfertado.class, id);
        model.addAttribute("tour", tour);
        model.addAttribute("inicial", false);
        return "crearInstancia";
    }

}
