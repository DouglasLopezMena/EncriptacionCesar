package schema;

import java.io.Serializable;

public class Paquete implements Serializable{
	
	String ip;
	String mensaje;
	String clave;
	
 public Paquete(String ip, String mensaje, String clave) {
	 
	 this.ip = ip;
	 this.mensaje = mensaje;
	 this.clave = clave;
	 
	 
 }

public String getIp() {
	return ip;
}

public void setIp(String ip) {
	this.ip = ip;
}

public String getMensaje() {
	return mensaje;
}

public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
}

public String getClave() {
	return clave;
}

public void setClave(String clave) {
	this.clave = clave;
}
 
 
	

}
