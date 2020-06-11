package com.bilgeadam.onlinefoodapp.service;

import com.bilgeadam.onlinefoodapp.domain.Contact;
import com.bilgeadam.onlinefoodapp.repo.ContactRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    private ContactRepo contactRepo;

    public ContactService(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public Optional<Contact> info() {
        return contactRepo.findById(1L);
    }

}



