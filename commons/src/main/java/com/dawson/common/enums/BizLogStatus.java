package com.dawson.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * bizlog status
 */
@Getter
@AllArgsConstructor
public enum BizLogStatus {

	/**
	 * 失败已处理
	 * */
	REDO_SUCCESS("01","失败已处理"),

	/**
	 * 成功
	 * */
	SUCCESS("10","成功"),
	/**
	 * 失败
	 * */
	FAIL("00","失败");


	/**
	 * jackson 反序列化
	 * */
	@JsonCreator
	public static BizLogStatus getItem(String code) {
		for (BizLogStatus item : values()) {
			if (item.getValue() == code) {
				return item;
			}
		}
		return null;
	}


	@JsonValue
	public String getValue() {
		return value;
	}

//	@Enum
	private String value;
	private String name;
}
