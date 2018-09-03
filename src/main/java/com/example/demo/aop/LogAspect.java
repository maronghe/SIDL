package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

	// 创建日志对象
	private Logger logger = Logger.getLogger(getClass());

	@Pointcut("execution(public * com.example.demo.controller.*.*(..))") // 切点 扫描 com.example.demo.controller包下面的所有函数
	public void webLog(){}

	/**
	 *  请求值之前
	 * @param joinPoint
	 */
	@Before("webLog()")
	public void before(JoinPoint joinPoint){
		// 接收请求，记录内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录请求内容
		logger.info("URL:" + request.getRequestURL().toString());
		logger.info("HTTP_METHOD:" + request.getMethod());
		logger.info("IP:" + request.getRemoteAddr());
		logger.info("PORT:" + request.getRemotePort());
		logger.info("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName()+ "."
		+ joinPoint.getSignature().getName());
		logger.info("ARGS：" + Arrays.toString(joinPoint.getArgs()));
	}

	/**
	 *  返回结果执行
	 * @param ret
	 */
	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret){
		logger.info("RESPONSE : " + ret);
	}

	/**
	 *   发生异常
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing( pointcut = "webLog()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e){
		logger.info("Exception : " + e.getMessage());
	}

}
