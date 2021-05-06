package es.ucm.fdi.iw.gotour.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.JoinColumn;
import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotNull;
/**
 * A message that users can send each other.
 *
 * @author mfreire
 */
@Entity
/*@NamedQueries({
	@NamedQuery(name="Mensaje.countUnread",
	query="SELECT COUNT(m) FROM MENSAJE m "
			+ "WHERE m.recipient.id = :userId AND m.dateRead = null")
})*/
@Data
public class Mensaje implements Transferable<Mensaje.Transfer> {
	
	private static Logger log = LogManager.getLogger(Mensaje.class);	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private User sender;

	private String text;
	
	private LocalDateTime dateSent;
	private LocalDateTime dateRead;

	@NotNull
	@ManyToOne (targetEntity=Tour.class)
	private Tour tour;
	/**
	 * Objeto para persistir a/de JSON
	 * @author mfreire
	 */
    @Getter
    @AllArgsConstructor
	public static class Transfer {
		private String from;
		private String sent;
		private String received;
		private String text;
		private Tour tour;
		long id;
		public Transfer(Mensaje m) {
			this.from = m.getSender().getUsername();
			this.sent = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(m.getDateSent());
			this.received = m.getDateRead() == null ?
					null : DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(m.getDateRead());
			this.text = m.getText();
			this.tour = m.getTour();
			this.id = m.getId();
		}
	}

	@Override
	public Transfer toTransfer() {
		return new Transfer(sender.getUsername(),
			DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateSent),
			dateRead == null ? null : DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateRead),
			text, tour, id
        );
    }
}
