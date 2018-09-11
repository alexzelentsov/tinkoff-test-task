package ru.alexz.tinkofftesttask;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import ru.alexz.tinkofftesttask.model.resource.ContactResource;
import ru.alexz.tinkofftesttask.model.resource.ErrorResource;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContactControllerTest extends AbstractTest {

    @Test
    public void createContact() {
        String contactId = UUID.randomUUID().toString();

        ContactResource contactResource = createContact(contactId);

        assertThat(contactResource.getContactId(), is(contactId));
    }

    @Test
    public void error400IfRequestBodyIsNull() {
        ErrorResource errorResource = sendInvalidRequest(contactUrl, null, HttpMethod.PUT);
        assertThat(errorResource, is(getErrorResource(HttpStatus.BAD_REQUEST, "Invalid request body")));
    }

    @Test
    public void error400IfContactIdIsNull() {
        HttpEntity<ContactResource> httpEntity = new HttpEntity<>(ContactResource.builder().build());
        ErrorResource errorResource = sendInvalidRequest(contactUrl, httpEntity, HttpMethod.PUT);
        assertThat(errorResource, is(getErrorResource(HttpStatus.BAD_REQUEST, "Parameter [contactId] value is invalid")));
    }

}
