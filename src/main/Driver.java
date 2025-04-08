package main;

import controllers.DayCare;
import models.*;
import utils.Helper;
import utils.ScannerInput;

import java.util.ArrayList;


public class Driver {

    private DayCare dayCare = new DayCare();


    public static void main(String[] args) {

        new Driver();

    }

    /**
     * Constructor that starts the daycare application by attempting to load
     * existing data and launching the main menu.
     */
    public Driver() {
        start();
        runMenu();
    }


    //----------------------------------------------------------------------------
    // Private methods for displaying the menu and processing the selected options
    //----------------------------------------------------------------------------


    /**
     * Attempts to load the daycare data from a file. If loading fails,
     * it prompts the user to create a new daycare.
     */
    private void start() {
        try {
            loadDayCare();
            System.out.println("Data was successfully loaded");
        } catch (Exception e) {
            System.out.println("Create new daycare.xml");
            newDayCare();
        }

    }

    /**
     * Displays the main menu and reads user input.
     *
     * @return the selected menu option
     */
    private int mainMenu() {
        return ScannerInput.readNextInt("""
                ------------------------------------------------------------------
                |                         Day Care Main Menu                    |
                ------------------------------------------------------------------
                |  DOGS:                                                        |
                |   1) Add a Dog                                                |
                |   2) List Dogs                                                |
                |   3) Update a Dog                                             |
                |   4) Delete a Dog                                             |
                |   5) Set Dog Active Days                                      |
                ------------------------------------------------------------------
                |  OWNERS:                                                      |
                |   6) Add Owner to Dog                                         |
                |   7) Update Owner                                             |
                |   8) Remove Owner                                             |
                |   9) List Owners                                              |
                ------------------------------------------------------------------
                | 10) Search & Find Menu                                        |
                ------------------------------------------------------------------
                | 11) Weekly Report                                             |
                | 12) Total Report                                              |
                | 13) Owner Report                                              |
                ------------------------------------------------------------------
                | 14) Save DayCare to File                                      |
                |  0) Exit                                                      |
                ------------------------------------------------------------------
                ==>>   """);
    }

    /**
     * Controls the main program loop. Responds to user selections by calling the
     * appropriate functionality from the menu.
     */
    private void runMenu() {
        int option = mainMenu();

        while (option != 0) {
            switch (option) {
                case 1 -> addDog();
                case 2 -> printDogs();
                case 3 -> updateDog();
                case 4 -> deleteDog();
                case 5 -> setDogActiveDays();

                case 6 -> addOwner();
                case 7 -> updateOwner();
                case 8 -> removeOwner();
                case 9 -> listOwners();

                case 10 -> searchMenu();

                case 11 -> weeklyReport();
                case 12 -> totalReport();
                case 13 -> ownerReport();

                case 14 -> saveDayCare();
                default -> System.out.println("Invalid option entered: " + option);
            }

            ScannerInput.readNextLine("\nPress enter key to continue...");
            option = mainMenu();
        }
    }


    /**
     * Displays the search menu and reads user input.
     *
     * @return the selected menu option
     */
    private int runSearchMenu() {
        return ScannerInput.readNextInt("""
                -----------------------------------------------------
                |             Search & Find Sub-Menu                |
                -----------------------------------------------------
                |  1) Search Dogs by Name                           |
                |  2) Search Dogs by Owner Name                     |
                |  3) Find Dog by Exact Name                        |
                |  4) List Dogs by Breed                            |
                |  5) List Dogs by Gender                           |
                |  6) List Neutered Dogs                            |
                |  7) List Dangerous Dogs                           |
                |  8) List Dogs by Owner Name                       |
                |  9) List Dogs That Stay More Than N Days          |
                |  0) Return to Main Menu                           |
                -----------------------------------------------------
                ==>>   """);
    }


    /**
     * Controls the search menu loop. Responds to user selections by calling the
     * appropriate functionality from the menu.
     */
    private void searchMenu() {
        int option = runSearchMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> searchDogsByName();
                case 2 -> searchDogsByOwnerName();
                case 3 -> findDogByName();
                case 4 -> listDogsByBreed();
                case 5 -> listDogsByGender();
                case 6 -> listNeuteredDogs();
                case 7 -> listDangerousDogs();
                case 8 -> listAllDogsByOwnerName();
                case 9 -> listAllDogsThatStayMoreThanDays();
                default -> System.out.println("Invalid option entered: " + option);
            }

            option = runSearchMenu();
        }
    }


