package EscribeYEscucha;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Escritura extends Thread {


	static InetAddress address;
	static DatagramSocket socket;
	private int numCliente;

	public static InetAddress getAddress() {
		return address;
	}

	public static void setAddress(InetAddress address) {
		Escritura.address = address;
	}
	
	public Escritura( DatagramSocket socket,  InetAddress address, int numero) {
		super();
		this.socket= socket;
		this.address= address;
		this.numCliente=numero;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		//para  enviar mensajesescritura
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		byte[] mensaje_bytes = new byte[256];
		String mensaje="";
		mensaje_bytes=mensaje.getBytes();
		//Paquete

		DatagramPacket paquete;
		
		try {
			do {
				
				mensaje = in.readLine();
				mensaje= "Cliente "+this.numCliente+" dice: "+ mensaje;
				
				mensaje_bytes = mensaje.getBytes();
				
				paquete = new DatagramPacket(mensaje_bytes,mensaje.length(),address,6000);
				
				socket.send(paquete);
				
				
				
			}while(true);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	




}