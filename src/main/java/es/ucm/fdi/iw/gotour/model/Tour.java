package es.ucm.fdi.iw.gotour.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name="AllTours", query="Select t from Tour t"),
	@NamedQuery(name="ToursBySearch", query="Select t from Tour t where t.Datos.Pais=:paisParam and "+
																		"t.Datos.Ciudad=:ciudadParam and "+
																		"t.Datos.Lugar=:lugarParam and "+
																		"(t.FechaIni<:fechaIniParam or "+
																		"t.FechaIni=:fechaIniParam) and "+
																		"(t.FechaFin>:fechaFinParam or "+
																		"t.FechaFin=:fechaFinParam)"),
	@NamedQuery(name="Tour.getTour",
		query="SELECT u FROM Tour u "
				+ "WHERE u.Id = :id "),
	@NamedQuery(name="Tour.getFirstTour",
		query="SELECT u FROM Tour u "
			+ "WHERE u.Datos.Guia.Id = :guia_id "
			+ "ORDER BY u.FechaIni DESC"),
	@NamedQuery(name="Tour.getToursByUser",
		query="SELECT u FROM Tour u "
			+ "WHERE u.Datos.Guia.Id = :guia_id")
})

public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	 @NotNull
	 @ManyToOne (targetEntity=TourOfertado.class)
	 private TourOfertado Datos;

	 @NotNull
	private LocalDateTime FechaIni;

	@NotNull
	private LocalDateTime FechaFin;

	@NotNull
	private int ActTuristas;
	
	@ManyToMany (mappedBy="ToursAsistidos")
	private List<User>  Turistas = new ArrayList<>();
    
	@OneToMany (mappedBy="TourValorado")
	private List<Review>  Reviews = new ArrayList<>();


	public void addTurista(User u,int numero){
		if(Datos.getMaxTuristas() >= (ActTuristas+numero)){
			Turistas.add(u);
			ActTuristas+=numero;
			u.addTour(this);
		}
	}
	public String getFechaIniString(){
		return FechaIni.toString().replace('T', ' ');
	}
	public String getFechaFinString(){
		return FechaFin.toString().replace('T', ' ');
	}
	@Override
	public String toString() {
		return "";
	}
	public String getFirstTourInfo(){
		String[] parts = FechaIni.toString().split("-");
		return "Guía desde "+parts[1]+"/"+parts[0];
	}
}
