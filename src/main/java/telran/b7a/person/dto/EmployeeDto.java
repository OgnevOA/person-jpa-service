package telran.b7a.person.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDto extends PersonDto {
	String company;
	Integer salary;
	
	public EmployeeDto(Integer id, String name, LocalDate birthDate, AddressDto address, String company, int salary) {
		super(id, name, birthDate, address);
		this.company = company;
		this.salary = salary;
	}
	

}
