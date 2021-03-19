package es.ucm.fdi.iw.gotour.model;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	
	private String nombre;
	private String apellidos;

	
	@OneToMany
	@JoinColumn(name="guia_id")
	private List<Tour> toursofrecidos=new ArrayList<>();

    @ManyToMany
	private List<Tour> tourscontratados=new ArrayList<>();

	@Override
	public String toString() {
		return "";
	}	
}

