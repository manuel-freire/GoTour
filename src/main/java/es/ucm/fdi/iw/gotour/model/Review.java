package es.ucm.fdi.iw.gotour.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	private String texto;
	private int Puntuacion;
	
	@NotNull
	@ManyToOne(targetEntity=User.class)
	private User Creador;

	@ManyToOne(targetEntity=User.class)
	private User Destinatario;

	@ManyToOne(targetEntity=Tour.class)
	private Tour TourValorado;

}
