package weekendgaming.service;

import java.util.List;

import weekendgaming.dto.AvailabilityDetailsRequestDTO;
import weekendgaming.model.Availability;

public interface AvailabilityService
{
	public List<Availability> getFutureAvailability();

	public List<Availability> getFutureAvailability(int noOfDays);

	public void addAvailability(AvailabilityDetailsRequestDTO availabilityDetails);

	public void deleteAvailability(long id);
}
