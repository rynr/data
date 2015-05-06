package org.rjung.data.resources;

import org.rjung.data.object.Data;
import org.rjung.data.object.Series;
import org.rjung.data.repository.DataRepository;
import org.rjung.data.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DataResource {
	public static final Logger LOG = LoggerFactory
			.getLogger(DataResource.class);
	DataRepository data;
	SeriesRepository series;

	@Autowired
	public DataResource(DataRepository data, SeriesRepository series) {
		this.data = data;
		this.series = series;
	}

	@RequestMapping(value = "series/{series}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedResources<Resource<Data>> getIndex(
			@PathVariable("series") String series,
			@PageableDefault(sort = "timestamp", direction = Direction.DESC, size = 250) Pageable page,
			PagedResourcesAssembler<Data> assembler) {
		LOG.error("data/" + series + " (" + page + ")");
		return assembler.toResource(data.findBySeriesName(series, page));
	}

	@RequestMapping(value = "series/{series}", method = RequestMethod.POST)
	public Data createData(@PathVariable("series") String name,
			@RequestBody Data data) {
		Series series = this.series.findByName(name);
		data.setSeries(series);
		if (data.getTimestamp() == null) {
			data.setTimestamp(new Date());
		}
		data.setId(null);
		return this.data.save(data);
	}
}
