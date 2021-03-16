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

/*
@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	
	private String nombre;
	private String apellidos;
	
	
	@OneToMany
	private List<Tour> toursofrecidos=new ArrayList<>();

    @ManyToMany
	private List<Tour> tourscontratados=new ArrayList<>();

	@ManyToMany
	private List<Review> reviews=new ArrayList<>();

	@Override
	public String toString() {
		return "";
	}		
}
*/