package com.toy.kh.ToDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CycleList {
	private int id;
	private int number;
	private String contents;
	private int success;
}
