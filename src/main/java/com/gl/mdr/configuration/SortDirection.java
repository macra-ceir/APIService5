package com.gl.mdr.configuration;

import org.springframework.stereotype.Component;
import org.springframework.data.domain.Sort;


@Component
public class SortDirection {
	public static Sort.Direction getSortDirection(String direction) {
	    if (direction.equals("asc")) {
	      return Sort.Direction.ASC;
	    } else if (direction.equals("desc")) {
	      return Sort.Direction.DESC;
	    }

	    return Sort.Direction.DESC;
	  }
}
