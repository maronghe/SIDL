package com.example.demo.factory;

import com.example.demo.vo.User;
import static java.lang.System.out;

import java.util.*;

/**
 * User Factory
 * @Author Logan
 */
public class UserFactory {

	// 定义容器
	private Map<String, User> map = new HashMap<>();

	/**
	 * 《享元模式》
	 * 根据id创建一个用户
	 * @param id
	 */
	public void createUser(String id){
		if ( !map.containsKey(id) ){
			User user = new User();
			// 添加爱好
			user.setHobbies(Arrays.asList("FootBall" + id, "Golf" + id));
			user.setId(id + "");
			user.setName("Logan" + id);
			map.put(id, user);
		}else {
			out.println("已经存在改用户Id" + id + "，添加失败");
		}

	}

	/**
	 *  返回所有User
	 * @return list
	 */
	public List<User> list(){
		List<User> list = new ArrayList<>();
		for(Map.Entry<String, User> entry : map.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}

	/**
	 *  获取指定ID的用户
	 * @param id
	 * @return
	 */
	public User getUser(String id){
		if(map.keySet().contains(id)){
			return map.get(id);
		}
		return null;
	}
}
