package weekendgaming.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weekendgaming.dto.AvailabilityDetailsRequestDTO;
import weekendgaming.model.Availability;
import weekendgaming.repository.AvailabilityRepository;
import weekendgaming.service.AvailabilityService;

@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService
{

	private static final long  NO_OF_DAYS = 2;
	
	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Override
	public void addAvailability(AvailabilityDetailsRequestDTO availabilityDetails)
	{
		System.out.println(availabilityDetails);

		OffsetDateTime start = OffsetDateTime.parse(availabilityDetails.getAvailableFrom());
		OffsetDateTime end = OffsetDateTime.parse(availabilityDetails.getAvailableTill());

		Availability availability = new Availability();

		availability.setName(availabilityDetails.getName());
		availability.setAvailableFrom(start);
		availability.setAvailableTill(end);

		availabilityRepository.save(availability);
	}

	@Override
	public List<Availability> getFutureAvailability()
	{
		OffsetDateTime currTime = OffsetDateTime.now();

		System.out.println("Current Time = " + currTime.toString());
		System.out.println("Current Time + 2 Days = " + currTime.plusDays(2));

		List<Availability> validAvailability = new ArrayList<>();

		List<Availability> allAvailability = availabilityRepository.findAll();
		System.out.println("allAvailability : " + allAvailability.toString());

		for (Availability availability : availabilityRepository.findAll())
		{
			System.out.println("User : " + availability.getName() + " | Time : " + availability.getAvailableFrom() + " - " + availability.getAvailableTill());
			System.out.println("Current Time = " + currTime.plusDays(2));

			// Delete Old Dates and Clear Database
			if (availability.getAvailableTill().isBefore(currTime))
				availabilityRepository.delete(availability);
			// Check if anyone starts playing in the next 2 days
			else if (availability.getAvailableFrom().isBefore(currTime.plusDays(NO_OF_DAYS)))
				validAvailability.add(availability);
		}

		System.out.println("Valid Availability = " + validAvailability.toString());
		return validAvailability;
	}

}
