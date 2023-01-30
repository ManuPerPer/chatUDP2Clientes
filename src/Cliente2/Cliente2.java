package Cliente2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import EscribeYEscucha.Escritura;
import EscribeYEscucha.Escucha;

public class Cliente2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Definimos el sockets
		DatagramSocket socket;
		InetAddress address;
		socket = new DatagramSocket();
		address=InetAddress.getByName("localhost");
		
		
		/*
		 * necesito enviar un primer paquete vacio para que establezca una conexion 
		 * y el servidor recoga direccion y puerto
		 */
			
			byte[] mensaje_bytes = new byte[256];
			String mensaje=" ";
			mensaje_bytes=mensaje.getBytes();
			DatagramPacket paquete;
			mensaje_bytes = mensaje.getBytes();
			paquete = new DatagramPacket(mensaje_bytes,mensaje.length(),address,6000);
			socket.send(paquete);
		
	
		
		//hilo de escritura tiene que pasar el socket y el addres
		Escritura envio= new Escritura(socket, address,2);
		Escucha recibe= new Escucha(socket);
		envio.start();
		recibe.start();

	}

}
