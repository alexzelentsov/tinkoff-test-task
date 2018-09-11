package ru.alexz.tinkofftesttask.model.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alexz.tinkofftesttask.xmladapter.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "APPLICATION")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactApplicationResource {

    @XmlElement(name = "APPLICATION_ID")
    @JsonProperty("APPLICATION_ID")
    @ApiModelProperty(value = "Application Id")
    private String applicationId;

    @XmlElement(name = "CONTACT_ID")
    @JsonProperty("CONTACT_ID")
    @ApiModelProperty(value = "Id of a Client")
    private String contactId;

    @XmlElement(name = "DT_CREATED")
    @JsonProperty("DT_CREATED")
    @ApiModelProperty(value = "Creation date of application")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDateTime createdDate;

    @XmlElement(name = "PRODUCT_NAME")
    @JsonProperty("PRODUCT_NAME")
    @ApiModelProperty(value = "Product name")
    private String productName;
}
