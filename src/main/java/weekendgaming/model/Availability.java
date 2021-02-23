package weekendgaming.model;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name", "availableFrom", "availableFrom" }) })
public class Availability
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private OffsetDateTime availableFrom;
	private OffsetDateTime availableTill;
}
