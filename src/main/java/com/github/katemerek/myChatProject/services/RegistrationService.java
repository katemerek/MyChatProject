package com.github.katemerek.myChatProject.services;

import com.github.katemerek.myChatProject.models.Person;
import com.github.katemerek.myChatProject.repositories.PeopleRepository;
import com.github.katemerek.myChatProject.security.PersonDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void loadUserByName(String name) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByName(name);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден! Пройдите регистрацию.");
        }else if (passwordEncoder.matches(name, person.get().getPassword())) {
            ВОТ ТУТ РЕДИРЕКТ НА ЧАТ ДЛЯ НАЧАЛА ОБЩЕНИЯ
        }
    }


    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }
}
