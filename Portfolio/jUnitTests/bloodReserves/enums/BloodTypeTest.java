package bloodReserves.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BloodTypeTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetBloodTypeName() {
		BloodType testedType = BloodType.A_RH_N;
		assertEquals("A Rh-", testedType.getBloodTypeName());
	}

}
