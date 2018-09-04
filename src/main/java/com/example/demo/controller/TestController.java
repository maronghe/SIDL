package com.example.demo.controller;

import com.example.demo.factory.UserFactory;
import com.example.demo.vo.ReturnObj;
import com.example.demo.vo.TestVo;
import com.example.demo.vo.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

import static java.lang.System.out;

/**
 * 测试controller类
 * @author Logan
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
	private static UserFactory factory = new UserFactory();

	static {
		for (int i = 0; i < 5; i++) {
			factory.createUser(i + "");
		}
		factory.createUser("1");//
	}

	@GetMapping("/list")
	public ReturnObj getList(){
		// 定义结果对象
		ReturnObj obj = new ReturnObj();
		List<User> list = factory.list();
		obj.setList(list);
		return obj;
	}

	@PostMapping("/add/{id}")
	public User addUser(@PathVariable("id") String id){
		System.out.println("begin addUser");
		this.addRealUser(id);
		System.out.println("end addUser");
		return factory.getUser(id);
	}


	public void addRealUser(String id){
		System.out.println("------------->>>>>> start adding ");
		System.out.println("添加" + id + "User");
		factory.createUser(id);
		System.out.println("------------->>>>>> end adding ");
	}


	/**
	 *  测试参数方法
	 *  http://localhost:8989/logan/api/testMethod?id=1&name=3
	 * @param testVo
	 * @return
	 */
	@PostMapping( path = "/testMethod/{id}" )
	public TestVo testMethod(@RequestBody @Valid TestVo testVo,@PathParam("id") String id ,@PathVariable("id") String id2){ //	@RequestBody 接受 json对象
		try {
			out.println(id);
			out.println(id2);
			out.println(testVo.getId());
			out.println(testVo.getName());
			out.println(testVo);
		}finally {
//			try {
//				throw new Exception("123");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}

		return testVo;
	}
}