//------------------------------------
// Private methods for CRUD on Dog
//------------------------------------

    /**
     * Adds a new dog to the daycare.
     * Prompts the user for all necessary information and adds the dog if valid.
     */
    private void addDog() {
        int dogID = dayCare.getDogAutoID();
        String dogName = ScannerInput.readNextLine("Name of the dog: ");
        String dogBreed = ScannerInput.readNextLine("Breed: ");
        boolean dogDangerous = Helper.YNtoBoolean(ScannerInput.readNextChar("Is Dangerous?(Y or N):"));
        int dogAge = ScannerInput.readNextInt("Age: ");
        char dogGender = ScannerInput.readNextChar("Gender?(M or F):");
        boolean dogNeutered = Helper.YNtoBoolean(ScannerInput.readNextChar("Is Neutered? (Y or N):"));


        boolean isAdded = dayCare.addDog(new Dog(dogID, dogName, dogBreed, dogDangerous, dogAge, dogGender, dogNeutered));

        if (isAdded) {
            System.out.println("Dog was Successfully added");
        } else {
            System.out.println("No Dog Added");
        }
    }

    /**
     * Prints a list of all dogs currently registered in the daycare.
     */
    private void printDogs() {
        System.out.println("List of Dogs are:");
        System.out.println(dayCare.listAllDogs());

    }

    /**
     * Updates the details of an existing dog.
     * If the dog with the specified ID exists, prompts the user to update its attributes.
     */
    private void updateDog() {
        System.out.println(dayCare.listAllDogs());
        int dogID = ScannerInput.readNextInt("Enter dog ID: ");

        if (dayCare.getDog(dogID) != null) {
            String dogName = ScannerInput.readNextLine("Name of the dog: ");
            String dogBreed = ScannerInput.readNextLine("Breed: ");
            boolean dogDangerous = Helper.YNtoBoolean(ScannerInput.readNextChar("Is Dangerous?(Y or N):"));
            int dogAge = ScannerInput.readNextInt("Age: ");
            char dogGender = ScannerInput.readNextChar("Gender?(M or F):");
            boolean dogNeutered = Helper.YNtoBoolean(ScannerInput.readNextChar("Is Neutered? (Y or N):"));

            ArrayList<Owner> owners = dayCare.getDog(dogID).getOwners();

            Dog updatedDog = dayCare.updateDog(dogID, new Dog(dogID, dogName, dogBreed, dogDangerous, dogAge, dogGender, dogNeutered, owners));
            if (updatedDog == null) {
                System.out.println("Update failed. Please try again.");
            } else {
                System.out.println("Dog was successfully updated");
                System.out.println(updatedDog);
            }
        } else {
            System.out.println("No Dog Found");
        }
    }

    /**
     * Deletes a dog from the daycare.
     * Prompts the user to enter a dog ID and removes the dog if found.
     */
    private void deleteDog() {
        System.out.println(dayCare.listAllDogs());

        int dogID = ScannerInput.readNextInt("Enter dog ID: ");

        if (dayCare.getDog(dogID) != null) {
            if (dayCare.removeDog(dogID) != null) {
                System.out.println("Dog was removed");
            } else {
                System.out.println("Dog was not removed");
            }
        } else {
            System.out.println("No Dog Found");
        }
    }

    /**
     * Sets the active days (days in kennel) for a specific dog.
     * Prompts the user for each weekday and updates the dog's kennel attendance accordingly.
     */
    private void setDogActiveDays() {
        boolean[] activeDays = new boolean[5];
        String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        System.out.println(dayCare.listAllDogs());

        int dogID = ScannerInput.readNextInt("Enter Dog ID: ");
        Dog dog = dayCare.getDog(dogID);

        if (dog == null) {
            System.out.println("No dog found with that ID.");
        } else {
            for (int i = 0; i < 5; i++) {
                activeDays[i] = Helper.YNtoBoolean(
                        ScannerInput.readNextChar("Is the dog in the kennel on " + dayNames[i] + "? (Y or N): ")
                );
            }

            dog.setDaysInKennel(activeDays);
            System.out.println("Active days updated for " + dog.getName() + ".");
        }
    }


