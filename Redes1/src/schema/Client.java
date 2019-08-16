package schema;



import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		
		
		}	
	
}

class LaminaMarcoCliente extends JPanel implements ActionListener, Runnable{
	
	public LaminaMarcoCliente(){
	
		nick = new JTextField(5);
		
		add(nick);
		
		JLabel texto=new JLabel("-CHAT-");
		
		add(texto);
		
		ip = new JTextField(8);
		
		add(ip);
		
        campochat = new JTextArea(12, 20);
		
		add(campochat);
		
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
		
		miboton.addActionListener(this);
		
		add(miboton);	
		
		
		Thread mihilo = new Thread(this);
	     mihilo.start();
		
	}
	
	
	
	public String encriptacionCesar(String mensajeObtenidoCliente, String clave) {
		
		String respuesta = "";
		char caracter ;
		
		if(mensajeObtenidoCliente != "") {
			for (int i = 0; i < mensajeObtenidoCliente.length(); i++) {
				caracter = mensajeObtenidoCliente.charAt(i);
				caracter = (char) (caracter + Integer.parseInt(clave));
				respuesta += Character.toString((caracter)) + "";
			}
		}
		else {
			respuesta = "::El cliente no envio texto para encriptar::";
		}
		return respuesta;
		
	}
	
	
	/**
	 * Se encarga de convertir un decimal a hexadecimal.
	 * @param abc
	 * @return
	 */
	public String decimalToHexa(String abc) {
		
		return Integer.toHexString(Integer.parseInt(abc));
		
	}
	
	
	
	
	public void actionPerformed(ActionEvent e) {
	
		Object botonPulsado = e.getSource();
		
		if(botonPulsado == miboton) {
		
	      campochat.append("\n" + "Yo: " + campo1.getText());		
			
			try {
				
				Socket miSocket = new Socket("172.30.160.137", 9999);
			
				DataInputStream flujo_entrada = new DataInputStream(miSocket.getInputStream());
			    DataOutputStream flujo_salida = new DataOutputStream(miSocket.getOutputStream());
			  
			    String claveUsada = flujo_entrada.readUTF();
			    String claveHexaDecimal = decimalToHexa(claveUsada);
			    
			    
			    System.out.println(claveUsada);
			    System.out.println(claveHexaDecimal);
			    
			    
			    String mensajeEncriptado = encriptacionCesar(campo1.getText(), claveUsada);
				Paquete envio = new Paquete(ip.getText(), mensajeEncriptado, claveHexaDecimal);
			    
			    ObjectOutputStream salida_objeto = new ObjectOutputStream(miSocket.getOutputStream());    
			
			    salida_objeto.writeObject(envio);
			    

			    flujo_salida.close();
			    flujo_entrada.close();
			    miSocket.close();
				
			} catch (UnknownHostException e1) {
				
				e1.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		
	}
		
			
		
	private JTextField campo1, nick, ip;
	
	private JButton miboton;
	
	private JTextArea campochat;

	@Override
	public void run() {
		
		try {
			
			ServerSocket servidor_cliente = new ServerSocket(9090);
			
			Socket cliente;
			
			Paquete paqueteRecibido;
			
		     while(true) {
		    	 
		    	 cliente = servidor_cliente.accept();
		    	 
		    	ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());
		    	
		    	paqueteRecibido= (Paquete)flujoentrada.readObject();
		    	
		    	
		    	campochat.append("\n"+ paqueteRecibido.ip + ": " + paqueteRecibido.mensaje);
		    	 
		     }
			
		}catch(Exception e) {
			
				System.out.println(e.getMessage());
		}
		
	}
}
