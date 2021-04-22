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

import es.ucm.fdi.iw.gotour.LocalData;
import es.ucm.fdi.iw.gotour.model.User;

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

		model.addAttribute("users", entityManager.createQuery(
				"SELECT u FROM User u").getResultList());
		
		return "admin/index";

	}

	
	@PostMapping("/toggleuser")
	@Transactional
	public String delUser(Model model,	@RequestParam long id) {
		User target = entityManager.find(User.class, id);
		if (target.getEnabled() == 1) {
			// remove profile photo
			File f = localData.getFile("user", ""+id);
			if (f.exists()) {
				f.delete();
			}
			// disable user
			target.setEnabled((byte)0); 
		} else {
			// enable user
			target.setEnabled((byte)1);
		}
		return index(model);
	}


	@GetMapping("/users")
	public String users(Model model) {
		return "admin/users";
	}

	@GetMapping("/notificaciones")
	public String notificaciones(Model model) {
		return "admin/notificaciones";
	}

	@GetMapping("/reportes-usuarios")
	public String reportes(Model model) {
		return "admin/reportes-usuarios";
	}

	@GetMapping("/reporte-usuario")
	public String reporteUsuario(Model model) {
		return "admin/reporte-usuario";
	}

	@GetMapping("/configuracion")
	public String reporteUsuario(Model model) {
		return "admin/configuracion";
	}




	

}
