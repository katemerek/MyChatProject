package com.github.katemerek.clients.services;

import com.github.katemerek.clients.repositories.PeopleRepository;
import com.github.katemerek.clients.servers.Server;
import com.github.katemerek.clients.models.Person;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    Server server;

    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, Server server) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.server = server;
    }

    public void loadUserByName(String name) throws UsernameNotFoundException, IOException {
        Optional<Person> person = peopleRepository.findByName(name);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден! Пройдите регистрацию.");
        }
        }


        public ArrayList checkTrueLoggingStatus () {
        List<Person> loggedUsers = peopleRepository.findAllByStatus(true);
        ArrayList <String> loggedUsersName = new ArrayList();
        for (Person person : loggedUsers) {
            loggedUsersName.add(person.getName());
        }
        return loggedUsersName;
        }


    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }
}
