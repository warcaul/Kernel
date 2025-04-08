package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class DogTest {
    Dog buddy, belle, boda, badDog;
    Owner siobhan, mairead;
    @BeforeEach
    void setUp() {
        siobhan = new Owner(555, "Siobhan", "051302000");
        mairead = new Owner(235, "Mairead", "999");
        buddy = new Dog(4444, "buddy", "bigdoggie", true, 12, 'm', false,siobhan);
        boda = new Dog(0, "12345678901234567890TOOLONG", "don'tcare", false, -2, '?', false,mairead);
    }

    @Nested
    class Constructors {
        @Test
        void testConstructor() {
            assertEquals(4444, buddy.getId());
            assertEquals("buddy", buddy.getName());
            assertEquals("bigdoggie", buddy.getBreed());
            assertTrue(buddy.isDangerousBreed());
            assertEquals(12, buddy.getAge());
            assertEquals('M', buddy.getSex());
            assertFalse(buddy.isNeutered());
            assertEquals(siobhan, buddy.getOwners().get(0));
            assertFalse(buddy.getDaysInKennel()[0]);
            // now for boda
            assertEquals(1000, boda.getId()); // default
            assertEquals("12345678901234567890", boda.getName()); // cut to 20
            assertEquals("don'tcare", boda.getBreed());
            assertFalse(boda.isDangerousBreed());
            assertEquals(5, boda.getAge());
            assertEquals('F', boda.getSex());
            assertFalse(boda.isNeutered());

            assertEquals(mairead, boda.getOwners().get(0));
        }
    }
    @Nested
    class Setters {
        @Test
        void setDangerousBreed() {
            assertTrue(buddy.isDangerousBreed());
            buddy.setDangerousBreed(true);
            assertTrue(buddy.isDangerousBreed());
            buddy.setDangerousBreed(false);
            assertFalse(buddy.isDangerousBreed());
            buddy.setDangerousBreed(true);
            assertTrue(buddy.isDangerousBreed());

        }

        @Test
        void setAge() {   // test valid range >= 0 and <= 20
            assertEquals(12, buddy.getAge());
            buddy.setAge(-1); // should remain unchanged
            assertEquals(12, buddy.getAge());
            buddy.setAge(0);
            assertEquals(0, buddy.getAge());
            buddy.setAge(19);
            assertEquals(19, buddy.getAge());
            buddy.setAge(20);
            assertEquals(20, buddy.getAge());
            buddy.setAge(21); // should remain unchanged
            assertEquals(20, buddy.getAge());
        }

        @Test
        void setSex() {

            assertEquals('M', buddy.getSex());
            buddy.setSex('f');
            assertEquals('F', buddy.getSex());
            buddy.setSex('m');
            assertEquals('M', buddy.getSex());
            buddy.setSex('F');
            assertEquals('F', buddy.getSex());
            buddy.setSex('M');
            assertEquals('M', buddy.getSex());
            buddy.setSex('?');
            assertEquals('M', buddy.getSex());  // should remain unchanged

        }

        @Test
        void setNeutered() {
            assertFalse(buddy.isNeutered());
            buddy.setNeutered(true);
            assertTrue(buddy.isNeutered());
            buddy.setNeutered(false);
            assertFalse(buddy.isNeutered());
        }

        @Test
        void initName() {

            belle = new Dog(2334, "12345678901234567890AFTERTHISREMOVE", "ff", true, 12, 'm', false, siobhan);
            assertEquals("12345678901234567890", belle.getName());
            badDog = new Dog(2334, "1234567890123", "ff", true, 12, 'm', false, siobhan);
            assertEquals("1234567890123", badDog.getName());
        }

        @Test
        void setName() {
            assertEquals("buddy", buddy.getName());
            buddy.setName("1234567890123456789");//19 chars
            assertEquals("1234567890123456789", buddy.getName());
            buddy.setName("12345678901234567890");//20 chars
            assertEquals("12345678901234567890", buddy.getName());
            buddy.setName("XXX456789012345678901"); // 21 chars so should stay the same
            assertEquals("12345678901234567890", buddy.getName());

        }

        @Test
        void setId() { // test range from 1000 to 9999
            assertEquals(4444, buddy.getId());
            buddy.setId(999); //
            assertEquals(4444, buddy.getId());
            buddy.setId(1000);
            assertEquals(1000, buddy.getId());
            buddy.setId(1001);
            assertEquals(1001, buddy.getId());
            buddy.setId(5000);
            assertEquals(5000, buddy.getId());
            buddy.setId(9999);
            assertEquals(9999, buddy.getId());
            buddy.setId(10000); // outside limit - remain unchanged
            assertEquals(9999, buddy.getId());
            buddy.setId(5000);
            buddy.setId(59876);// way outside - remain at 5000
            assertEquals(5000, buddy.getId());
        }
        @Test
        void setBreed() {
            assertEquals("bigdoggie", buddy.getBreed());
            buddy.setBreed("bigDoggie");
            assertEquals("bigDoggie", buddy.getBreed());

        }
        @Test
        void setDaysInKennel() {
            boolean[] trueBools = {true, true, true, true, true};
            boolean[] falseBools = {false, false, false, false, false};
//            6 members in array are not supposed to be added. Looks like last true was a typo mistake, so I remove it
//            On method itself there is validation that allows only array with length = 5
            boolean[] mixedBools = {true, true, true, false, true};
            for (int i = 0; i < buddy.getDaysInKennel().length; i++) {
                assertFalse(buddy.getDaysInKennel()[i]);
            }
            buddy.setDaysInKennel(trueBools);
            for (int i = 0; i < buddy.getDaysInKennel().length; i++) {
                assertTrue(buddy.getDaysInKennel()[i]);
            }

            buddy.setDaysInKennel(mixedBools);
            for (int i = 0; i < mixedBools.length; i++) {
                if (i == 3) assertFalse(buddy.getDaysInKennel()[i]);
                else assertTrue(buddy.getDaysInKennel()[i]);
            }
        }
    }

    @Nested
    class Getters {
        @Test
        void testSetDaysInKennelByIndex() {
            boolean[] trueBools = {true, true, true, true, true};
            assertFalse(buddy.getDaysInKennel()[0]);
            buddy.setDaysInKennel(0, true);
            assertTrue(buddy.getDaysInKennel()[0]);

            for (int i = 1; i < buddy.getDaysInKennel().length; i++) {
                assertFalse(buddy.getDaysInKennel()[i]);
            }
            buddy.setDaysInKennel(0, false);
            for (int i = 0; i < buddy.getDaysInKennel().length; i++) {
                assertFalse(buddy.getDaysInKennel()[i]);
            }

        }


        @Test
        void getDaysInKennelByIndex() {
            boolean[] mixedBools = {true, true, false, true, true};
            buddy.setDaysInKennel(mixedBools);
            assertTrue(buddy.getDaysInKennel()[0]);
            assertTrue(buddy.getDaysInKennel()[1]);
            assertFalse(buddy.getDaysInKennel()[2]);
            assertTrue(buddy.getDaysInKennel()[3]);
            assertTrue(buddy.getDaysInKennel()[4]);
            // test invalid (out of bounds) inputs
            buddy.setDaysInKennel(-1, true);
            assertFalse(buddy.isInKennel(-1));
            buddy.setDaysInKennel(5, true);
            assertFalse(buddy.isInKennel(5));
        }
    }
    @Nested
    class Owners{
    @Test
    void addOwnerAndGetNumOwners() {
        assertEquals(siobhan, buddy.getOwners().get(0));
        assertEquals(1, buddy.getNumOwners());
        buddy.addOwner(mairead);
        assertEquals(mairead, buddy.getOwners().get(1));
        assertEquals(2, buddy.getNumOwners());
    } }

    @Nested
    class toString{
    @Test
    void testToString() {
        String fullDog = buddy.toString();
        assertTrue(fullDog.contains("Name: "));
        assertTrue(fullDog.contains(buddy.getName()));

        assertTrue(fullDog.contains("Breed: "));
        assertTrue(fullDog.contains(buddy.getBreed()));

        assertTrue(fullDog.contains("Dangerous: Yes"));
        buddy.setDangerousBreed(false);
        fullDog = buddy.toString();
        assertTrue(fullDog.contains("Dangerous: No"));
        //reset back
        buddy.setDangerousBreed(true);
        fullDog = buddy.toString();

        assertTrue(fullDog.contains("Age: "));
        assertEquals(12, buddy.getAge());

        assertTrue(fullDog.contains("Sex: M"));
        assertTrue(fullDog.contains("Neutered: is not neutered"));
        // test other values in options
        buddy.setSex('F');
        buddy.setNeutered(true);
        fullDog = buddy.toString();
        assertTrue(fullDog.contains("Sex: F"));
        assertTrue(fullDog.contains("Neutered: is neutered"));
        buddy.setNeutered(false);
        buddy.setSex('M');
        fullDog = buddy.toString();
        // check first owner
        Owner dOwner1 = buddy.getOwners().get(0);
        assertTrue(dOwner1.toString().contains("Name:"));
        assertTrue(dOwner1.toString().contains("Siobhan"));
        assertTrue(dOwner1.toString().contains("Id:"));
        assertTrue(dOwner1.toString().contains("555"));
        // add second owner and check that details come out
        buddy.addOwner(mairead);
        Owner dOwner2 = buddy.getOwners().get(1);
        assertTrue(dOwner2.toString().contains("Name:"));
        assertTrue(dOwner2.toString().contains("Mairead"));
        assertTrue(dOwner2.toString().contains("Id:"));
        assertTrue(dOwner2.toString().contains("235"));
    }}

}