package com.kidscademy.www;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import js.xpath.client.XPath;

public interface SoftSchools {
    @Path("facts/{path}")
    @XPath("//*[@class='factsmaintbl']/*/TD")
    String getDescription(@PathParam("path") String path);

    @Path("facts/{path}")
    SoftSchoolsFacts getFacts(@PathParam("path") String path);

    @Path("facts/{category}")
    @XPath(value = "//*[@id='sortable_list']/A", attribute = "href")
    List<String> getFactLinks(@PathParam("category") String category);
}
