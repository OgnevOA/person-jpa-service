package telran.b7a.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.b7a.person.dao.PersonRepository;
import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.dto.PersonDto;
import telran.b7a.person.dto.exceptions.PersonNotFoundException;
import telran.b7a.person.dto.exceptions.UnknownPersonTypeException;
import telran.b7a.person.model.Address;
import telran.b7a.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	private static final String MODEL_PACKAGE = "telran.b7a.person.model.";
	private static final String DTO_SUFFIX = "Dto";
	private static final String DTO_PACKAGE = "telran.b7a.person.dto.";
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
		personRepository.save(modelMapper.map(personDto, getModelClass(personDto)));
		return true;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends Person> getModelClass(PersonDto personDto) {
		String modelClassName = personDto.getClass().getSimpleName();
		modelClassName = modelClassName.substring(0, modelClassName.length() - 3);
		try {
			Class<? extends Person> clazz = (Class<? extends Person>) Class.forName(MODEL_PACKAGE + modelClassName);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnknownPersonTypeException();
		}
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		return modelMapper.map(person, getDtoClass(person));
	}

	@SuppressWarnings("unchecked")
	private Class<? extends PersonDto> getDtoClass(Person person) {
		String dtoClassName = person.getClass().getSimpleName();
		dtoClassName = dtoClassName + DTO_SUFFIX;
		try {
			Class<? extends PersonDto> clazz = (Class<? extends PersonDto>) Class.forName(DTO_PACKAGE + dtoClassName);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnknownPersonTypeException();
		}
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
//		personRepository.save(person);
		return modelMapper.map(person, getDtoClass(person));
	}

	@Override
	@Transactional
	public PersonDto updatePersonAddress(Integer id, AddressDto address) {
		Person person = personRepository.getById(id);
		person.setAddress(modelMapper.map(address, Address.class));
		personRepository.save(person);
		return modelMapper.map(person, getDtoClass(person));
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findByName(name).map(p -> modelMapper.map(p, getDtoClass(p)))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		LocalDate from = LocalDate.now().minusYears(minAge);
		LocalDate to = LocalDate.now().minusYears(maxAge);
		return personRepository.findByBirthDateBetween(to, from).map(p -> modelMapper.map(p, getDtoClass(p)))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findPersonsByCity(city).map(p -> modelMapper.map(p, getDtoClass(p)))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<CityPopulationDto> getCityPopulation() {
		return personRepository.getCityPopulation();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findEmployeeBySalary(int min, int max) {
		return personRepository.findEmployeesBySalaryRange(min, max).map(p -> modelMapper.map(p, getDtoClass(p)))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> getChildren() {
		return personRepository.getChildren().map(p -> modelMapper.map(p, getDtoClass(p)))
				.collect(Collectors.toList());
	}

}
