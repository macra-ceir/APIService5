package com.gl.mdr.model.constants;

public enum OrderColumnMapping {
	
	createdOn("Created Date"), modifiedOn("Modified Date"), deviceId("TAC"), userId("User ID"),
	brandName("Brand Name"), modelName("Model Name"), deviceState("Status");

	private String column;

	OrderColumnMapping(String column) {
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	public static OrderColumnMapping getColumnMapping(String column) {
		for (OrderColumnMapping columns : OrderColumnMapping.values()) {
			if ( columns.column.equals(column) )
				return columns;
		}

		return null;
	}
	
}
