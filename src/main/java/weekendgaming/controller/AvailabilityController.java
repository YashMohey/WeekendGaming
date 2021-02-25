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

import weekendgaming.dto.AvailabilityDetailsRequestDTO;
import weekendgaming.model.Availability;
import weekendgaming.service.AvailabilityService;

@RestController
@RequestMapping("/api")
public class AvailabilityController
{

	@Autowired
	private AvailabilityService availabilityService;

	@GetMapping("/availability")
	public List<Availability> getFutureAvailability()
	{
		return availabilityService.getFutureAvailability();
	}
	
	@GetMapping("/availability/days/{noOfDays}")
	public List<Availability> getFutureAvailability(@PathVariable("noOfDays") int noOfDays)
	{
		return availabilityService.getFutureAvailability(noOfDays);
	}

	@PostMapping("/availability")
	public void addAvailability(@RequestBody AvailabilityDetailsRequestDTO availabilityDetails)
	{
		availabilityService.addAvailability(availabilityDetails);
	}

	@DeleteMapping("/availability/{id}")
	public void deleteAvailability(@PathVariable("id") long id)
	{
		availabilityService.deleteAvailability(id);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public void handleException(ConstraintViolationException e)
	{
		System.out.println("Constraint Violation Exception - Multiple Entries");
	}

}
