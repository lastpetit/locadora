package br.sicofi.locadora.validator;

import java.lang.annotation.Annotation;

import org.hibernate.validator.Validator;

public abstract class AbstractHibernateStringValidator<A extends Annotation>
		implements Validator<A> {

	public void initialize(A Annotation) {
	}

	public boolean isValid(Object value) {
		if (isEmpty(value)) {
			return true;
		}

		if (!(value instanceof String)) {
			return false;
		}

		return validate(value.toString());
	}

	private boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}

		if (value instanceof String && (value.toString()).trim().length() == 0) {
			return true;
		}

		return false;
	}

	protected abstract boolean validate(String value);
}

