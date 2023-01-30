package EscribeYEscucha;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class Espejo extends Thread {

	DatagramSocket socket;
	private int puerto = 0;
	private InetAddress direccion1;
	private int puerto2 = 0;
	private InetAddress direccion2;

	public Espejo(DatagramSocket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		byte[] mensaje_bytes = new byte[256];
		byte[] mensajePen_bytes = new byte[256];
		String mensaje = "";
		String mensajePen;
		mensaje = new String(mensaje_bytes);
		mensajePen = new String(mensajePen_bytes);
		DatagramPacket paquete = new DatagramPacket(mensaje_bytes, 256);
		DatagramPacket envpaquete = new DatagramPacket(mensaje_bytes, 256);
		ArrayList<DatagramPacket> paquetesPendientesDeEnviar = new ArrayList();
	//	ArrayList<String> pendientes = new ArrayList();

		try {

			do {
				// Recibimos el paquete
				socket.receive(paquete);

				/*
				 * vamos a rellenar las direcciones comparando los puertos de los paquetes ya
				 * que en este caso la direccion es exactamente la misma ya que es localhost
				 */

				/*
				 * el primer cliente lanza su main, y con ello un paquete vacio. este paquete es
				 * recibido por el servidor e identifica direccion y puerto
				 */

				if (this.puerto == 0) {
					this.direccion1 = paquete.getAddress();
					this.puerto = paquete.getPort();
					// syso traza para ver direccion y puerto del cliente1
					System.out.println("Cliente 1 llega" + this.direccion1 + " " + this.puerto);

				}

				/*
				 * En este caso se conecta el cliente2, enviando su paquete vacio para que lo
				 * pueda identificar el servidor
				 */

				else if ((this.puerto2 == 0) && (paquete.getPort() != this.puerto)) {
					this.direccion2 = paquete.getAddress();
					this.puerto2 = paquete.getPort();

					// syso traza para ver que se conecta correctamente
					System.out.println("Cliente 2 llega" + this.direccion1 + " " + this.puerto2);

					/*
					 * en caso de que pendientes tenga algo acumulado, se recorreran los mensajes y
					 * se enviaran al cliente2 ( que es el que faltaba por conectarse)
					 */

					if (paquetesPendientesDeEnviar.size() > 0) {

						
						for (int i = 0; i < paquetesPendientesDeEnviar.size(); i++) {

							byte[] mensajePen2_bytes = new byte[256];
							mensajePen2_bytes = paquetesPendientesDeEnviar.get(i).getData();
							mensajePen = new String(mensajePen_bytes);

							envpaquete = new DatagramPacket(mensajePen2_bytes, mensajePen.length(), this.direccion2,
									this.puerto2);
							socket.send(envpaquete);
							mensajePen2_bytes = new byte[256];

						}

//						for (int i = 0; i < pendientes.size(); i++) {
//							// Lo formateamos
//
//							mensaje = pendientes.get(i);
//							mensaje_bytes = mensaje.getBytes();
//							envpaquete = new DatagramPacket(mensaje_bytes, mensaje.length(), this.direccion2,
//									this.puerto2);
//							// realizamos el envio
//							socket.send(envpaquete);
//							mensaje_bytes = new byte[256];
//							paquete = new DatagramPacket(mensaje_bytes, 256);
//
//						}

//						como ya estan los 2 conectados esto no lo necesitamos 
//						limpiamos para ahorrar memoria

//						pendientes.clear();

					}

				}

				/*
				 * apartir de aqui como contamos con las 2 direcciones ya redireccionariamos
				 * paquetes
				 */

				else if (this.puerto != 0 && puerto2 != 0) {

					// Lo formateamos
					mensaje = new String(mensaje_bytes).trim();
					mensaje_bytes = mensaje.getBytes();
					if (paquete.getPort() == this.puerto) {

						envpaquete = new DatagramPacket(mensaje_bytes, mensaje.length(), this.direccion2, this.puerto2);
						// realizamos el envio
						socket.send(envpaquete);

					}

					else if (paquete.getPort() == this.puerto2) {

						envpaquete = new DatagramPacket(mensaje_bytes, mensaje.length(), this.direccion1, this.puerto);
						// realizamos el envio
						socket.send(envpaquete);

					}
				}

				/*
				 * en caso de que el cliente que ya lo tengamos identificado y escriba mensajes
				 * al ciente2 que aun no esta conectado, dichos mensajes los vamos acumulando en
				 * un arrayList de Strings pendientes
				 */

				else if (puerto != 0 && puerto2 == 0) {

					mensaje = new String(mensaje_bytes).trim();
					mensaje_bytes = mensaje.getBytes();
					// pendientes.add(mensaje);
					paquetesPendientesDeEnviar.add(paquete);

	

				}

				// limpiamos
				mensaje_bytes = new byte[256];
				paquete = new DatagramPacket(mensaje_bytes, 256);

			} while (true);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
