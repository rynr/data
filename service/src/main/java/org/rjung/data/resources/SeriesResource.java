package org.rjung.data.resources;

import org.rjung.data.SeriesResourceAssembler;
import org.rjung.data.object.Series;
import org.rjung.data.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeriesResource {
	public static final Logger LOG = LoggerFactory
			.getLogger(SeriesResource.class);
	SeriesRepository series;
	SeriesResourceAssembler assembler;

	@Autowired
	public SeriesResource(SeriesRepository series,
			SeriesResourceAssembler assembler) {
		this.series = series;
		this.assembler = assembler;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "series", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedResources<Resource> getSeriesIndex(Pageable page,
			PagedResourcesAssembler<Series> assembler) {
		LOG.error("GET series");
		return assembler.toResource(series.findAll(page), this.assembler);
	}

	@RequestMapping(value = "series", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Series updateSeries(@RequestBody Series series) {
		LOG.error("POST series (" + series + ")");
		return this.series.save(series);
	}

	@RequestMapping(value = "series/{series}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Series> update(@PathVariable("series") String name,
			@RequestBody Series update) {
		LOG.error("POST series/" + name + " (" + update + ")");
		Series old = series.findByName(name);
		if (old != null) {
			old.setName(update.getName());
			return assembler.toResource(series.save(old));
		}
		return assembler.toResource(update);
	}

}
