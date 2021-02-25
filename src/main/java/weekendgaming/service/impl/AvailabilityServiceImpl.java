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

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Override
	public void addAvailability(AvailabilityDetailsRequestDTO availabilityDetails)
	{
		System.out.println(availabilityDetails);

		OffsetDateTime start = OffsetDateTime.parse(availabilityDetails.getAvailableFrom());
		OffsetDateTime end = OffsetDateTime.parse(availabilityDetails.getAvailableTill());

		if (start.isAfter(end))
			return;

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

		List<Availability> validAvailability = new ArrayList<>();

		List<Availability> allAvailability = availabilityRepository.findAll();
		System.out.println("allAvailability : " + allAvailability.toString());

		for (Availability availability : availabilityRepository.findAll())
		{
			System.out.println("User : " + availability.getName() + " | Time : " + availability.getAvailableFrom() + " - " + availability.getAvailableTill());

			// Delete Old Dates and Clear Database
			if (availability.getAvailableTill().isBefore(currTime))
				availabilityRepository.delete(availability);
			// Check if anyone starts playing in the future
			else
				validAvailability.add(availability);
		}

		System.out.println("Valid Availability = " + validAvailability.toString());
		return validAvailability;
	}

	@Override
	public List<Availability> getFutureAvailability(int noOfDays)
	{
		OffsetDateTime currTime = OffsetDateTime.now();

		System.out.println("Current Time = " + currTime.toString());

		List<Availability> validAvailability = new ArrayList<>();

		List<Availability> allAvailability = availabilityRepository.findAll();
		System.out.println("allAvailability : " + allAvailability.toString());

		for (Availability availability : availabilityRepository.findAll())
		{
			System.out.println("User : " + availability.getName() + " | Time : " + availability.getAvailableFrom() + " - " + availability.getAvailableTill());

			// Delete Old Dates and Clear Database
			if (availability.getAvailableTill().isBefore(currTime))
				availabilityRepository.delete(availability);
			// Check if anyone starts playing in the next 2 days
			else if (availability.getAvailableFrom().isBefore(currTime.plusDays(noOfDays)))
				validAvailability.add(availability);
		}

		System.out.println("Valid Availability = " + validAvailability.toString());
		return validAvailability;
	}

	@Override
	public void deleteAvailability(long id)
	{
		availabilityRepository.deleteById(id);
	}

}
