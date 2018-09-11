package ru.alexz.tinkofftesttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexz.tinkofftesttask.model.record.ContactApplicationRecord;

@Repository
public interface ContactApplicationRepository extends JpaRepository<ContactApplicationRecord, String> {

    ContactApplicationRecord findFirstByContactIdEqualsOrderByCreatedDateDesc(String contactId);
}
