package com.dawson.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  WorkerNodeType {

	/**
	 * 容器
	 * */
	CONTAINER(1,"容器"),
	/**
	 * 实体机器
	 * */
	ACTUAL(2,"实体机器");



	/**
	 * jackson 反序列化
	 * */
	@JsonCreator
	public static WorkerNodeType getItem(int code) {
		for (WorkerNodeType item : values()) {
			if (item.getValue() == code) {
				return item;
			}
		}
		return null;
	}


	@JsonValue
	public int getValue() {
		return value;
	}

//	@Enum
	private int value;
	private String desc;
}
