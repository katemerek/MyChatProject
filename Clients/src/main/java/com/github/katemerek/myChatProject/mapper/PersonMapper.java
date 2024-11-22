package com.github.katemerek.myChatProject.mapper;

import com.github.katemerek.myChatProject.dto.PersonDto;
import com.github.katemerek.myChatProject.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    Person toPerson (PersonDto personDto);
    PersonDto toPersonDto (Person person);
}
