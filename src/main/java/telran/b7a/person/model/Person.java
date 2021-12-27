package telran.b7a.person.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "persons")
public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4478261898905305126L;
	@Id
	int id;
	String name;
	LocalDate birthDate;
	//@Embedded
	Address address;

}
