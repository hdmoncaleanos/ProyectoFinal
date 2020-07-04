package com.sistema.principal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class Propiedades {
	
	static String result = "";
	static Properties properties = null;
	
	private static boolean ISDEBUG = false;
	
	public static void cargarPropiedades() throws IOException{
		
		String project_path = System.getProperty("user.dir");
		String properties_path = project_path + "/config/config.properties";
		System.out.println("Cargando la configuracion del archivo " + properties_path);
		File archivo = new File(properties_path);
		properties = new Properties();

		FileInputStream stream = new FileInputStream(archivo);
		properties.load(stream);
		
		ISDEBUG = Boolean.parseBoolean(obtenerPropiedad("debug"));
		if(ISDEBUG){
			String log_file_path = project_path + "/log/Simulacion" + System.currentTimeMillis() + ".log";
			File file = new File(log_file_path);
			System.out.println("Ahora el archivo "+file.getAbsolutePath()+" sera la consola.");
			PrintStream printStream = new PrintStream(file);
			System.setOut(printStream);
			
		}
		stream.close();
	}
	
	public static String obtenerPropiedad(String propiedad){
		String valor = StringUtils.trimToNull(properties.getProperty(propiedad));
		System.out.println("El valor para la propiedad \"" + propiedad + "\" es \"" + valor + "\"");
		return valor;
	}
	
}
