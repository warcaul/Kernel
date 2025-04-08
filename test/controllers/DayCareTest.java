package controllers;

import models.Dog;
import models.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DayCareTest {
    private DayCare dayCare, nameTooLong, tooManyDogs, popDayCare;
    private Dog dog1, dog2, dog3;
    private Owner owner1, owner2, owner3, owner4;

    @BeforeEach
    void setUp() {
        dayCare = new DayCare("HappyTails", 20);
        nameTooLong = new DayCare("1234567890123", 15);
        tooManyDogs = new DayCare("12345", 101);
        owner1 = new Owner(101, "Alice", "0871234567");
        owner2 = new Owner(102, "Bob", "0877654321");
        owner3 = new Owner(103, "Carol", "0879876543");
        owner4 = new Owner(104, "I'm not registered", "1234567890");

        dog1 = new Dog(1001, "Buddy", "Labrador", false, 3, 'M', true, owner1);
        dog2 = new Dog(1002, "Max", "Beagle", false, 5, 'M', false, owner2);
        dog3 = new Dog(1003, "Bella", "Poodle", false, 2, 'F', true, owner3);

        popDayCare = new DayCare("I have three dogs", 20);
        popDayCare.addDog(dog1);
        popDayCare.addDog(dog2);
        popDayCare.addDog(dog3);

    }

    @Nested
    class ConstructorsAndGetters {
        @Test
        void testConstructor() {
            assertEquals("HappyTails", dayCare.getName());
            assertEquals(20, dayCare.getMaxNumberOfDogs());
            assertTrue(dayCare.getDogsArray().isEmpty());
            assertEquals("1234567890", nameTooLong.getName());
            assertEquals(15, nameTooLong.getMaxNumberOfDogs());
            assertEquals("12345", tooManyDogs.getName());
            assertEquals(10, tooManyDogs.getMaxNumberOfDogs());
            assertTrue(nameTooLong.getDogsArray().isEmpty());
            assertTrue(tooManyDogs.getDogsArray().isEmpty());
            assertEquals(dog1, popDayCare.getDog(0));
            assertEquals(dog2, popDayCare.getDog(1));
            assertEquals(dog3, popDayCare.getDog(2));
        }

        @Test
        void getName() {
            assertEquals("HappyTails", dayCare.getName());
            dayCare.setName("Sad");
            assertEquals("Sad", dayCare.getName());
            assertEquals("1234567890", nameTooLong.getName());
            nameTooLong.setName("12345");
        }

        @Test
        void getMaxNumberOfDogs() {
            assertEquals(20, dayCare.getMaxNumberOfDogs());
            dayCare.setMaxNumberOfDogs(21);
            assertEquals(21, dayCare.getMaxNumberOfDogs());
        }

        @Test
        void getDogsArray() {
            assertTrue(dayCare.getDogsArray().isEmpty());
            assertTrue(tooManyDogs.getDogsArray().isEmpty());
        }

    }

    @Nested
    class Setters {
        @Test
        void setMaxNumberOfDogs() {
            assertEquals(20, dayCare.getMaxNumberOfDogs());
            dayCare.setMaxNumberOfDogs(50);
            assertEquals(50, dayCare.getMaxNumberOfDogs());
            dayCare.setMaxNumberOfDogs(9);
            assertEquals(50, dayCare.getMaxNumberOfDogs());
            dayCare.setMaxNumberOfDogs(10);
            assertEquals(10, dayCare.getMaxNumberOfDogs());
            dayCare.setMaxNumberOfDogs(99);
            assertEquals(99, dayCare.getMaxNumberOfDogs());
            dayCare.setMaxNumberOfDogs(100);
            assertEquals(100, dayCare.getMaxNumberOfDogs());
            dayCare.setMaxNumberOfDogs(101);
            assertEquals(100, dayCare.getMaxNumberOfDogs());
        }

        @Test
        void setName() {
            assertEquals("HappyTails", dayCare.getName());
            dayCare.setName("Sad");
            assertEquals("Sad", dayCare.getName());
            dayCare.setName("Medium");
            assertEquals("Medium", dayCare.getName());
            dayCare.setName("12345678901234");
            assertEquals("Medium", dayCare.getName());
            dayCare.setName("12345");
            assertEquals("12345", dayCare.getName());

        }

        @Test
        void setDogsArray() {
            assertTrue(dayCare.getDogsArray().isEmpty());
            ArrayList<Dog> newDogs = new ArrayList<>();
            newDogs.add(dog1);
            newDogs.add(dog2);
            newDogs.add(dog3);
            dayCare.setDogsArray(newDogs);
            assertEquals(3, dayCare.getDogsArray().size());
            assertEquals(dog1, dayCare.getDogsArray().get(0));
            assertEquals(dog2, dayCare.getDogsArray().get(1));
            assertEquals(dog3, dayCare.getDogsArray().get(2));
            dayCare.setDogsArray(new ArrayList<Dog>());
            assertEquals(0, dayCare.getDogsArray().size());
        }
    }

    @Nested
    class CRUD { // TODO  add in tests for add, read, update

        @Test
        void addDog() {
            // check that the adds are working as expected.
            dayCare.addDog(dog1);
            assertEquals(1, dayCare.getDogsArray().size());
            assertEquals(dog1, dayCare.getDogsArray().get(0));

            dayCare.addDog(dog2);
            assertEquals(2, dayCare.getDogsArray().size());
            assertEquals(dog2, dayCare.getDogsArray().get(1));

            dayCare.addDog(dog3);
            assertEquals(3, dayCare.getDogsArray().size());

            dayCare.addDog(dog1);
            assertEquals(4, dayCare.getDogsArray().size());
            dayCare.addDog(dog2);
            dayCare.addDog(dog3);
            assertEquals(6, dayCare.getDogsArray().size());
            dayCare.addDog(dog1);
            assertEquals(7, dayCare.getDogsArray().size());

            dayCare.addDog(dog2);
            dayCare.addDog(dog3);
            dayCare.addDog(dog1);
            assertEquals(10, dayCare.getDogsArray().size());
            //have added 10 dogs, set maxNumber of dogs to 10, then show that next add is not sucessful (limit reached)

            dayCare.setMaxNumberOfDogs(10);  // change to fill the kennel
            assertEquals(10, dayCare.getMaxNumberOfDogs());

            assertFalse(dayCare.addDog(dog1));  // kennel full so add not sucessful
            assertEquals(10, dayCare.getDogsArray().size()); // show that add did not happen
        }

        //        CRUD Methods was changed to search by id, because id must be unique identifier of objects
        //        Search buy index was removed due to inefficiency of project architecture
        @Test
        void removeDogValidID() {
            dayCare.addDog(dog1);
            assertEquals(dog1, dayCare.removeDog(1001));
            assertTrue(dayCare.getDogsArray().isEmpty());
            dayCare.addDog(dog1);
            dayCare.addDog(dog2);
            dayCare.addDog(dog3);

            assertEquals(3, dayCare.getDogsArray().size());
            assertEquals(dog1, dayCare.getDogsArray().get(0));
            assertEquals(dog2, dayCare.getDogsArray().get(1));
            assertEquals(dog3, dayCare.getDogsArray().get(2));

            assertEquals(dog1, dayCare.removeDog(1001));
            assertEquals(2, dayCare.getDogsArray().size());
            assertEquals(dog2, dayCare.getDogsArray().get(0));
            assertEquals(dog3, dayCare.getDogsArray().get(1));
            assertNull(dayCare.removeDog(-1));
            assertNull(dayCare.removeDog(2));

        }

        @Test
        void removeDogInvalidIndex() {
            assertEquals(null, dayCare.removeDog(0));// No dogs yet
            dayCare.addDog(dog1);
            dayCare.addDog(dog2);
            dayCare.addDog(dog3);
            assertEquals(3, dayCare.getDogsArray().size());
            assertNull(dayCare.removeDog(-1));
            dayCare.removeDog(2);
            assertNull(dayCare.removeDog(2));
        }


//        CRUD Methods was changed to search by id, because id must be unique identifier of objects
//        Search buy index was removed due to inefficiency of project architecture
        @Test
        void updateDogValidID() {
            dayCare.addDog(dog1);
            dayCare.updateDog(1001, dog2);
            assertEquals(dog2, dayCare.getDogsArray().get(0));
            Dog dg = new Dog(1001, "dog1234", "Br1234", true, 10, 'm',  true, owner1 );
            dayCare.addDog(new Dog(dayCare.getDogAutoID(), "dog1234", "Br1234", true, 10, 'm',  true, owner1 ));
            assertEquals(new Dog(1001, "dog1234", "Br1234", true, 10, 'm',  true, owner1 ),
                    dayCare.updateDog(1001,dg));
        }
        @Test
        void updateDogInvalidIndex() {
            assertNull(dayCare.updateDog(0, dog2)); // No dogs in the list yet
            dayCare.addDog(dog1);
            assertNull(dayCare.updateDog(-1, dog3));
            assertNull(dayCare.updateDog(1, dog3));
        }


    @Test
    void getDogValidIndex() {
        dayCare.addDog(dog1);
        assertEquals(dog1, dayCare.getDog(0));
    }

    @Test
    void getDogInvalidIndex() {
            dayCare.addDog(dog1);
        assertNull(dayCare.getDog(-1)); // Out of range
        assertNull(dayCare.getDog(1));  //out of range
    }

    @Test
        void getDogByName() {
                assertEquals(dog1, popDayCare.getDog("Buddy"));
                assertEquals(dog2, popDayCare.getDog("Max"));
                assertEquals(dog3, popDayCare.getDog("Bella"));
                assertNull(popDayCare.getDog("no dog of this name"));
    }
        @Test
        void getDogByid() {
            assertEquals(dog1, popDayCare.getDog(0));
            assertEquals(dog2, popDayCare.getDog("Max"));
            assertEquals(dog3, popDayCare.getDog("Bella"));
            assertNull(popDayCare.getDog("no dog of this name"));
        }
        @Test
        void getDogsByOwnersName(){
            dog1.addOwner(owner2);
            dog1.addOwner(owner3);

            assertEquals(3, (popDayCare.getDog(0).getNumOwners()));
            String allDogsOwner1 = popDayCare.getDogsByOwnersName("Alice");
            //assertEquals("", allDogsOwner1);
            assertTrue(allDogsOwner1.contains("Buddy"));
            assertFalse(allDogsOwner1.contains("Max"));
            assertFalse(allDogsOwner1.contains("Bella"));

            String allDogsOwner2 = popDayCare.getDogsByOwnersName("Bella");
            assertTrue(allDogsOwner1.contains("Buddy"));
            assertFalse(allDogsOwner1.contains("Max"));
            assertFalse(allDogsOwner1.contains("Bella"));

            dog2.addOwner(owner2);
            String allDogsOwner2update = popDayCare.getDogsByOwnersName("Alice");
            assertTrue(allDogsOwner2update.contains("Buddy"));
            assertFalse(allDogsOwner2update.contains("Max"));
            assertFalse(allDogsOwner2update.contains("Bella"));



            assertTrue(dayCare.getDogsByOwnersName("Alice").contains("There are no dogs registered at the moment"));

            Owner newOwner = new Owner(111, "Anne", "0871234");

            assertTrue(popDayCare.getDogsByOwnersName("Anne").contains("No Dogs have that owner"));

        }
}
@Nested
class Listing {
    @Test
    void testListDogsEmpty() {
        assertEquals("There are no dogs registered at the moment", dayCare.listAllDogs());
    }


    @Test
    void testListAllDogs() {   //toString already tested
        assertEquals("There are no dogs registered at the moment", dayCare.listAllDogs());
        dayCare.addDog(dog1);
        dayCare.addDog(dog2);
        dayCare.addDog(dog3);

        assertTrue(dayCare.listAllDogs().contains("Buddy"));
        assertTrue(dayCare.listAllDogs().contains("Max"));
        assertTrue(dayCare.listAllDogs().contains("Bella"));
    }

    @Test
    public void listAllDogsThatStayMoreThanDays() {
        boolean[] mixedBools = {true, true, false, true, true};
        dog1.setDaysInKennel(mixedBools);
        assertTrue(dog1.getDaysInKennel()[0]);
        assertTrue(dog1.getDaysInKennel()[1]);
        assertFalse(dog1.getDaysInKennel()[2]);
        assertTrue(dog1.getDaysInKennel()[3]);
        assertTrue(dog1.getDaysInKennel()[4]);

        assertTrue(dog1.isInKennel(0));
        assertFalse(dog1.isInKennel(2));
        assertEquals(4, dog1.numOfDaysInKennel());

        assertEquals(0, dog2.numOfDaysInKennel());
        assertEquals(0, dog3.numOfDaysInKennel());

        String over2Days = popDayCare.listAllDogsThatStayMoreThanDays(2);

        assertTrue(over2Days.contains("Buddy"));
        assertFalse(over2Days.contains("Max"));
        assertFalse(over2Days.contains("Bella"));

        boolean[] threeDays = {true, false, false, true, true};
        dog2.setDaysInKennel(threeDays);

        over2Days = popDayCare.listAllDogsThatStayMoreThanDays(2);
        assertTrue(over2Days.contains("Buddy"));
        assertTrue(over2Days.contains("Max"));
        assertFalse(over2Days.contains("Bella"));

        String over4Days = popDayCare.listAllDogsThatStayMoreThanDays(4);
        assertTrue(over4Days.contains("No Dogs stay longer"));
        assertTrue(over4Days.contains("4"));

        assertEquals("There are no dogs registered at the moment", dayCare.listAllDogsThatStayMoreThanDays(0));

    }

    @Test
    void listAllDangerousDogs() {
        String allDangerousDogs = popDayCare.listAllDangerousDogs();
        assertTrue(allDangerousDogs.contains("No Dangerous Breeds at the moment"));
        allDangerousDogs = dayCare.listAllDangerousDogs();

        assertTrue(allDangerousDogs.contains("There are no dogs registered at the moment"));

        dog1.setDangerousBreed(true);
        allDangerousDogs = popDayCare.listAllDangerousDogs();
        assertTrue(allDangerousDogs.contains("Buddy"));

    }

    @Test
    void testFindDogByOwnerAndBreedAndAge() {
        dayCare.addDog(dog1);
        dayCare.addDog(dog2);
        assertEquals(dog1, dayCare.findDogByOwnerAndBreedAndAge("Buddy", "Labrador", 3));
        assertNull(dayCare.findDogByOwnerAndBreedAndAge("Unknown", "Breed", 5));
    }

    @Test
    void findDogByOwner() {
        dog1.addOwner(owner2);
        dog1.addOwner(owner3);

        assertEquals(3, (popDayCare.getDog(0).getNumOwners()));
        String allDogsOwner1 = popDayCare.listAllDogsByOwner(owner1);
        assertTrue(allDogsOwner1.contains("Buddy"));
        assertFalse(allDogsOwner1.contains("Max"));
        assertFalse(allDogsOwner1.contains("Bella"));

        String allDogsOwner2 = popDayCare.listAllDogsByOwner(owner2);
        assertTrue(allDogsOwner1.contains("Buddy"));
        assertFalse(allDogsOwner1.contains("Max"));
        assertFalse(allDogsOwner1.contains("Bella"));

        dog2.addOwner(owner2);
        String allDogsOwner2update = popDayCare.listAllDogsByOwner(owner2);
        assertTrue(allDogsOwner2update.contains("Buddy"));
        assertTrue(allDogsOwner2update.contains("Max"));
        assertFalse(allDogsOwner2update.contains("Bella"));

        assertTrue(dayCare.listAllDogsByOwner(owner1).contains("There are no dogs registered at the moment"));

        Owner newOwner = new Owner(111, "Anne", "0871234");

        assertTrue(popDayCare.listAllDogsByOwner(newOwner).contains("No Dogs have that owner"));
    }
}
@Nested
class Calculations {
    @Test
    void testGetWeeklyIncome() {
        dog1.setDaysInKennel(2, true);
        dog1.setDaysInKennel(3, true);
        dayCare.addDog(dog1);
        dayCare.addDog(dog2);
        assertTrue(dayCare.getWeeklyIncome() >= 0);
        assertTrue(dayCare.getWeeklyIncome() == 60.0);
    }

@Test
    void numberOfDangerousDogs() {
        assertEquals(0, popDayCare.numberOfDangerousDogs());
        dog1.setDangerousBreed( true);
        dog2.setDangerousBreed( true);
        assertEquals(2, popDayCare.numberOfDangerousDogs());
    }
@Test
    void getAverageNumDaysPerWeek(){
        assertEquals(0, popDayCare.getAverageNumDaysPerWeek());

    boolean[] mixedBools = {true, true, false, true, true};
    dog1.setDaysInKennel(mixedBools);


    assertEquals(1.33, popDayCare.getAverageNumDaysPerWeek(), 0.01);
     }
   }
}
