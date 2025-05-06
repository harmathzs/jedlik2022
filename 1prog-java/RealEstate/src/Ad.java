import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Ad {
	public Integer Area;
	public Category Category;
	public LocalDateTime CreateAt;
	public String Description;
	public Integer Floors;
	public Boolean FreeOfCharge;
	public Integer Id;
	public String ImageUrl;
	public String LatLong;
	public Integer Rooms;
	public Seller Seller;

	public Ad(String line) {

	}

	public static ArrayList<Ad> LoadFromCsv(String fileName) {
		ArrayList<Ad> ads = new ArrayList<>();
		StringBuilder csv = new StringBuilder();
		try {
			FileReader f = new FileReader(fileName);
			int c;
			while ((c = f.read()) != -1) {
				csv.append((char) c);
			}
			f.close();

			String[] lines = csv.toString().split("\n");
			for (String line: lines) {
				ads.add(new Ad(line));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return ads;
	}
}
