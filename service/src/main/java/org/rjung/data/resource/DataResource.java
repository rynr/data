package org.rjung.data.resource;

import org.rjung.data.object.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class DataResource extends Resource<Data> {

    public DataResource(Data content, Link... links) {
        super(content, links);
        // TODO Auto-generated constructor stub
    }
}
