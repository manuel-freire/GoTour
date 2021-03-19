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

    @NotNull
	@Size(max=244)
	private String nombre;

	@NotNull
	@Size(max=244)
	private String apellidos;
	
	
	@NotNull
	private String email;
	private String rol;

	@NotNull
	@Size(max=244)
	private String password;
    private int num_tarjeta;
	private String caducidad_tarjeta;

	@NotNull
	private int num_telefono;

	@NotNull
	@Size(max=100)
	private String pregunta_seguridad;

	@NotNull
	@Size(max=100)
	private String respuesta_seguridad;

	@ManyToMany(targetEntity=Tour.class, mappedBy="tour")
	private List<Tour> tour_ofrecidos=new ArrayList<>();

	@ManyToMany(targetEntity=Tour.class, mappedBy="tour")
	private List<Tour> tours_asistidos=new ArrayList<>(); 

	@ManyToMany(targetEntity=Review.class, mappedBy="review")
	private List<Review> reviews_hechas=new ArrayList<>();
	
	@ManyToMany(targetEntity=Review.class, mappedBy="review")
	private List<Review> reviews_recibidas=new ArrayList<>();
	
	private List<String> idiomas_hablados=new ArrayList<>();
	

	@Override
	public String toString() {
		return "";
	}		
}
