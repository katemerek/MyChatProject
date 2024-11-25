package com.github.katemerek.server.services;

import com.github.katemerek.clients.models.Person;
import com.github.katemerek.server.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistrationService {

    private final com.github.katemerek.server.repositories.PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private Server server;
    private Person person;

    public void loadUserByName(String name) throws UsernameNotFoundException, IOException {
        Optional<Person> person = peopleRepository.findByName(name);
        if (person.isPresent()) {
            this.person = person.get();
        } else throw new UsernameNotFoundException("Пользователь не найден! Пройдите регистрацию.");
    }


        public ArrayList checkTrueLoggingStatus () {
        ArrayList<Person> loggedUsers = (ArrayList<Person>) peopleRepository.findAllByStatus(true);
        return loggedUsers;
        }


    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }
}
