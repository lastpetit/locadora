package br.sicofi.locadora.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.sicofi.locadora.model.Genero;

public class GeneroConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {

			Long id = Long.parseLong(arg2);
			 
			Genero genero = new Genero();
			genero.setId(id);
			
			return genero;

		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Genero) {
			Genero genero = (Genero) arg2;
			return genero.getId().toString();
		}
		return null;
	}

}
