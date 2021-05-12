package es.ucm.fdi.iw.gotour.control;

import java.io.File;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PathVariable;

import es.ucm.fdi.iw.gotour.LocalData;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.Tour;
import es.ucm.fdi.iw.gotour.model.TourOfertado;

/**
 * Admin-only controller
 * @author mfreire
 */
@Controller()
@RequestMapping("admin")
public class AdminController {
	
	private static final Logger log = LogManager.getLogger(AdminController.class);
	
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private LocalData localData;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/")
	public String index(Model model) {

        model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));
		List<User> users = entityManager.createNamedQuery("AllUsers").getResultList();        
        // dumps them via log
		
        log.info("Dumping table {}", "user");
        for (Object o : users) {
            log.info("\t{}", o);
        }

		List<Tour> tours = entityManager.createNamedQuery("AllTours").getResultList();
		List<TourOfertado> toursOfertados = entityManager.createNamedQuery("AllToursOfer").getResultList();
		
		int tourNumber=0;
		if(tours!=null){
			tourNumber=tours.size();
			toursOfertados.size();

		}
		int tourOfertadoNumber=0;

		if(toursOfertados!=null){
			
			tourOfertadoNumber=toursOfertados.size();
			

		}


		int userNumber= users.size();
        // adds them to model
        model.addAttribute("userNumber", userNumber);
		model.addAttribute("users", users);
		model.addAttribute("tourNumber", tourNumber);
		model.addAttribute("classActiveAdmin","active");		
        return "admin/index";

	}





	
	@PostMapping("/toggleuser")
	@Transactional
	public String delUser(Model model, @RequestParam long id) {
		User target = entityManager.find(User.class, id);
		if (target.getEnabled() == 1) {
			// remove profile photo
			File f = localData.getFile("user", ""+id);
			if (f.exists()) {
				f.delete();
			}
			// disable user
			target.setEnabled(0); 
		} else {
			// enable user
			target.setEnabled(1);
		}
		return index(model);
	}


	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));
		List<User> users = entityManager.createNamedQuery("AllUsers").getResultList();        
        // dumps them via log
        log.info("Dumping table {}", "user");
        for (Object o : users) {
            log.info("\t{}", o);
        }        
        // adds them to model
        model.addAttribute("users", users);
		model.addAttribute("classActiveUsers","active");
		return "admin/users";
	}


	@PostMapping("/user-busqueda")
	public String userBusqueda(Model model, List<User> users) {
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));
		model.addAttribute("debug", env.getProperty("es.ucm.fdi.debug"));
		      
        // dumps them via log
        log.info("Dumping table {}", "user");
        for (Object o : users) {
            log.info("\t{}", o);
        }        
        // adds them to model
        model.addAttribute("users", users);
		model.addAttribute("classActiveUsers","active");
		return "admin/user-busqueda";
	}

	@GetMapping("/notificaciones")
	public String notificaciones(Model model) {
		model.addAttribute("classActiveNotificaciones","active");
		return "admin/notificaciones";
	}

	@GetMapping("/reportes-usuarios")
	public String reportes(Model model) {
		model.addAttribute("classActiveReportes","active");
		return "admin/reportes-usuarios";
	}

	@GetMapping("/reporte-usuario")
	public String reporteUsuario(Model model) {
		model.addAttribute("classActiveReportes","active");
		return "admin/reporte-usuario";
	}

	 @GetMapping("/configuracion")
	public String configuracion(Model model) {
		model.addAttribute("classActiveSettings","active");
	return "admin/configuracion";


	}


	@PostMapping("/userSearch")
    public String searchUsers(Model model,@RequestParam String username
                                        ,@RequestParam String email
                                        ){

											System.out.println("Hola");
        List<User> busqueda = entityManager.createNamedQuery("UsersByAdminSearch")
            .setParameter("usernameParam", username)
            .setParameter("emailParam", email).getResultList();      	
        model.addAttribute("busqueda", busqueda);
		
        return userBusqueda(model,busqueda);
    }


	


	


		
		







	



}

