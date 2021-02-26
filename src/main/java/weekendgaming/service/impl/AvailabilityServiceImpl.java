package weekendgaming.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import weekendgaming.dto.AvailabilityDetailsRequestDTO;
import weekendgaming.model.Availability;
import weekendgaming.repository.AvailabilityRepository;
import weekendgaming.service.AvailabilityService;

@Service
@Transactional
@Slf4j
public class AvailabilityServiceImpl implements AvailabilityService
{

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Override
	public void addAvailability(AvailabilityDetailsRequestDTO availabilityDetails)
	{
		OffsetDateTime start = OffsetDateTime.parse(availabilityDetails.getAvailableFrom());
		OffsetDateTime end = OffsetDateTime.parse(availabilityDetails.getAvailableTill());

		if (start.isAfter(end))
		{
			log.warn("User End time was after start time. Details : " + availabilityDetails);
			return;
		}

		Availability availability = new Availability();

		availability.setName(availabilityDetails.getName());
		availability.setAvailableFrom(start);
		availability.setAvailableTill(end);

		availability = availabilityRepository.save(availability);

		log.info("Availability Added - " + availability.toString());
	}

	@Override
	public List<Availability> getFutureAvailability()
	{
		return getFutureAvailability(0);
	}

	@Override
	public List<Availability> getFutureAvailability(int noOfDays)
	{
		OffsetDateTime currTime = OffsetDateTime.now();
		log.debug("Fetching Details. Current Time = " + currTime.toString() + " | No of Days = " + noOfDays);

		List<Availability> allAvailability = availabilityRepository.findAll();
		log.debug("All Details : " + allAvailability.toString());

		List<Availability> response = new ArrayList<>();
		for (Availability availability : availabilityRepository.findAll())
		{
			log.debug("User : " + availability.getName() + " | Time : " + availability.getAvailableFrom() + " - " + availability.getAvailableTill());

			// Delete Old Dates and Clear Database
			if (availability.getAvailableTill().isBefore(currTime))
			{
				log.debug("Deleting outdated detail : " + availability.toString());
				availabilityRepository.delete(availability);
			}

			// Check if anyone starts playing in the future no of days
			else if (noOfDays == 0 || availability.getAvailableFrom().isBefore(currTime.plusDays(noOfDays)))
			{
				response.add(availability);
			}
		}

		// Sort the response
		response = response.stream()
			.sorted(Comparator.comparing(Availability::getAvailableFrom))
			.collect(Collectors.toList());

		log.info("Availability Details : " + response.toString());
		return response;
	}

	@Override
	public void deleteAvailability(long id)
	{
		availabilityRepository.deleteById(id);
		log.info("Availability with ID: " + id + " deleted");
	}

}
