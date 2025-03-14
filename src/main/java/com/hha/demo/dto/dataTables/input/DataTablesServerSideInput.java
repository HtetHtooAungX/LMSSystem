package com.hha.demo.dto.dataTables.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DataTablesServerSideInput {

	private long draw = 1;
//	private List<Column> columns = new ArrayList<Column>();
	private List<Map<String, String>> order = new ArrayList<>();
	private int start;
	private int length;
	private Map<String, String> search = new HashMap<>();
}
