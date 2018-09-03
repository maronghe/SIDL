package com.example.demo.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

public class User {

	@Max(value = 20 ,message = "Id不能超过20")
	@Min(value = 10 ,message = "密码设定不正确")
	private String id;

	private String name;
	private List<String> hobbies;

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

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", hobbies=" + hobbies +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if ( this== o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(name, user.name) &&
				Objects.equals(hobbies, user.hobbies);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, hobbies);
	}
}
