package org.rjung.data;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.rjung.data.object.Series;
import org.rjung.data.resources.DataResource;
import org.rjung.data.resources.SeriesResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SeriesResourceAssembler extends
		ResourceAssemblerSupport<Series, Resource> {

	public SeriesResourceAssembler() {
		super(SeriesResource.class, Resource.class);
	}

	@Override
	public Resource<Series> toResource(Series entity) {
		Resource resource = new Resource<Series>(entity, linkTo(
				methodOn(DataResource.class).getIndex(entity.getName(), null,
						null)).withSelfRel());

		return resource;
	}

}
