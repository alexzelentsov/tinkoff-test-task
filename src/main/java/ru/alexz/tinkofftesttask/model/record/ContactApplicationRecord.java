package ru.alexz.tinkofftesttask.model.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "APPLICATIONS")
public class ContactApplicationRecord {
    @Id
    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "CONTACT_ID")
    private String contactId;

    @Column(name = "DT_CREATED")
    private LocalDateTime createdDate;

    @Column(name = "PRODUCT_NAME")
    private String productName;
}
