/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.agesic.certificados.web.utils;

/**
 *
 * @author Bruno
 */
public class Errors {
	
	
	public static final String GENERAL_CAMPOS_OBLIGATORIOS = "De ingresar todos los campos obligatorios (*)";
	public static final String GENERAL_UNEXPECTED_ERROR = "Ocurrió un error inesperado. Por favor, comuníquese con soporte.";
	public static final String GENERAL_SUCCESS = "Operación realizada con éxito";
	
	public static final String USUARIO_CODE_UNIQUE = "Ya existe un usuario con el código ingresado";
	public static final String USUARIO_CODE_SPACES = "El código del usuario no puede contener espacios";
	public static final String USUARIO_NRO_DOC_INVALID = "La cédula de identidad no es válida.";
	public static final String USUARIO_EMAIL_INVALID = "La dirección de email no es válida.";
	public static final String USUARIO_PASS_CONFIRM_INVALID = "Las contraseñas ingresadas no coinciden.";
	
	public static final String ROL_CODE_UNIQUE = "Ya existe un rol con el código ingresado";
	public static final String ROL_CODE_SPACES = "El código del rol no puede contener espacios";
	
}
