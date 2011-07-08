package br.sicofi.locadora.validator;

import org.xseam.validation.CNPJValidator;
import org.xseam.validation.CPFValidator;

public class CpfValidator extends AbstractHibernateStringValidator<CpfValida>{

	@SuppressWarnings("unused")
	private String removeChar(String value){
		String r = "";
		int i;
		for (i = 0; i < value.length(); i++) {
			char c = value.charAt(i); 
			
			if (c != '.' && c != '-' && c!='/')
				r += c;
		}
		return r;
	}
	
	@Override
	public boolean isValid(Object value) {
		
		return validate(value.toString());
	}
	
	@Override
	protected boolean validate(String value) {
		boolean valido = false;
		
		if (value.length() <=14) {
			if(value.equalsIgnoreCase("000.000.000-00")||
			   value.equalsIgnoreCase("111.111.111-11")||
			   value.equalsIgnoreCase("222.222.222-22")||
			   value.equalsIgnoreCase("333.333.333-33")||
			   value.equalsIgnoreCase("444.444.444-44")||
			   value.equalsIgnoreCase("555.555.555-55")||
			   value.equalsIgnoreCase("666.666.666-66")||
			   value.equalsIgnoreCase("777.777.777-77")||
			   value.equalsIgnoreCase("888.888.888-88")||
			   value.equalsIgnoreCase("999.999.999-99")){
				return false;
			}
			CPFValidator cpfValidator=new CPFValidator();
			cpfValidator.initialize(null);
			valido=cpfValidator.validate(value.toString());
		} else {
			CNPJValidator cnpjValidator=new CNPJValidator();
			cnpjValidator.initialize(null);
			valido=cnpjValidator.validate(value.toString());
		}
		return valido;
	}
	

		
}