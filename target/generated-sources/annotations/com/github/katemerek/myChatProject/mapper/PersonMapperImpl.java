package com.github.katemerek.myChatProject.mapper;

import com.github.katemerek.myChatProject.dto.PersonDto;
import com.github.katemerek.myChatProject.models.Person;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T11:49:20+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public Person toPerson(PersonDto personDto) {
        if ( personDto == null ) {
            return null;
        }

        Person person = new Person();

        person.setName( personDto.getName() );
        person.setPassword( personDto.getPassword() );
        person.setYearOfBirth( personDto.getYearOfBirth() );

        return person;
    }

    @Override
    public PersonDto toPersonDto(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDto personDto = new PersonDto();

        personDto.setName( person.getName() );
        personDto.setPassword( person.getPassword() );
        personDto.setYearOfBirth( person.getYearOfBirth() );

        return personDto;
    }
}
