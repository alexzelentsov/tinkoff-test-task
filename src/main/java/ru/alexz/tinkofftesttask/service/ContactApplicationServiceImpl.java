package ru.alexz.tinkofftesttask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexz.tinkofftesttask.exception.CommonException;
import ru.alexz.tinkofftesttask.exception.ErrorCode;
import ru.alexz.tinkofftesttask.model.record.ContactApplicationRecord;
import ru.alexz.tinkofftesttask.model.record.ContactRecord;
import ru.alexz.tinkofftesttask.model.resource.ContactApplicationResource;
import ru.alexz.tinkofftesttask.repository.ContactApplicationRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContactApplicationServiceImpl implements ContactApplicationService {

    private final ContactApplicationRepository contactApplicationRepository;
    private final ContactService contactService;

    @Autowired
    public ContactApplicationServiceImpl(ContactApplicationRepository contactApplicationRepository, ContactService contactService) {
        this.contactApplicationRepository = contactApplicationRepository;
        this.contactService = contactService;
    }

    @Override
    public ContactApplicationResource getLatestApplicationByContactId(String contactId) {

        checkExistContact(contactId);

        ContactApplicationRecord applicationRecord = contactApplicationRepository.findFirstByContactIdEqualsOrderByCreatedDateDesc(contactId);
        if (applicationRecord == null) {
            throw new CommonException(ErrorCode.APPLICATIONS_NOT_FOUND);
        }
        return getResource(applicationRecord);
    }

    @Override
    public ContactApplicationResource createApplication(ContactApplicationResource applicationResource) {
        String contactId = applicationResource.getContactId();

        checkExistContact(contactId);

        ContactApplicationRecord applicationRecord = getRecord(applicationResource);
        ContactApplicationRecord savedRecord = contactApplicationRepository.save(applicationRecord);
        return getResource(savedRecord);
    }

    @Override
    public void deleteAll() {
        contactApplicationRepository.deleteAll();
    }

    private void checkExistContact(String contactId) {
        Optional<ContactRecord> contactById = contactService.getContactById(contactId);
        if (!contactById.isPresent()) {
            throw new CommonException(ErrorCode.CONTACT_NOT_FOUND, "CONTACT_ID");
        }
    }

    private ContactApplicationRecord getRecord(ContactApplicationResource applicationResource) {
        return ContactApplicationRecord.builder()
                    .applicationId(UUID.randomUUID().toString())
                    .contactId(applicationResource.getContactId())
                    .createdDate(applicationResource.getCreatedDate())
                    .productName(applicationResource.getProductName())
                    .build();
    }

    private ContactApplicationResource getResource(ContactApplicationRecord record) {
        return ContactApplicationResource.builder()
                .applicationId(record.getApplicationId())
                .contactId(record.getContactId())
                .createdDate(record.getCreatedDate())
                .productName(record.getProductName())
                .build();
    }
}
