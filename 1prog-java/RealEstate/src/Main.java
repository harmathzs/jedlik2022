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

		// 8. feladat:
		String mesevarLatLong = "47.4164220114023,19.066342425796986";
		Ad minDAd = null;
		double minD = Double.MAX_VALUE;
		for (Ad ad: ads) {
			if (ad.FreeOfCharge && ad.DistanceTo(mesevarLatLong)<minD) {
				minDAd = ad;
				minD = ad.DistanceTo(mesevarLatLong);
			}
		}
		System.out.println("2. Mesevár óvodához légvonalban legközelebbi tehermentes ingatlan adatai: ");
		if (minDAd!=null) {
			System.out.println("\t\tEladó neve     : " + minDAd.Seller.Name);
			System.out.println("\t\tEladó telefonja: " + minDAd.Seller.Phone);
			System.out.println("\t\tAlapterület    : " + minDAd.Area);
			System.out.println("\t\tSzobák száma   : " + minDAd.Rooms);
		}
	}
}
