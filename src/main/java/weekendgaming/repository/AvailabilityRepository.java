package weekendgaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import weekendgaming.model.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long>
{

}
