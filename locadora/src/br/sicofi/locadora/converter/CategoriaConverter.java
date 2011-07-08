package br.sicofi.locadora.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.sicofi.locadora.model.Categoria;

public class CategoriaConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			Long id = Long.parseLong(arg2);
			Categoria categoria = new Categoria();
			categoria.setId(id);
			return categoria;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Categoria) {
			Categoria categoria = (Categoria) arg2;
			return categoria.getId().toString();
		}
		return null;
	}

}
