package com.github.katemerek.myChatProject.services;

import com.github.katemerek.myChatProject.models.Person;
import com.github.katemerek.myChatProject.repositories.PeopleRepository;
import com.github.katemerek.myChatProject.security.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Component
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByName(name);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }
        return new PersonDetails(person.get());
    }
}
