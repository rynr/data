package org.rjung.data.repository;

import org.rjung.data.object.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {

	public Series findByName(String name);

}