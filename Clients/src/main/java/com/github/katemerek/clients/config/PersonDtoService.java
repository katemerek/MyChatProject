package com.github.katemerek.clients.config;

import com.github.katemerek.dto.dto.PersonDto;
import lombok.Data;
import org.springframework.stereotype.Service;


@Data
@Service
public class PersonDtoService {
        private PersonDto currentUser;
    }
