package ru.alexz.tinkofftesttask.model.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CONTACTS")
public class ContactRecord {
    @Id
    @Column(name = "CONTACT_ID")
    private String contactId;
}
