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
import java.util.List;
import es.ucm.fdi.iw.gotour.LocalData;
import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.User;

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

	@PostMapping("/{id}/inscribirse")
    @Transactional
	public String inscribirse(@PathVariable("id") long id,Model model,@RequestParam int turistas,HttpSession session) {
        Tour t= entityManager.createNamedQuery("Tour.getTour", Tour.class)
        .setParameter("id", id)
        .getSingleResult();
        User u=(User)session.getAttribute("u");
        t.addTurista(u, turistas);
        //u.addTour(t);
		/*entityManager.persist(t);
		entityManager.flush();*/
		return tourOfertado(id,model);
	}

}
