package com.capgemini.cif.core.exception;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author apkadam
 *
 */
@Ignore
public class ExceptionHandlerTest
{
	@Test
	public void testCreateException() {
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertNotNull(genericCoreException);
	}
	
	@Test
	public void testGetExceptionMessage() {
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertNotNull(ExceptionHandler.getExceptionMessage(genericCoreException, 1024));
	}
	
	@Test
	public void testGetExceptionMessageNegativeLength() {
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertNotNull(ExceptionHandler.getExceptionMessage(genericCoreException, -1));
	}
	
	@Test
	public void testGetDisplayMessage() {
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertEquals("Exception Code :1003; Validation Error : Interface : testInterface : Field: testField should be null or empty !", ExceptionHandler.getDisplayMessage(genericCoreException));
	}
	
	@Test
	public void testGetExceptionMessageNullLength() {
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertEquals("Exception Code :1003; Validation Error : Interface : testInterface : Field: testField should be null or empty !", ExceptionHandler.getExceptionMessage(genericCoreException, -1));
	}

	
	@Test
	public void testRootCauseContains(){
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertEquals(true, ExceptionHandler.rootCauseContains(genericCoreException, "testInterface"));
	}
	
	@Test
	public void testRootCauseContainsNullPattern(){
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertEquals(false,ExceptionHandler.rootCauseContains(genericCoreException, null));
	}
	
	@Test
	public void testRootCauseContainsTwoPattern(){
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertEquals(true,ExceptionHandler.rootCauseContains(genericCoreException, "testInterface", "testField"));
	}
	
	@Test
	public void testRootCauseContainsTwoPatternSecondNull(){
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertEquals(false,ExceptionHandler.rootCauseContains(genericCoreException, null, "testField"));
	}
	
	@Test
	public void testRootCauseContainsTwoPatternFirstNull(){
		GenericCoreException genericCoreException = ExceptionHandler.createException("validation.null_field", "testInterface",
				"testField");
		Assert.assertEquals(false,ExceptionHandler.rootCauseContains(genericCoreException, "testInterface", null));
	}
	
	@Test
	public void testExternalIdMissingBothNull(){
		Assert.assertEquals(false,ExceptionHandler.externalIdMissing(null, null));
	}
	
	@Test
	public void testExternalIdMissingFirstNull(){
		Assert.assertEquals(false,ExceptionHandler.externalIdMissing(null, null));
	}
	
	@Test
	public void testExternalIdMissingSecondNull(){
		Assert.assertEquals(false,ExceptionHandler.externalIdMissing(null, null));
	}
	
	@Test
	public void testExternalIdMissing(){
		Assert.assertEquals(true,ExceptionHandler.externalIdMissing("testInterface", "testInterface"));
	}
	
	@Test
	public void testExternalIdMissingFalse(){
		Assert.assertEquals(false,ExceptionHandler.externalIdMissing("testInterface", "test"));
	}
	
	@Test
	public void testGetResources(){
		ExceptionHandler eh = new ExceptionHandler();
		Assert.assertNotNull(eh.getResources());
	}
}