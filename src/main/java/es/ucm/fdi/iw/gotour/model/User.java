package es.ucm.fdi.iw.gotour.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.ArrayList;

import lombok.Data;


public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	

	private long id;
	private String nombre;
	private String apellidos;
	private String rol;
	private String password;
    private int num_tarjeta;
	private String caducidad_tarjeta;
	private int num_telefono;
	private String pregunta_seguridad;
	private String respuesta_seguridad;
	private List<Tour> tour_ofrecidos=new ArrayList<>();
	private List<Tour> tours_asistidos=new ArrayList<>(); 
	private List<Review> reviews_hechas=new ArrayList<>();
	private List<Review> reviews_recibidas=new ArrayList<>();
	private List<String> idiomas_hablados=new ArrayList<>();
	
	
	

	@Override
	public String toString() {
		return "";
	}		
}
