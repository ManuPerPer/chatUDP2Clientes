package EscribeYEscucha;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Escucha extends Thread{
	
	static DatagramSocket socket;
	
	public Escucha(DatagramSocket socket) {
		super();
		
		this.socket= socket;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		//para escucha
		DatagramPacket servPaquete;
		byte[] RecogerServidor_bytes = new byte[256];
		String cadenaMensaje="";
		try {
			
		do {
			RecogerServidor_bytes = new byte[256];
			servPaquete = new DatagramPacket(RecogerServidor_bytes, 256);
			socket.receive(servPaquete);
			//Convertimos el mensaje recibido en un string

			cadenaMensaje = new String(RecogerServidor_bytes).trim();

			//Imprimimos el paquete recibido
			System.out.println(cadenaMensaje);
		}while(true);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
	}
	

}
