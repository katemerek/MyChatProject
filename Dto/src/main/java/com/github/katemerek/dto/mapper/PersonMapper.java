package com.github.katemerek.dto.mapper;

import com.github.katemerek.dto.dto.PersonDto;
import com.github.katemerek.dto.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    Person toPerson(PersonDto personDto);
    PersonDto toPersonDto(Person person);
}
