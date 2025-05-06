import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		ArrayList<Ad> ads = Ad.LoadFromCsv("src/realestates.csv");

		// 6. feladat:
		double sumOfFloor0areas = 0.00;
		int countOfFloor0areas = 0;
		for (Ad ad: ads) {
			if (ad.Floors<1) {
				countOfFloor0areas++;
				sumOfFloor0areas += ad.Area;
			}
		}
		double avgOfFloor0areas = sumOfFloor0areas / countOfFloor0areas;
		System.out.print("1. Földszinti ingatlanok átlagos alapterülete: ");
		System.out.print(Math.round(100.00*avgOfFloor0areas)/100.00);
		System.out.println(" m2");

	}
}
