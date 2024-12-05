package com.github.katemerek.dto.services;

import com.github.katemerek.dto.models.Person;
import com.github.katemerek.dto.repositories.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@ComponentScan ("com.github.katemerek.dto.repositories")
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private Person person;

    public void loadUserByName(String name) throws UsernameNotFoundException, IOException {
        Optional<Person> person = peopleRepository.findByName(name);
        if (person.isPresent()) {
            this.person = person.get();
        } else throw new UsernameNotFoundException("Пользователь не найден! Пройдите регистрацию или проверьте имя пользователя.");
    }

        public List <Person> checkTrueLoggingStatus () {
        List<Person> loggedUsers = peopleRepository.findAllByStatus(true);
        return loggedUsers;
        }


    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setStatus(false);
        peopleRepository.save(person);
    }
}
