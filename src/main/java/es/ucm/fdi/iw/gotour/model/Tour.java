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
	@NamedQuery(name="ToursBySearch", query="Select t from Tour t where t.datos.pais=:paisParam and "+
																		"t.datos.ciudad=:ciudadParam and "+
																		"t.datos.lugar=:lugarParam and "+
																		"t.fechaIni=:fechaIniParam and "+
																		"t.fechaFin=:fechaFinParam"),
	@NamedQuery(name="Tour.getTour",
		query="SELECT u FROM Tour u "
				+ "WHERE u.id = :id ")
})

public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	 @NotNull
	 @ManyToOne (targetEntity=TourOfertado.class)
	 private TourOfertado datos;

	 @NotNull
	private LocalDateTime fechaIni;

	@NotNull
	private LocalDateTime fechaFin;

	@NotNull
	private int actTuristas;
	
	@ManyToMany (mappedBy="toursasistidos")
	private List<User>  turistas = new ArrayList<>();
    
	@OneToMany (mappedBy="tourvalorado")
	private List<Review>  reviews = new ArrayList<>();

	public String getFechaIniString(){
		return fechaIni.toString().replace('T', ' ');
	}
	public String getFechaFinString(){
		return fechaFin.toString().replace('T', ' ');
	}
	@Override
	public String toString() {
		return "";
	}
}
