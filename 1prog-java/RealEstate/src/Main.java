import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
	public static void main(String[] args) {
		//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
		// to see how IntelliJ IDEA suggests fixing it.
		//System.out.println(System.getProperty("user.dir")); // Shows current directory
		ArrayList<Ad> ads = Ad.LoadFromCsv("src/realestates.csv");
		//System.out.println(ads);
	}
}
