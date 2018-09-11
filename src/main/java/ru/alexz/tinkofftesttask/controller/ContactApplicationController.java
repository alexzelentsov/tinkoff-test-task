package ru.alexz.tinkofftesttask.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.alexz.tinkofftesttask.exception.CommonException;
import ru.alexz.tinkofftesttask.exception.ErrorCode;
import ru.alexz.tinkofftesttask.model.resource.ContactApplicationResource;
import ru.alexz.tinkofftesttask.service.ContactApplicationService;

import java.time.LocalDateTime;

import static ru.alexz.tinkofftesttask.controller.ControllerConstants.RESTAPI_PATH;

@RestController
@RequestMapping(RESTAPI_PATH + "/application")
@Api("Operations with applications")
public class ContactApplicationController {

    private final ContactApplicationService contactApplicationService;

    @Autowired
    public ContactApplicationController(ContactApplicationService contactApplicationService) {
        this.contactApplicationService = contactApplicationService;
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Create application", response = ContactApplicationResource.class)
    public ContactApplicationResource createApplication(@RequestBody ContactApplicationResource applicationResource) {
        String contactId = applicationResource.getContactId();
        if (contactId == null) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER, "CONTACT_ID");
        }

        LocalDateTime createdDate = applicationResource.getCreatedDate();
        if (createdDate == null) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER, "DT_CREATED");
        }

        if (createdDate.isAfter(LocalDateTime.now())) {
            throw new CommonException(ErrorCode.INVALID_CREATED_DATE);
        }
        return contactApplicationService.createApplication(applicationResource);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Getting latest application by contact id", response = ContactApplicationResource.class)
    public ContactApplicationResource getLatestApplicationByContactId(
            @RequestParam(required = false)
            @ApiParam(value = "contact id", example = "53e40bb2", required = true) String contactId) {
        if (contactId == null) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER, "CONTACT_ID");
        }
        return contactApplicationService.getLatestApplicationByContactId(contactId);
    }

    @DeleteMapping
    @ApiOperation(value = "Delete all applications")
    public void deleteAll() {
        contactApplicationService.deleteAll();
    }

}
