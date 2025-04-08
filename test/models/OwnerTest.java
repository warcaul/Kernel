package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    private Owner dOwner1, dOwner2, dOwnerInVal;

    @BeforeEach
    void setUp() {
        dOwner1 = new Owner(555, "Siobhan", "051302000");
        dOwnerInVal = new Owner(99, "123456789012345678901234567890TOOLONGAFTERTHIS", "Emergency999");
    }

    @Nested
    class ConstructorsAndGetters {
    @Test
    void testConstructor() {
        assertEquals(555, dOwner1.getId());
        assertEquals("Siobhan", dOwner1.getName());
        assertEquals("051302000", dOwner1.getPhoneNumber());
        assertEquals(100, dOwnerInVal.getId());
        assertEquals("123456789012345678901234567890", dOwnerInVal.getName());
        assertEquals("087302000", dOwnerInVal.getPhoneNumber());
    }
}
    @Nested
    class Setters {
        @Test
        void testSetId() {
            assertEquals(555, dOwner1.getId());
            dOwner1.setId(99);  //invalid - should remain unchanged
            assertEquals(555, dOwner1.getId());
            dOwner1.setId(100); //valid
            assertEquals(100, dOwnerInVal.getId());
            dOwner1.setId(101); //within limit
            assertEquals(101, dOwner1.getId());
            dOwner1.setId(999);
            assertEquals(999, dOwner1.getId());
            dOwner1.setId(1000); //invalid - should remain unchanged
            assertEquals(999, dOwner1.getId());
            dOwner1.setId(1001);
            assertEquals(999, dOwner1.getId());
        }

        @Test
        void testSetName() {
            assertEquals("Siobhan", dOwner1.getName());
            dOwner1.setName("12345678901234567890");  //just enough
            assertEquals("12345678901234567890", dOwner1.getName());
            dOwner1.setName("XXX456789012345678901234567890XXX"); // too long
            assertEquals("12345678901234567890", dOwner1.getName());
        }

        @Test
        void testSetPhoneNumber() {
            assertEquals("051302000", dOwner1.getPhoneNumber());
            dOwner1.setPhoneNumber("1234567890");
            assertEquals("1234567890", dOwner1.getPhoneNumber());
            dOwner1.setPhoneNumber("error");
            assertEquals("1234567890", dOwner1.getPhoneNumber());
        }
    }
    @Nested
    class toString {
        @Test
        void testToString() {

            assertTrue(dOwner1.toString().contains("Siobhan"));

            assertTrue(dOwner1.toString().contains("555"));
        }
    }
}
