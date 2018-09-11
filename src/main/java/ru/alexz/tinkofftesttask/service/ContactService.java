package ru.alexz.tinkofftesttask.service;

import ru.alexz.tinkofftesttask.model.record.ContactRecord;
import ru.alexz.tinkofftesttask.model.resource.ContactResource;

import java.util.Optional;

public interface ContactService {
    ContactResource createContact(ContactResource contactResource);

    Optional<ContactRecord> getContactById(String contactId);

    void deleteAll();
}
