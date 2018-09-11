package ru.alexz.tinkofftesttask.model.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "CONTACT")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactResource {
    @XmlElement(name = "CONTACT_ID")
    @JsonProperty("CONTACT_ID")
    @ApiModelProperty(value = "Id of a Client")
    private String contactId;
}
