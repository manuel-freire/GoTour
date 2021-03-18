package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import java.time;

//@Entity
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String lugar;
	//private String descripcion;
	
	@NotNull
	@ManyToOne
reviewsUser	private User guia;

	@NotNull
	private LocalDate fecha_review;

	@NotNull
	
	private String descripcion;
	
	@NotNull
	@ManyToOne(mappedBy="")
	private User guia;

    private User user;

	
	
	@NotNull
	@ManyToOne(mappedBy="")
	private User guia;

}
