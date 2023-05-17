package com.nb.futbol24checker.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@JacksonXmlRootElement(localName = "M")
public class Match {

    @JacksonXmlProperty(localName = "HN")
    private String homeNameTeam;
    @JacksonXmlProperty(localName = "GN")
    private String guestNameTeam;
    @JacksonXmlProperty(localName = "C0")
    private String timestamp;

}
