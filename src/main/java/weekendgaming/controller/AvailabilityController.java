package weekendgaming.controller;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/availability")
	public void addAvailability(@RequestBody AvailabilityDetailsRequestDTO availabilityDetails)
	{
		availabilityService.addAvailability(availabilityDetails);
	}

	@GetMapping("/availability")
	public List<Availability> getFutureAvailability()
	{
		return availabilityService.getFutureAvailability();
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public void handleException(ConstraintViolationException e)
	{
		System.out.println("Constraint Violation Exception - Multiple Entries");
	}

}
