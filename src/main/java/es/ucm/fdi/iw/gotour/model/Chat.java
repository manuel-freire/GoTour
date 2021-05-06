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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Data
public class Chat {
	private static final Logger log = LogManager.getLogger(Chat.class);

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

    @ManyToMany 
	private List<User> Users = new ArrayList<>();

	@OneToMany (targetEntity=Mensaje.class)
	private List<Mensaje> Mensajes = new ArrayList<>();

    @Override
	public String toString() {
		return "Chat #" + Id;
	}
}
