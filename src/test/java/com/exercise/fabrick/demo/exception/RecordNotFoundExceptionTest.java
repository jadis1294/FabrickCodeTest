package com.exercise.fabrick.demo.exception;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RecordNotFoundExceptionTest {

		RecordNotFoundException recordNotFoundException;
		
		@Test
		void recordNotFoundExceptionTest1ParamString() {
			recordNotFoundException = new RecordNotFoundException("error");
			assertNotNull(recordNotFoundException.getMsgRetCode());
		}
		
		@Test
		void recordNotFoundExceptionTest2ParamThrowable() {
			recordNotFoundException = new RecordNotFoundException("error",new RuntimeException());
			assertNotNull(recordNotFoundException.getMsgRetCode());
		}
		
		@Test
		void recordNotFoundExceptionTest2ParamString() {
			recordNotFoundException = new RecordNotFoundException("error","retcode");
			assertEquals("retcode", recordNotFoundException.getMsgRetCode());
		}

		@Test
		void recordNotFoundExceptionTest1SetGetMsgRetCode() {
			recordNotFoundException = new RecordNotFoundException("test");
			String expected = "retcode";
			recordNotFoundException.setMsgRetCode(expected);
			assertEquals(expected, recordNotFoundException.getMsgRetCode());
		}
}
