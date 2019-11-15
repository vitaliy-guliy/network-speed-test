import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService {
	
	public void go() throws IOException {
		ServerSocket uploadServerSocket = new ServerSocket(9001);
		ServerSocket downloadServerSocket = new ServerSocket(9002);
		
		
		new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						Socket socket = uploadServerSocket.accept();
						new UploadTestAgent(socket).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						Socket socket = downloadServerSocket.accept();
						new DownloadTestAgent(socket).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		System.out.println("Test UPLOAD at 9001...");
		System.out.println("Test DOWNLOAD at 9002...");		

		try {
			while (true) {
				if (System.in.read() == 'x') {
					break;
				}

				Thread.sleep(100);
			}
		} catch (Exception e) {
			System.out.println("Unable to accept client. " + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			uploadServerSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			downloadServerSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class UploadTestAgent extends Thread {

		private Socket socket;
		
		public UploadTestAgent(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			System.out.println("Upload Agent STARTED");
			
			byte []buff = new byte[1024];

			try {
				while (true) {
					if (socket.getInputStream().read(buff) < 0) {
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Upload Agent STOPPED");
		}
		
	}
	
	private class DownloadTestAgent extends Thread {

		private Socket socket;
		
		public DownloadTestAgent(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			System.out.println("DownloadTestAgent STARTED");

			byte []buff = new byte[1024];
			
			try {
				while (true) {
					socket.getOutputStream().write(buff);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("DownloadTestAgent STOPPED");			
		}
	};

	

	public static void main(String[] args) {
		System.out.println("ServerService");
		System.out.println();
		
		try {
			new ServerService().go();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("ServerService stopped");
	}

}
