package telran.b7a.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.PersonDto;
import telran.b7a.person.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@PostMapping
	public boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}
	
	@GetMapping("/{id}")
	public PersonDto findPerson(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}
	
	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.removePerson(id);
	}
	
	@PutMapping("/{id}/name/{name}")
	public PersonDto updateName(@PathVariable Integer id,@PathVariable String name) {
		return personService.updatePersonName(id, name);
	}
	
	@PutMapping("/{id}/address")
	public PersonDto updateAddress(@PathVariable Integer id,@RequestBody AddressDto address) {
		return personService.updatePersonAddress(id, address);
	}
	
	@GetMapping("/name/{name}")
	public Iterable<PersonDto> findByName(@PathVariable String name) {
		return personService.findPersonsByName(name);
	}
	
	@GetMapping("/ages/{min}/{max}")
	public Iterable<PersonDto> findByAge(@PathVariable Integer min,@PathVariable Integer max) {
		return personService.findPersonsBetweenAge(min, max);
	}
	
	@GetMapping("/city/{city}")
	public Iterable<PersonDto> findByCity(@PathVariable String city) {
		return personService.findPersonsByCity(city);
	}

}
