//package com.github.katemerek.myChatProject.controllers;
//
//import com.github.katemerek.myChatProject.dto.PersonDto;
//import com.github.katemerek.myChatProject.mapper.PersonMapper;
//import com.github.katemerek.myChatProject.models.Person;
//import com.github.katemerek.myChatProject.services.RegistrationService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/auth")
//public class AuthController{
//
//    private final RegistrationService registrationService;
//    private final PersonMapper personMapper;
//
//    @GetMapping("/login")
//    private String login (){
//        return "login";
//    }
//
//    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("person") PersonDto personDto){
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String performRegistration(@ModelAttribute("Person") @Valid PersonDto personDto, BindingResult result){
//        Person personAdd = personMapper.toPerson(personDto);
//        registrationService.register(personAdd);
//        return "redirect:/auth/login";
//    }
//}
