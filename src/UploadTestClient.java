import java.net.Socket;

public class UploadTestClient {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("No host specified");
			return;
		}
		
		System.out.println("Upload to " + args[0] + "...");
		
		Socket s = new Socket(args[0], 9001);
		
		byte []buff = new byte[1024];
		
		try {
			while (true) {
				long startTime = System.currentTimeMillis();
				long kilobytes = 0;
				while (System.currentTimeMillis() - startTime < 1000) {
					s.getOutputStream().write(buff);
					kilobytes++;
				}
				
				long mbytes = kilobytes / 1024;
				long mbytesPart = kilobytes % 1024;
				
				String mb = "" + mbytesPart;
				
				System.out.println("Host: " + args[0] + "    Upload speed: " + kilobytes + " KB/Sec    " + mbytes + "." + mb.substring(0, 1) + " MB/Sec");
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			s.close();
		}
		
		System.out.println("Upload test stopped");
	}

}
