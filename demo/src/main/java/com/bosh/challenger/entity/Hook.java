package com.bosh.challenger.entity;

import org.springframework.data.annotation.Id;

import com.bosh.challenger.entity.enumeration.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hook {

	@Id	
	private Long id;

	private String name;

	private String description;

	private Status status;
}
