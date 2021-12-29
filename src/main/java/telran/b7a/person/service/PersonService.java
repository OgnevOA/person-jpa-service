package telran.b7a.person.service;

import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.dto.PersonDto;

public interface PersonService {
	public boolean addPerson(PersonDto personDto);

	public PersonDto findPersonById(Integer id);

	public PersonDto removePerson(Integer id);

	public PersonDto updatePersonName(Integer id, String name);

	public PersonDto updatePersonAddress(Integer id, AddressDto address);

	public Iterable<PersonDto> findPersonsByName(String name);

	public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge);

	public Iterable<PersonDto> findPersonsByCity(String city);
	
	public Iterable<CityPopulationDto> getCityPopulation();
	
	public Iterable<PersonDto> findEmployeeBySalary(int min, int max);
	
	public Iterable<PersonDto> getChildren();

}
