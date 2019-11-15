import java.net.Socket;

public class DownloadTestClient {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("No host specified");
			return;
		}
		
		System.out.println("Download from " + args[0] + "...");
		
		Socket s = new Socket(args[0], 9002);
		
		byte []buff = new byte[1024];
		
		try {
			boolean testing = true;
			while (testing) {
				long startTime = System.currentTimeMillis();
				long readedKilobytes = 0;
				
				while (System.currentTimeMillis() - startTime < 1000) {
					int readed = s.getInputStream().read(buff);
					if (readed < 0) {
						testing = false;
						break;
					}

					readedKilobytes += readed;
				}
				
				long mbytes = readedKilobytes / 1024;
				long mbytesPart = readedKilobytes % 1024;
				
				String mb = "" + mbytesPart;
				
				System.out.println("Host: " + args[0] + "    Download speed: " + readedKilobytes + " KB/Sec    " + mbytes + "." + mb.substring(0, 1) + " MB/Sec");
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			s.close();
		}
		
		System.out.println("Download test stopped");
	}

}
