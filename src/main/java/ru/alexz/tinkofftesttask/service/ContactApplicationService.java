package ru.alexz.tinkofftesttask.service;

import org.springframework.stereotype.Service;
import ru.alexz.tinkofftesttask.model.resource.ContactApplicationResource;

@Service
public interface ContactApplicationService {
    ContactApplicationResource getLatestApplicationByContactId(String contactId);

    ContactApplicationResource createApplication(ContactApplicationResource applicationResource);

    void deleteAll();
}
