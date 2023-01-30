package Cliente1;
import java.net.*;
import java.io.*;
import EscribeYEscucha.*;

public class Cliente1 {

	public static void main(String[] args) throws IOException {

		
		//Definimos el sockets
		DatagramSocket socket= new DatagramSocket();
		InetAddress address=InetAddress.getByName("localhost");
		
	
		
		
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
		Escritura envio= new Escritura(socket, address,1);
		Escucha recibe= new Escucha(socket);
		envio.start();
		recibe.start();
		
			
		
	}

}
