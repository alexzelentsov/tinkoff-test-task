package ru.alexz.tinkofftesttask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexz.tinkofftesttask.exception.CommonException;
import ru.alexz.tinkofftesttask.exception.ErrorCode;
import ru.alexz.tinkofftesttask.model.record.ContactRecord;
import ru.alexz.tinkofftesttask.model.resource.ContactResource;
import ru.alexz.tinkofftesttask.repository.ContactRepository;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public ContactResource createContact(ContactResource contactResource) {
        String contactId = contactResource.getContactId();
        if (contactId == null) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER, "contactId");
        }
        ContactRecord contactRecord = ContactRecord.builder()
                .contactId(contactId)
                .build();
        ContactRecord savedRecord = contactRepository.save(contactRecord);
        return ContactResource.builder()
                .contactId(savedRecord.getContactId())
                .build();
    }

    @Override
    public Optional<ContactRecord> getContactById(String contactId) {
        return contactRepository.findById(contactId);
    }

    @Override
    public void deleteAll() {
        contactRepository.deleteAll();
    }
}
