package com.hha.demo.dto.dataTables.output;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTablesServerSideOutput<T> {

	private long draw;
	private long recordsTotal;
	private long recordsFiltered;
	private List<T> data;
	
	public DataTablesServerSideOutput<T> listSubList(int start, int length) {
		if (this.data.size() >= start + length) {
			this.setData(this.data.subList(start, start+length));
		} else {
			this.setData(this.data.subList(start, this.data.size()));
		}
		return this;
	}
}
