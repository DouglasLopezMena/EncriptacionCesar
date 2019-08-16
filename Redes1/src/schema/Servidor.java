package schema;



import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.io.*;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread miHilo = new Thread(this);
		
		miHilo.start();
		}
	
	private	JTextArea areatexto;
	
	public String clave () {
	       return (int) ((Math.random() * 20) + 1) + "";
		}
	
	public String convertHexadecimal(int key) {
		
		return Integer.toHexString(key);
	}

	@Override
	public void run() {
		
		try {
			
			String key = clave();
			
			System.out.println(key);
			
			ServerSocket servidor = new ServerSocket(9999);
			
			String ip, mensaje, clave;
			
			Paquete paquete_recibido;
			
			while(true) {
			
			  Socket miSocket = servidor.accept();
			
			  DataOutputStream flujo_salida = new DataOutputStream(miSocket.getOutputStream());
			  DataInputStream flujo_entrada = new DataInputStream(miSocket.getInputStream());
			  
			  flujo_salida.writeUTF(key); //Envia la clave a los clientes.
			
			 //Para sacar y recibir paquetes de objetos
			  
			  ObjectInputStream entrada_objeto = new ObjectInputStream(miSocket.getInputStream());
			 
			  
			  paquete_recibido = (Paquete) entrada_objeto.readObject();
					  
			  ip = paquete_recibido.ip;
			  mensaje = paquete_recibido.mensaje; 
			  clave = paquete_recibido.clave;
			  
			  
			  areatexto.append("\n"+ip+" dice: "+ mensaje + ". Enviado con la clave: "+clave);
			 
			
			  Socket envioDestinatario = new Socket(ip, 9090);
			  ObjectOutputStream salida_objeto = new ObjectOutputStream(envioDestinatario.getOutputStream());
			  salida_objeto.writeObject(paquete_recibido);
			  
			  salida_objeto.close();
			  envioDestinatario.close();
			  miSocket.close(); // Se cierra la conexión con el cliente.
			
			}
			
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
}

