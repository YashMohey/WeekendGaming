package weekendgaming.service;

import java.util.List;

import weekendgaming.dto.AvailabilityDetailsRequestDTO;
import weekendgaming.model.Availability;

public interface AvailabilityService
{
	public void addAvailability(AvailabilityDetailsRequestDTO availabilityDetails);

	public List<Availability> getFutureAvailability();

}
