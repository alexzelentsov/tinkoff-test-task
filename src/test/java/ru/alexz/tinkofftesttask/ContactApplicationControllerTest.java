package ru.alexz.tinkofftesttask;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.alexz.tinkofftesttask.model.resource.ContactApplicationResource;
import ru.alexz.tinkofftesttask.model.resource.ErrorResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ContactApplicationControllerTest extends AbstractTest {

    @Test
    public void createApplication() {
        String contactId = UUID.randomUUID().toString();

        createContact(contactId);
        ContactApplicationResource applicationResourceForCreate = contactApplication(contactId, LocalDateTime.now().minusDays(1));
        ContactApplicationResource contactApplication = createContactApplication(applicationResourceForCreate);

        assertThat(contactApplication.getApplicationId(), notNullValue());
        assertThat(contactApplication.getContactId(), is(applicationResourceForCreate.getContactId()));
        assertThat(contactApplication.getCreatedDate(), is(applicationResourceForCreate.getCreatedDate()));
        assertThat(contactApplication.getProductName(), is(applicationResourceForCreate.getProductName()));
    }

    @Test
    public void getLatestApplicationByContactId() {
        String contactId = UUID.randomUUID().toString();

        createContact(contactId);
        createContactApplication(contactApplication(contactId, LocalDateTime.now().minusDays(2)));

        ContactApplicationResource expectedContactApplication = createContactApplication(contactApplication(contactId, LocalDateTime.now().minusDays(1)));

        ResponseEntity<ContactApplicationResource> responseEntity = restTemplate.exchange(
                HOST + port + APPLICATION_PATH + "?contactId=" + contactId,
                HttpMethod.GET,
                null,
                ContactApplicationResource.class);
        ContactApplicationResource applicationResource = responseEntity.getBody();
        assertThat(applicationResource, is(expectedContactApplication));
    }

    @Test
    public void getLatestApplicationByContactIdWithXmlResponse() {
        List<String> acceptHeaderValue = ImmutableList.of("application/xml");

        getLatestApplicationByContactIdWithAcceptHeader(acceptHeaderValue);
    }

    @Test
    public void getLatestApplicationByContactIdWithJsonResponse() {
        List<String> acceptHeaderValue = ImmutableList.of("application/json;charset=UTF-8");

        getLatestApplicationByContactIdWithAcceptHeader(acceptHeaderValue);
    }

    @Test
    public void error400IfCreateApplicationWithWrongContactID() {
        String contactId = UUID.randomUUID().toString();

        ContactApplicationResource applicationResourceForCreate = contactApplication(contactId, LocalDateTime.now().minusDays(1));

        ErrorResource errorResource = sendInvalidRequest(applicationUrl, new HttpEntity<>(applicationResourceForCreate), HttpMethod.PUT);
        assertThat(errorResource, is(getErrorResource(HttpStatus.BAD_REQUEST, "Contact is not found for contactId")));
    }

    @Test
    public void error400IfCreateApplicationWithoutCreatedDate() {
        String contactId = UUID.randomUUID().toString();

        ContactApplicationResource applicationResourceForCreate = contactApplication(contactId, null);

        ErrorResource errorResource = sendInvalidRequest(applicationUrl, new HttpEntity<>(applicationResourceForCreate), HttpMethod.PUT);
        assertThat(errorResource, is(getErrorResource(HttpStatus.BAD_REQUEST, "Parameter [DT_CREATED] value is invalid")));
    }

    @Test
    public void error400IfCreateApplicationWithCreatedDateFromFuture() {
        String contactId = UUID.randomUUID().toString();

        ContactApplicationResource applicationResourceForCreate = contactApplication(contactId, LocalDateTime.now().plusDays(1));

        ErrorResource errorResource = sendInvalidRequest(applicationUrl, new HttpEntity<>(applicationResourceForCreate), HttpMethod.PUT);
        assertThat(errorResource, is(getErrorResource(HttpStatus.BAD_REQUEST, "Parameter [DT_CREATED] should not be from future")));
    }

    @Test
    public void error400IfRequestBodyIsNull() {
        ErrorResource errorResource = sendInvalidRequest(applicationUrl, null, HttpMethod.PUT);
        assertThat(errorResource, is(getErrorResource(HttpStatus.BAD_REQUEST, "Invalid request body")));
    }

    @Test
    public void error400IfContactDoesNotExist() {
        String contactId = UUID.randomUUID().toString();

        ErrorResource errorResource = sendInvalidRequest(applicationUrl + "?contactId=" + contactId, null, HttpMethod.GET);
        assertThat(errorResource, is(getErrorResource(HttpStatus.BAD_REQUEST, "Contact is not found for contactId")));
    }

    @Test
    public void error404IfApplicationDoesNotExist() {
        String contactId = UUID.randomUUID().toString();

        createContact(contactId);

        ErrorResource errorResource = sendInvalidRequest(applicationUrl + "?contactId=" + contactId, null, HttpMethod.GET);
        assertThat(errorResource, is(getErrorResource(HttpStatus.NOT_FOUND, "Applications for contactId are not found")));
    }

    private void getLatestApplicationByContactIdWithAcceptHeader(List<String> headerValue) {
        String contactId = UUID.randomUUID().toString();

        createContact(contactId);
        createContactApplication(contactApplication(contactId, LocalDateTime.now().minusDays(2)));

        ContactApplicationResource expectedContactApplication = createContactApplication(contactApplication(contactId, LocalDateTime.now().minusDays(1)));

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.put("Accept", headerValue);
        ResponseEntity<ContactApplicationResource> responseEntity = restTemplate.exchange(
                HOST + port + APPLICATION_PATH + "?contactId=" + contactId,
                HttpMethod.GET,
                new HttpEntity<>(null, multiValueMap),
                ContactApplicationResource.class);
        ContactApplicationResource applicationResource = responseEntity.getBody();
        assertThat(applicationResource, is(expectedContactApplication));
        assertThat(responseEntity.getHeaders().get("Content-Type"), is(headerValue));
    }
}
