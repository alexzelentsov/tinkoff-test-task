package ru.alexz.tinkofftesttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexz.tinkofftesttask.model.record.ContactRecord;

@Repository
public interface ContactRepository extends JpaRepository<ContactRecord, String> {
}
