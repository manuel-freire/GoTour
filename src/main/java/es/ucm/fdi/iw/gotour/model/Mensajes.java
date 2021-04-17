package es.ucm.fdi.iw.gotour.model;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Mensajes {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

    private String contenido;
    private Date Fecha;
}