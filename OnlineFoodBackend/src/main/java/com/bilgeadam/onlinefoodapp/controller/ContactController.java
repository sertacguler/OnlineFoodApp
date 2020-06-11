package com.bilgeadam.onlinefoodapp.controller;

import com.bilgeadam.onlinefoodapp.domain.Contact;
import com.bilgeadam.onlinefoodapp.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/")
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/contact")
    public Optional<Contact> contactInfo() {
        return contactService.info();
    }

}
