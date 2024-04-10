package com.exercise.fabrick.demo.exception;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FabrickExceptionTest {

	FabrickException fabrickException;
	
	@Test
	void fabrickExceptionTest2ParamThrowable() {
		fabrickException = new FabrickException("error","retcode", new RuntimeException());
		assertNotNull(fabrickException.getMsgRetCode());
	}
	
	@Test
	void fabrickExceptionTest2ParamString() {
		fabrickException = new FabrickException("error","retcode");
		assertEquals("error", fabrickException.getMsgRetCode());
	}
	
	@Test
	void fabrickExceptionTest3ParamList() {
		List<String> list = new ArrayList<String>();
		list.add("test");
		fabrickException = new FabrickException("test","retcode","MESSAGGIO");
		assertEquals("MESSAGGIO", fabrickException.getMsgRetCode());
	}
	
	@Test
	void fabrickExceptionTest3ParamThrowable() {
		fabrickException = new FabrickException("test","retcode",new RuntimeException());
		assertEquals("test", fabrickException.getMsgRetCode());
	}
}