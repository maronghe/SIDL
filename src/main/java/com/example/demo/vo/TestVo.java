package com.example.demo.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class TestVo {

	@Max(value = 5, message = "不能超过5")
	@Min(value = 1, message = "不能小于1")
	private String id;
	private String name;

	public TestVo() {
	}

	public TestVo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
