import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Ad {
	public int Area;
	public Category Category;
	public LocalDate CreateAt;
	public String Description;
	public int Floors;
	public boolean FreeOfCharge;
	public int Id;
	public String ImageUrl;
	public String LatLong;
	public int Rooms;
	public Seller Seller;

	public Ad(String line) {
		String[] fieldValues = line.split(";");

		this.Id = Integer.parseInt(fieldValues[0]);
		this.Rooms = Integer.parseInt(fieldValues[1]);
		this.LatLong = fieldValues[2];
		this.Floors = Integer.parseInt(fieldValues[3]);
		this.Area = Integer.parseInt(fieldValues[4]);
		this.Description = fieldValues[5];
		this.FreeOfCharge = Boolean.parseBoolean(fieldValues[6]);
		this.ImageUrl = fieldValues[7];
		this.CreateAt = LocalDate.parse(fieldValues[8]);

		this.Seller = new Seller();
		this.Seller.Id = Integer.parseInt(fieldValues[9]);
		this.Seller.Name = fieldValues[10];
		this.Seller.Phone = fieldValues[11];

		this.Category = new Category();
		this.Category.Id = Integer.parseInt(fieldValues[12]);
		this.Category.Name = fieldValues[13];
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
			for (int i=1; i<lines.length; i++) {
				String line = lines[i];
				ads.add(new Ad(line));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return ads;
	}
}