//------------------------------------
// Private methods for CRUD on Owner
//------------------------------------

    /**
     * Adds an owner to a dog.
     * Prompts the user to choose between adding an existing or new owner.
     */
    private void addOwner() {
        boolean flag = Helper.YNtoBoolean(ScannerInput.readNextChar("Would you like to add an existing owner? (Y or N)"));

        if (flag) {
            addExistingOwner();
        } else {
            addNewOwner();
        }
    }

    /**
     * Associates an existing owner with a dog.
     * Prompts the user to select a dog and an existing owner.
     */
    private void addExistingOwner() {
        System.out.println(dayCare.listAllDogs());
        int dogID = ScannerInput.readNextInt("Enter dog ID: ");

        System.out.println(dayCare.listOwners());
        int ownerID = ScannerInput.readNextInt("Enter owner ID: ");

        boolean isAdded = dayCare.addOwner(dayCare.getOwner(ownerID), dogID);

        if (isAdded) {
            System.out.println("Owner was Successfully added");
        } else {
            System.out.println("No owner Added");
        }

    }

    /**
     * Creates and associates a new owner with a dog.
     * Prompts the user for owner details and adds them to the selected dog.
     */
    private void addNewOwner() {
        System.out.println(dayCare.listAllDogs());
        int dogID = ScannerInput.readNextInt("Enter dog ID: ");

        String ownerName = ScannerInput.readNextLine("Name of the owner: ");
        String ownerNumber = ScannerInput.readNextLine("Phone Number of the owner: ");
        int ownerID = dayCare.getOwnerAutoID();

        boolean isAdded = dayCare.addOwner(new Owner(ownerID, ownerName, ownerNumber), dogID);

        if (isAdded) {
            System.out.println("Owner was Successfully added");
        } else {
            System.out.println("No owner Added");
        }

    }

    /**
     * Lists all owners currently registered in the system.
     */
    private void listOwners() {
        System.out.println("List of Owners:");
        System.out.println(dayCare.listOwners());
    }


    /**
     * Updates an existing owner’s information for a selected dog.
     * Prompts the user to enter updated details.
     */
    private void updateOwner() {
        System.out.println(dayCare.listAllDogs());
        int dogID = ScannerInput.readNextInt("Enter dog ID: ");

        if (dayCare.getDog(dogID) != null) {
            System.out.println(dayCare.listAllOwnersByDogID(dogID));

            int ownerID = ScannerInput.readNextInt("Enter owner ID: ");
            String ownerName = ScannerInput.readNextLine("Name of the owner: ");
            String ownerNumber = ScannerInput.readNextLine("Phone Number of the owner: ");


            Owner updatedOwner = dayCare.updateOwner(dogID, ownerID, new Owner(ownerID, ownerName, ownerNumber));
            if (updatedOwner == null) {
                System.out.println("Owner was not successfully updated");
            } else {
                System.out.println("Owner was successfully updated");
                System.out.println(updatedOwner);
            }
        } else {
            System.out.println("No Dog Found");
        }

    }

    /**
     * Removes an owner from a dog.
     * Prompts the user to choose the dog and owner to remove.
     */
    private void removeOwner() {
        System.out.println(dayCare.listAllDogs());
        int dogID = ScannerInput.readNextInt("Enter dog ID: ");

        if (dayCare.getDog(dogID) != null) {

            System.out.println(dayCare.listAllOwnersByDogID(dogID));
            int ownerID = ScannerInput.readNextInt("Enter owner ID: ");

            if (dayCare.removeOwner(ownerID, dogID) != null) {
                System.out.println("Owner was removed");
            } else {
                System.out.println("Owner was not removed");
            }
        } else {
            System.out.println("No Dog Found");
        }
    }

