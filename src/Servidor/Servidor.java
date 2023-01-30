package Servidor;

import java.net.*;
import EscribeYEscucha.Espejo;
import java.io.*;


public class Servidor {
	
	public static void main(String[] args) {
		
		DatagramSocket socket;


		
		
		try {
			//Creamos el socket
			socket = new DatagramSocket(6000);
			Espejo paraTodos= new Espejo(socket);
			paraTodos.start();
	
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
	}
	

}
