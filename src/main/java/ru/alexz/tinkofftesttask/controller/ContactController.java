package ru.alexz.tinkofftesttask.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.alexz.tinkofftesttask.model.resource.ContactResource;
import ru.alexz.tinkofftesttask.service.ContactService;

import static ru.alexz.tinkofftesttask.controller.ControllerConstants.RESTAPI_PATH;

@RestController
@RequestMapping(RESTAPI_PATH + "/contact")
@Api("Operations with contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Create contact", response = ContactResource.class)
    public ContactResource createContact(@RequestBody ContactResource contactResource) {
        return contactService.createContact(contactResource);
    }

    @DeleteMapping
    @ApiOperation(value = "Delete all contacts")
    public void deleteAll() {
        contactService.deleteAll();
    }
}
