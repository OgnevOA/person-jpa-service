package telran.b7a.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.b7a.person.dao.PersonRepository;
import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.PersonDto;
import telran.b7a.person.dto.exceptions.PersonNotFoundException;
import telran.b7a.person.model.Address;
import telran.b7a.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	PersonRepository personRepository;
	ModelMapper modelMapper;

	@Autowired
	public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto removePerson(Integer id) {
		PersonDto person = findPersonById(id);
		personRepository.deleteById(id);
		return person;
	}

	@Override
	@Transactional
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.getById(id);
		person.setName(name);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonAddress(Integer id, AddressDto address) {
		Person person = personRepository.getById(id);
		person.setAddress(modelMapper.map(address, Address.class));
		personRepository.save(person); 
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findPersonsByName(name).stream()
								.map(p -> modelMapper.map(p, PersonDto.class))
								.collect(Collectors.toList());
	}

	@Override
	public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		LocalDate from = LocalDate.now().minusYears(minAge);
		LocalDate to = LocalDate.now().minusYears(maxAge);
		return personRepository.findByBirthDateBetween(to, from).stream()
								.map(p -> modelMapper.map(p, PersonDto.class))
								.collect(Collectors.toList());
	}

	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findPersonsByAddressCity(city).stream()
								.map(p -> modelMapper.map(p, PersonDto.class))
								.collect(Collectors.toList());
	}

}
