package org.rjung.data.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.xalan.xsltc.dom.KeyIndex;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "series_id", "timestamp" }))
public class Data {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotNull
	@ManyToOne
	@JsonIgnore
	private Series series;

	@Id
	@Column
	@NotNull
	Date timestamp;

	@Column
	@NotNull
	BigDecimal value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Data [id=" + id + ", series=\""
				+ (series == null ? "null" : series.getName())
				+ "\", timestamp=" + timestamp + ", value=" + value + "]";
	}
}