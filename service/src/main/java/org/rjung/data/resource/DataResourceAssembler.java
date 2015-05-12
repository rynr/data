package org.rjung.data.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.rjung.data.controller.DataController;
import org.rjung.data.controller.SeriesController;
import org.rjung.data.object.Data;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class DataResourceAssembler extends
        ResourceAssemblerSupport<Data, DataResource> {

    public DataResourceAssembler() {
        super(SeriesController.class, DataResource.class);
    }

    @Override
    public DataResource toResource(Data entity) {
        DataResource resource = new DataResource(entity, linkTo(
                methodOn(DataController.class).getData(
                        entity.getSeries().getName(),
                        entity.getTimestamp().getTime())).withSelfRel());

        return resource;
    }
}
