package telran.b7a.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
//	@Query(value = "SELECT * FROM persons WHERE name=?1", nativeQuery = true)
	@Query("select p from Person p where p.name=?1")
	public Stream<Person> findByName(String name);

	public Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);

	@Query("select p from Person p where p.address.city=:city")
	public Stream<Person> findPersonsByCity(@Param("city") String city);
	
	@Query("select new telran.b7a.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	public Iterable<CityPopulationDto> getCityPopulation();
	
	@Query("select c from Child c")
	public Stream<Person> getChildren();
	
	@Query("select e from Employee e where e.salary between ?1 and ?2")
	public Stream<Person> findEmployeesBySalaryRange(Integer min, Integer max);
}
