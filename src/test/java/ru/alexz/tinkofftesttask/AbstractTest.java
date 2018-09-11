package ru.alexz.tinkofftesttask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.alexz.tinkofftesttask.model.resource.ContactApplicationResource;
import ru.alexz.tinkofftesttask.model.resource.ContactResource;
import ru.alexz.tinkofftesttask.model.resource.ErrorCodeResource;
import ru.alexz.tinkofftesttask.model.resource.ErrorResource;

import java.io.IOException;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class AbstractTest {

    protected static final String HOST = "http://localhost:";

    protected static final String APPLICATION_PATH = "/restapi/v1.0/application";
    protected static final String CONTACT_PATH = "/restapi/v1.0/contact";

    @Value("${local.server.port}")
    protected int port;

    protected String applicationUrl;
    protected String contactUrl;

    protected RestTemplate restTemplate = new RestTemplate();
    protected ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void beforeTest() {
        applicationUrl = HOST + port + APPLICATION_PATH;
        contactUrl = HOST + port + CONTACT_PATH;

        deleteAllApplications();
        deleteAllContacts();
    }

    protected ErrorResource sendInvalidRequest(String url, HttpEntity<?> requestEntity, HttpMethod httpMethod) {
        ResponseEntity<ErrorResource> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    url,
                    httpMethod,
                    requestEntity,
                    ErrorResource.class);
        } catch(HttpStatusCodeException e) {
            try {
                responseEntity = ResponseEntity.<ErrorResource>status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                        .body(objectMapper.readValue(e.getResponseBodyAsString(), ErrorResource.class));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        return responseEntity.getBody();
    }

    protected ContactApplicationResource contactApplication(String contactId, LocalDateTime createdDate) {
        return ContactApplicationResource.builder()
                .contactId(contactId)
                .createdDate(createdDate)
                .productName("testName")
                .build();
    }

    protected ContactApplicationResource createContactApplication(ContactApplicationResource applicationResource) {
        HttpEntity<ContactApplicationResource> httpEntity = new HttpEntity<>(applicationResource);
        ResponseEntity<ContactApplicationResource> resourceResponseEntity = restTemplate.exchange(
                applicationUrl,
                HttpMethod.PUT,
                httpEntity,
                ContactApplicationResource.class);

        return resourceResponseEntity.getBody();
    }

    protected ContactResource createContact(String contactId) {
        HttpEntity<ContactResource> httpEntity = new HttpEntity<>(ContactResource.builder().contactId(contactId).build());
        ResponseEntity<ContactResource> responseEntity = restTemplate.exchange(
                contactUrl,
                HttpMethod.PUT,
                httpEntity,
                ContactResource.class);
        return responseEntity.getBody();
    }

    protected void deleteAllContacts() {
        restTemplate.exchange(
                contactUrl,
                HttpMethod.DELETE,
                null,
                Void.class);
    }

    protected void deleteAllApplications() {
        restTemplate.exchange(
                applicationUrl,
                HttpMethod.DELETE,
                null,
                Void.class);
    }

    protected ErrorResource getErrorResource(HttpStatus httpStatus, String message) {
        ErrorCodeResource errorCodeResource = ErrorCodeResource.builder()
                .httpStatus(httpStatus)
                .message(message)
                .build();

        return ErrorResource.builder()
                .error(errorCodeResource)
                .build();
    }
}
