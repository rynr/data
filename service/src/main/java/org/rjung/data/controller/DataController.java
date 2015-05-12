package org.rjung.data.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;

import org.rjung.data.object.Data;
import org.rjung.data.object.Series;
import org.rjung.data.repository.DataRepository;
import org.rjung.data.repository.SeriesRepository;
import org.rjung.data.resource.DataResource;
import org.rjung.data.resource.DataResourceAssembler;
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

@RestController
public class DataController {
    public static final Logger LOG = LoggerFactory
            .getLogger(DataController.class);
    DataRepository data;
    SeriesRepository series;
    DataResourceAssembler assembler;

    @Autowired
    public DataController(DataRepository data, SeriesRepository series,
            DataResourceAssembler assembler) {
        this.data = data;
        this.series = series;
        this.assembler = assembler;
    }

    @RequestMapping(value = "series/{series}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedResources<DataResource> getIndex(
            @PathVariable("series") String series,
            @PageableDefault(sort = "timestamp", direction = Direction.DESC, size = 250) Pageable page,
            PagedResourcesAssembler<Data> assembler) {
        LOG.error("GET /series/" + series + " (" + page + ")");
        PagedResources<DataResource> resources = assembler.toResource(
                data.findBySeriesName(series, page), this.assembler);
        resources.add(linkTo(
                methodOn(SeriesController.class).getSeriesIndex(null, null))
                .withRel("index"));
        resources.add(linkTo(
                methodOn(DataController.class).getRawIndex(series, page))
                .withRel("raw"));
        return resources;
    }

    @RequestMapping(value = "series/{series}/{timestamp}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Data> getData(@PathVariable("series") String series,
            @PathVariable("timestamp") long ts) {
        Date timestamp = new Date(ts);
        LOG.error("GET /series/" + series + "/" + timestamp);
        Resource<Data> resource = this.assembler.toResource(data
                .findBySeriesNameAndTimestamp(series, timestamp));
        resource.add(linkTo(
                methodOn(DataController.class).getIndex(series, null, null))
                .withRel("index"));
        resource.add(linkTo(
                methodOn(SeriesController.class).getSeriesIndex(null, null))
                .withRel("series"));
        return resource;
    }

    @RequestMapping(value = "series/{series}.raw", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Data> getRawIndex(@PathVariable("series") String series,
            Pageable page) {
        LOG.error("GET /series/" + series + ".raw");
        return data.findBySeriesName(series, page).getContent();
    }

    @RequestMapping(value = "series/{series}", method = RequestMethod.POST)
    public Data createData(@PathVariable("series") String name,
            @RequestBody Data data) {
        LOG.error("POST /series/" + name + " (" + data + ")");
        Series series = this.series.findByName(name);
        data.setSeries(series);
        if (data.getTimestamp() == null) {
            data.setTimestamp(new Date());
        }
        return this.data.save(data);
    }
}