//-----------------------------------------------------------------
//  Private methods for Search facility
//-----------------------------------------------------------------

    /**
     * Displays a list of all dogs marked as dangerous.
     */
    private void listDangerousDogs() {
        System.out.println("List of dangerous Dogs are:");
        System.out.println(dayCare.listAllDangerousDogs());
    }

    /**
     * Displays dogs that match a specific breed entered by the user.
     */
    private void listDogsByBreed() {
        String breed = ScannerInput.readNextLine("Breed that we are looking for: ");
        System.out.println(dayCare.listDogsByBreed(breed));
    }

    /**
     * Displays all dogs that match the gender selected by the user ('M' or 'F').
     */
    private void listDogsByGender() {
        char gender = ScannerInput.readNextChar("Gender (M or F):");
        System.out.println(dayCare.listDogsByGender(gender));
    }

    /**
     * Displays all dogs that are marked as neutered.
     */
    private void listNeuteredDogs() {
        System.out.println("List of neutered Dogs are:");
        System.out.println(dayCare.listNeuteredDogs());
    }

    /**
     * Lists all dogs owned by people whose name contains the given input.
     */
    private void listAllDogsByOwnerName() {
        System.out.println(dayCare.listOwners());

        String ownerName = ScannerInput.readNextLine("Owner Name: ");

        System.out.println(dayCare.getDogsByOwnersName(ownerName));
    }

    /**
     * Displays dogs that stay in the kennel for more than a user-specified number of days.
     */
    private void listAllDogsThatStayMoreThanDays() {
        int numDays = ScannerInput.readNextInt("Number of days: ");
        System.out.println(dayCare.listAllDogsThatStayMoreThanDays(numDays));
    }

    /**
     * Searches and displays dogs whose names partially match the input.
     */
    private void searchDogsByName() {
        String namePart = ScannerInput.readNextLine("Enter part of the dog’s name to search: ");
        System.out.println(dayCare.searchDogsByName(namePart));
    }

    /**
     * Searches and displays dogs based on a partial match of their owner’s name.
     */
    private void searchDogsByOwnerName() {
        String ownerPart = ScannerInput.readNextLine("Enter part of the owner's name to search: ");
        System.out.println(dayCare.getDogsByOwnersName(ownerPart));
    }

    /**
     * Finds and displays a dog that matches the exact name entered.
     * Also prints out the dog's owners if found.
     */
    private void findDogByName() {
        String name = ScannerInput.readNextLine("Enter exact dog name: ");
        Dog dog = dayCare.getDog(name);

        if (dog != null) {
            System.out.println("Dog found:");
            System.out.println(dog);
            System.out.println("Owners:\n" + dog.listOwners());
        } else {
            System.out.println("No dog found with that name.");
        }
    }


//-----------------------------
//  Private methods for Reports
// ----------------------------

    private void weeklyReport() {
        System.out.println("===== WEEKLY REPORT =====");
        System.out.println("Weekly income: " + dayCare.getWeeklyIncome());
        System.out.println("Week timetable:\n" + listDogsByDayOfTheWeek());
    }


    private String listDogsByDayOfTheWeek() {
        StringBuilder result = new StringBuilder();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        for (int i = 0; i < days.length; i++) {
            result.append(days[i]).append(":\n");
            result.append(dayCare.listDogsByDayOfWeek(i)).append("\n\n");
        }

        return result.toString();
    }

    private String dogStatistics() {
        return """
                --------------------------------
                Dog Statistics:
                Total Dogs: %d
                Neutered Dogs: %d
                Dangerous Dogs: %d
                """.formatted(
                dayCare.numberOfDogs(),
                dayCare.numberOfNeuteredDogs(),
                dayCare.numberOfDangerousDogs()
        );
    }


    private void totalReport() {
        System.out.println("===== TOTAL REPORT =====");
        System.out.println("DayCare Name: " + dayCare.getName());
        System.out.println("Maximum Number of Dogs Allowed: " + dayCare.getMaxNumberOfDogs());
        System.out.println("\n--- All Dogs and Their Owners ---");
        System.out.println(dayCare.listAllDogs()); // includes owners per dog
        System.out.println(dogStatistics());
    }

    private void ownerReport() {
        System.out.println("===== OWNER REPORT =====");

        ArrayList<Owner> owners = dayCare.getOwners();

        if (owners.isEmpty()) {
            System.out.println("No owners found.");
            return;
        }

        for (Owner owner : owners) {
            System.out.println("Owner: " + owner.getName() + " (ID: " + owner.getId() + ")");
            System.out.println("Phone: " + owner.getPhoneNumber());

            ArrayList<Dog> ownedDogs = dayCare.searchDogsByOwner(owner);
            if (ownedDogs.isEmpty()) {
                System.out.println("  No dogs assigned.\n");
            } else {
                for (Dog dog : ownedDogs) {
                    System.out.println("  - " + dog.getName() + " (" + dog.getBreed() + ")");
                }
                System.out.println();
            }
        }
    }


//---------------------------------
//  Private methods for Persistence
// --------------------------------

    /**
     * Prompts the user to enter the daycare name and maximum number of dogs.
     * Initializes the daycare with these values.
     */
    private void newDayCare() {
        String name = ScannerInput.readNextLine("Enter the name of the day care: ");
        int numOfDogs = ScannerInput.readNextInt("Enter the number of dogs: ");
        dayCare = new DayCare(name, numOfDogs);
    }

    /**
     * Loads daycare data from a file using serialization.
     *
     * @throws Exception if file reading fails
     */
    private void loadDayCare() throws Exception {
        try {
            dayCare.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Saves the current daycare data to a file using serialization.
     */
    private void saveDayCare() {
        try {
            dayCare.save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }

    }

}
