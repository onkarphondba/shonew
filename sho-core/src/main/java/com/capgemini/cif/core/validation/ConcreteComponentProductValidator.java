package com.capgemini.cif.core.validation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.capgemini.cif.core.domain.BatchItemProcessMessage;
import com.capgemini.cif.core.exception.ExceptionHandler;
import com.capgemini.cif.core.exception.GenericCoreException;

/**
 * @author snkesarw
 *
 */
@Component
public class ConcreteComponentProductValidator {
	private static ValidatorFactory factory = null;
	private static ConcreteComponentProductValidator interfaceValidator = null;
	static Validator validator;

	private ConcreteComponentProductValidator() {
		factory = Validation.buildDefaultValidatorFactory();
	}

	public static Validator getValidator() {
		if (interfaceValidator == null) {
			interfaceValidator = new ConcreteComponentProductValidator();
		}
		return factory.getValidator();
	}

	public static List<BatchItemProcessMessage> validate(ConcreteComponentProduct product) {
		List<BatchItemProcessMessage> messages = new ArrayList<BatchItemProcessMessage>();
		initializeValidator();
		return validateProduct(product, 1, 0, messages);
	}

	private static List<BatchItemProcessMessage> validateProduct(ConcreteComponentProduct product, int level,
			int sequence, List<BatchItemProcessMessage> messages) {
		Set<ConstraintViolation<ConcreteComponentProduct>> constraintViolations = validator.validate(product);
		BatchItemProcessMessage message = getMessageFromViolations(constraintViolations, product.getFriendlyName(),
				level, ++sequence);
		if (message != null) {
			messages.add(message);
		}
		if (ConcreteCompositeProduct.class.isAssignableFrom(product.getClass())) {
			level++;
			List<ConcreteComponentProduct> products = product.getAllChildren();
			if (products != null) {
				for (int i = 0; i < products.size(); i++)
					messages = validateProduct(products.get(i), level, i, messages);
			}
		}

		return messages;
	}

	private static void initializeValidator() {
		validator = ConcreteComponentProductValidator.getValidator();
	}

	private static BatchItemProcessMessage getMessageFromViolations(
			Set<ConstraintViolation<ConcreteComponentProduct>> constraintViolations, String nameOfInterface, int level,
			int sequence) {

		StringBuilder stringBuilder = new StringBuilder();
		for (ConstraintViolation<?> cval : constraintViolations) {
			stringBuilder.append(getErrorMessage(cval, nameOfInterface));
		}
		if (stringBuilder.length() > 0) {
			return new BatchItemProcessMessage(null, null, 1, level, sequence, true, stringBuilder.toString(), null,
					null);
		}
		return null;
	}

	private static String getErrorMessage(ConstraintViolation<?> cval, String nameOfInterface) {
		Annotation annotation = cval.getConstraintDescriptor().getAnnotation();

		Class<?> className = annotation.annotationType();
		GenericCoreException exception = null;
		if (className.isAssignableFrom(Size.class)) {
			exception = ExceptionHandler.createException("validation.max_size_exceeded", nameOfInterface,
					cval.getPropertyPath().toString(), cval.getConstraintDescriptor().getAttributes().get("min"),
					cval.getConstraintDescriptor().getAttributes().get("max"));
		} else if (className.isAssignableFrom(NotNull.class)) {
			exception = ExceptionHandler.createException("validation.required_field", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(Null.class)) {
			exception = ExceptionHandler.createException("validation.null_field", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(Min.class)) {
			exception = ExceptionHandler.createException("validation.minimum_value", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(Max.class)) {
			exception = ExceptionHandler.createException("validation.maximum_value", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(Digits.class)) {
			exception = ExceptionHandler.createException("validation.only_digits", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(Pattern.class)) {
			exception = ExceptionHandler.createException("validation.valid_pattern", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(AssertTrue.class)) {
			exception = ExceptionHandler.createException("validation.should_be_true", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(AssertFalse.class)) {
			exception = ExceptionHandler.createException("validation.should_be_false", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(DecimalMax.class)) {
			exception = ExceptionHandler.createException("validation.maximum_decimal_points_allowed", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(DecimalMin.class)) {
			exception = ExceptionHandler.createException("validation.minimum_decimal_points_required", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(Past.class)) {
			exception = ExceptionHandler.createException("validation.past_date_required", nameOfInterface,
					cval.getPropertyPath().toString());
		} else if (className.isAssignableFrom(Future.class)) {
			exception = ExceptionHandler.createException("validation.future_date_required", nameOfInterface,
					cval.getPropertyPath().toString());
		}

		if (exception != null) {
			return ExceptionHandler.getExceptionMessage(exception, 1024);
		}
		return null;
	}
}
