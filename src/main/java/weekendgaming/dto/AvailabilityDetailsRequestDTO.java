package weekendgaming.dto;

import lombok.Data;

@Data
public class AvailabilityDetailsRequestDTO
{

	private String name;

	private String availableFrom;

	private String availableTill;

	private String timeZone;
}
