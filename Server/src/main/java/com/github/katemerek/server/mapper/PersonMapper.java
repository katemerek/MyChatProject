package com.github.katemerek.server.mapper;

import com.github.katemerek.clients.dto.PersonDto;
import com.github.katemerek.clients.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    Person toPerson(PersonDto personDto);
    PersonDto toPersonDto(Person person);
}
