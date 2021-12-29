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
public class ChildDto extends PersonDto {
	String kindergarten;

	public ChildDto(Integer id, String name, LocalDate birthDate, AddressDto address, String kindergarten) {
		super(id, name, birthDate, address);
		this.kindergarten = kindergarten;
	}
	

}
