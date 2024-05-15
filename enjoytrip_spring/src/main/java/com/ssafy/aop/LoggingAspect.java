package com.ssafy.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Component
@Aspect
public class LoggingAspect {

//	@Before(value = "execution(* com.ssafy.board.model.mapper.Board*.*(..))")
//	public void loggin(JoinPoint joinPoint) {
//		log.debug("before call method : {} ", joinPoint.getSignature());
//		log.debug("메서드 선언부 : {} 전달 파라미터 : {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
//	}

	@Around(value = "execution(* com.ssafy.board.model.mapper.Board*.*(..))")
	public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object proceed = joinPoint.proceed();

		stopWatch.stop();

		return proceed;
	}

//	@AfterReturning(value = "execution(* com.ssafy.board.model.mapper.Board*.list*(..))", returning = "obj")
//	public void afterReturningMethod(JoinPoint joinPoint, Object obj) {
//		log.debug("afterReturning call method : {} ", joinPoint.getSignature());
//		log.debug("return value : {}", obj);
//	}
//
//	@AfterThrowing(value = "execution(* com.ssafy.board.model..Board*.list*(..))", throwing = "exception")
//	public void afterThrowingMethod(JoinPoint joinPoint, Exception exception) {
//		log.debug("afterThrowing call method : {}", joinPoint.getSignature());
//		log.debug("exception : {}", exception);
//	}
//
//	@After(value = "execution(* com.ssafy.board.model..Board*.list*(..))")
//	public void afterMethod(JoinPoint joinPoint) {
//		log.debug("after call method : {}", joinPoint.getSignature());
//	}

}