package com.nb.futbol24checker.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "F24")
public class FutbolResponse {

    @JacksonXmlElementWrapper(localName = "Mecze")
    private List<Match> matches;
}
