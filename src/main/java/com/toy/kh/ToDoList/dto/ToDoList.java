package com.toy.kh.ToDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoList {
	private int id;
	private String doDate;
	private String classification;
	private String contents;
	private int success;
	private String reason;
	private int visible;
}
