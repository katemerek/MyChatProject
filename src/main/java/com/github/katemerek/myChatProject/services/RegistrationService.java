package com.github.katemerek.myChatProject.services;

import com.github.katemerek.myChatProject.clients.Client;
import com.github.katemerek.myChatProject.models.Person;
import com.github.katemerek.myChatProject.repositories.PeopleRepository;
import com.github.katemerek.myChatProject.security.PersonDetails;
import com.github.katemerek.myChatProject.servers.CommunicationHandler;
import com.github.katemerek.myChatProject.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.Socket;
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
