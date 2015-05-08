package org.rjung.data.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.rjung.data.controller.DataController;
import org.rjung.data.controller.SeriesController;
import org.rjung.data.object.Series;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SeriesResourceAssembler extends
		ResourceAssemblerSupport<Series, Resource> {

	public SeriesResourceAssembler() {
		super(SeriesController.class, Resource.class);
	}

	@Override
	public Resource<Series> toResource(Series entity) {
		Resource resource = new Resource<Series>(entity, linkTo(
				methodOn(DataController.class).getIndex(entity.getName(), null,
						null)).withSelfRel());

		return resource;
	}

}
