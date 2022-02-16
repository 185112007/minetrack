package tr.com.minetrack.app.helpers;

public class XorTest433Tag {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "17 06 12 62 18 02 08 00 0d f4 80 00 ff 1f de";
		s = "17 06 12 62 18 02 08 00 0d f4 80 00 ff 1e dd";
		String[] ss = s.split(" ");
		
		int[] arr = new int[ss.length];
		for (int i = 0; i < ss.length; i++) {
			arr[i] = Integer.parseInt(ss[i],16);
		}
		int result = 0;
		for (int i = 0; i < 14; i++) {
			result = result ^ arr[i];
		}
		
		int checkcode = arr[14];
		
		if (checkcode == result) {
			System.out.println("ok");
		}
	}

}
