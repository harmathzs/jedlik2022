package RealEstate;

import org.junit.Test;

public class TestMain {
	@Test
	public void testMain() {
		Main.main(null);
	}

	@Test
	public void testSeller() {
		Seller seller = new Seller();
		seller.Id = 1;
		seller.Name = "Test";
		seller.Phone = "+36701234567";
	}

	@Test
	public void testCategory() {
		Category category = new Category();
		category.Id = 1;
		category.Name = "test";
	}
}
