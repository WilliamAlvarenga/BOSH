package com.bosh.challenger.entity.enumeration;

import java.util.Optional;
import java.util.stream.Stream;

public enum Status {

	ACTIVE,
	OFFLINE;
	
	
	public static Optional<Status> findStatus(String status) {
		
		return Stream.of(Status.values()).filter( s -> status.equals(s.name())).findAny();
		
		
	}
}
