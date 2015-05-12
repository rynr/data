package org.rjung.data.repository;

import java.util.Date;

import org.rjung.data.object.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends CrudRepository<Data, Long> {
    Page<Data> findBySeriesName(String name, Pageable page);
    Data findBySeriesNameAndTimestamp(String name, Date timestamp);
}