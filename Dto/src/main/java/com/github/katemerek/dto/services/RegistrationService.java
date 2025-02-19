package com.github.katemerek.dto.services;

import com.github.katemerek.dto.models.Person;
import com.github.katemerek.dto.repositories.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@ComponentScan("com.github.katemerek.dto.repositories")
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private Person person;


    public void loadUserByName(String name) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByName(name);
        if (person.isPresent()) {
            this.person = person.get();
        } else
            throw new UsernameNotFoundException("Пользователь не найден! Пройдите регистрацию или проверьте имя пользователя.");
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }
}
