package weekendgaming.controller;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import weekendgaming.dto.AvailabilityDetailsRequestDTO;
import weekendgaming.model.Availability;
import weekendgaming.service.AvailabilityService;

@RestController
@RequestMapping("/api")
@Slf4j
public class AvailabilityController
{

	@Autowired
	private AvailabilityService availabilityService;

	@GetMapping("/availability")
	public List<Availability> getFutureAvailability()
	{
		log.info("Request for Availability Details");
		return availabilityService.getFutureAvailability();
	}

	@GetMapping("/availability/days/{noOfDays}")
	public List<Availability> getFutureAvailability(@PathVariable("noOfDays") int noOfDays)
	{
		log.info("Request for Availability Details for the next " + noOfDays + " days");
		return availabilityService.getFutureAvailability(noOfDays);
	}

	@PostMapping("/availability")
	public void addAvailability(@RequestBody AvailabilityDetailsRequestDTO availabilityDetails)
	{
		log.info("Adding new Availability Detail - " + availabilityDetails.toString());
		availabilityService.addAvailability(availabilityDetails);
	}

	@DeleteMapping("/availability/{id}")
	public void deleteAvailability(@PathVariable("id") long id)
	{
		log.info("Deleting availability with ID - " + id);
		availabilityService.deleteAvailability(id);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public void handleException(ConstraintViolationException e)
	{
		log.warn("Constraint Violation Exception - Multiple Entries");
	}

}
