package es.ucm.fdi.iw.gotour.model;
import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

//@Entity
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String texto;
	private int puntuacion;
	
	@NotNull
	@ManyToMany(mappedBy="id")
	private Usuario creador;

	@NotNull
	@ManyToMany(mappedBy="id")
	private Usuario destinatario;

	@NotNull
	@OneToMany(mappedBy="id")
	private Tour tour_valorado;

	@NotNull
	private LocalDate fecha_review;

}
