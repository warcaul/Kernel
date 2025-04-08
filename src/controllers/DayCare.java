package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.Dog;
import models.Owner;
import utils.Helper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * The DayCare class manages a collection of dogs and their owners.
 * It supports operations for adding, removing, updating, and listing dogs and owners,
 * as well as persistence through XML serialization.
 */
public class DayCare {

    //-------------------------------------
    //  Fields
    //-------------------------------------

    /**
     * List of dogs currently in the daycare
     */
    private ArrayList<Dog> dogsArray = new ArrayList<>();

    /**
     * Name of the daycare (max 10 characters)
     */
    private String name = "";

    /**
     * Maximum number of dogs allowed (between 10 and 100)
     */
    private int maxNumberOfDogs = 10;

    /**
     * Auto-generated ID for dogs
     */
    private int dogAutoID = 1000;

    /**
     * Auto-generated ID for owners
     */
    private int ownerAutoID = 100;

    //-------------------------------------
    //  Constructor
    //-------------------------------------

    /**
     * Constructs a new DayCare instance with a name and a maximum number of dogs.
     *
     * @param name            The name of the daycare (max 10 characters).
     * @param maxNumberOfDogs The maximum number of dogs allowed.
     */
    public DayCare(String name, int maxNumberOfDogs) {
        this.name = Helper.truncateString(name, 10);
        setMaxNumberOfDogs(maxNumberOfDogs);
    }

    /**
     * Default constructor for serialization or manual configuration
     */
    public DayCare() {
    }

    //-------------------------------------
    //  Setters/Getters
    //-------------------------------------

    /**
     * @return the name of the daycare
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the daycare if it's 10 characters or fewer.
     *
     * @param name the new name
     */
    public void setName(String name) {
        if (Helper.validateStringLength(name, 10)) {
            this.name = name;
        }
    }

    /**
     * @return the maximum number of dogs allowed
     */
    public int getMaxNumberOfDogs() {
        return maxNumberOfDogs;
    }

    /**
     * Sets the maximum number of dogs if between 10 and 100.
     *
     * @param maxNumberOfDogs the new maximum
     */
    public void setMaxNumberOfDogs(int maxNumberOfDogs) {
        if (Helper.validRange(maxNumberOfDogs, 10, 100)) {
            this.maxNumberOfDogs = maxNumberOfDogs;
        }
    }

    /**
     * @return the list of all dogs in the daycare
     */
    public ArrayList<Dog> getDogsArray() {
        return dogsArray;
    }

    /**
     * Replaces the list of dogs with a new list.
     *
     * @param dogsArray the new list of dogs
     */
    public void setDogsArray(ArrayList<Dog> dogsArray) {
        this.dogsArray = dogsArray;
    }

    /**
     * @return the current dog auto-increment ID
     */
    public int getDogAutoID() {
        return dogAutoID;
    }

    /**
     * @return the current owner auto-increment ID
     */
    public int getOwnerAutoID() {
        return ownerAutoID;
    }

    //-------------------------------------
    //  ARRAYLIST DOG CRUD
    //-------------------------------------

    /**
     * Gets a dog by index or ID.
     *
     * @param input index (<1000) or ID (>=1000)
     * @return the dog found or null
     */
    public Dog getDog(int input) {
        if (input >= 1000) {
            return getDogByID(input);
        } else {
            return getDogByIndex(input);
        }
    }

    /**
     * Finds a dog by ID.
     *
     * @param id the dog's ID
     * @return the matching dog or null
     */
    public Dog getDogByID(int id) {
        for (Dog dog : dogsArray) {
            if (dog.getId() == id) {
                return dog;
            }
        }
        return null;
    }

    /**
     * Gets a dog by its position in the list.
     *
     * @param index the index
     * @return the dog or null if out of bounds
     */
    public Dog getDogByIndex(int index) {
        if (isValidDogIndex(index)) {
            return dogsArray.get(index);
        } else {
            return null;
        }
    }

    /**
     * Adds a dog if under the maximum dog limit.
     *
     * @param dog the dog to add
     * @return true if added
     */
    public boolean addDog(Dog dog) {
        if (dog != null && (dogsArray.size() < maxNumberOfDogs)) {
            dogsArray.add(dog);
            dogAutoID++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates an existing dog.
     *
     * @param id  the ID of the dog to update
     * @param dog the new dog object
     * @return the updated dog or null
     */
    public Dog updateDog(int id, Dog dog) {
        if (isValidDogID(id) && (dog != null)) {
            dogsArray.set(getDogIndexByID(id), dog);
            return dog;
        } else {
            return null;
        }
    }

    /**
     * Removes a dog by ID.
     *
     * @param id the dog's ID
     * @return the removed dog or null
     */
    public Dog removeDog(int id) {
        if (isValidDogID(id)) {
            return dogsArray.remove(getDogIndexByID(id));
        } else {
            return null;
        }
    }

    //-------------------------------------
    //  ARRAYLIST OWNER CRUD
    //-------------------------------------

    /**
     * Adds an owner to a dog.
     *
     * @param owner the owner to add
     * @param dogID the dog's ID
     * @return true if added successfully
     */
    public boolean addOwner(Owner owner, int dogID) {
        if (owner != null && isValidDogID(dogID)) {
            ownerAutoID++;
            return dogsArray.get(getDogIndexByID(dogID)).addOwner(owner);
        } else {
            return false;
        }
    }

    /**
     * Updates an existing owner for a dog.
     *
     * @param dogID   the dog's ID
     * @param ownerID the owner's ID
     * @param owner   the updated owner
     * @return the updated owner or null
     */
    public Owner updateOwner(int dogID, int ownerID, Owner owner) {
        if (isValidDogID(dogID) && isValidOwnerID(ownerID)) {
            return dogsArray.get(getDogIndexByID(dogID)).updateOwner(ownerID, owner);
        } else {
            return null;
        }
    }

    /**
     * Removes an owner from a dog.
     *
     * @param ownerID the owner's ID
     * @param dogID   the dog's ID
     * @return the removed owner or null
     */
    public Owner removeOwner(int ownerID, int dogID) {
        if (isValidDogID(dogID) && isValidOwnerID(ownerID)) {
            return dogsArray.get(getDogIndexByID(dogID)).removeOwner(ownerID);
        } else {
            return null;
        }
    }

    //-------------------------------------
    //  ARRAYLIST - Utility methods
    //-------------------------------------

    /**
     * @return true if index is a valid dog index
     */
    private boolean isValidDogIndex(int index) {
        return index >= 0 && index < dogsArray.size();
    }

    /**
     * @return true if ID is a valid dog ID
     */
    private boolean isValidDogID(int id) {
        return getDogByID(id) != null;
    }
    /**
     * @return true if ID is a valid owner ID
     */
    private boolean isValidOwnerID(int id) {
        return id >= 100 && id <= ownerAutoID;
    }

    /**
     * @return index of owner by ID, or -1 if not found
     */
    private int getOwnerIndexByID(int id) {
        for (Owner owner : getOwners()) {
            if (owner.getId() == id) {
                return getOwners().indexOf(owner);
            }
        }
        return -1;
    }

    /**
     * @return index of dog by ID, or -1 if not found
     */
    private int getDogIndexByID(int id) {
        for (Dog dog : dogsArray) {
            if (dog.getId() == id) {
                return dogsArray.indexOf(dog);
            }
        }
        return -1;
    }
    //------------------------------------
    // LISTING METHODS - Basic and Advanced
    //------------------------------------

    /**
     * Lists all dogs and their owners.
     *
     * @return Formatted list of all dogs in the daycare.
     */
    public String listAllDogs() {
        if (dogsArray.isEmpty()) {
            return "There are no dogs registered at the moment";
        }

        StringBuilder result = new StringBuilder();
        for (Dog dog : dogsArray) {
            result.append(dog.toString()).append("\nOwners:\n").append(dog.listOwners()).append("\n");
        }
        return result.toString();
    }

    /**
     * Lists all dogs marked as dangerous.
     *
     * @return Formatted list or message if none found.
     */
    public String listAllDangerousDogs() {
        if (dogsArray.isEmpty()) return "There are no dogs registered at the moment";

        StringBuilder result = new StringBuilder();
        for (Dog dog : dogsArray) {
            if (dog.isDangerousBreed()) result.append(dog.toString()).append("\n");
        }

        return result.length() == 0 ? "No Dangerous Breeds at the moment" : result.toString();
    }

    /**
     * Lists dogs that match a given breed.
     *
     * @param breed Breed name or partial match.
     * @return List of matching dogs or message if none found.
     */
    public String listDogsByBreed(String breed) {
        if (dogsArray.isEmpty()) return "There are no dogs registered at the moment";

        StringBuilder result = new StringBuilder();
        for (Dog dog : dogsArray) {
            if (dog.getBreed().toLowerCase().contains(breed.toLowerCase())) {
                result.append(dog.toString()).append("\n");
            }
        }

        return result.length() == 0 ? "No dog that Breed at the moment" : result.toString();
    }

    /**
     * Lists dogs filtered by gender.
     *
     * @param gender 'M' or 'F'
     * @return Matching dogs or message if none found.
     */
    public String listDogsByGender(char gender) {
        if (dogsArray.isEmpty()) return "There are no dogs registered at the moment";

        StringBuilder result = new StringBuilder();
        for (Dog dog : dogsArray) {
            if (dog.getSex() == gender) result.append(dog.toString()).append("\n");
        }

        return result.length() == 0 ? "No dog found" : result.toString();
    }

    /**
     * Lists all neutered dogs.
     *
     * @return Formatted list or message if none found.
     */
    public String listNeuteredDogs() {
        if (dogsArray.isEmpty()) return "There are no dogs registered at the moment";

        StringBuilder result = new StringBuilder();
        for (Dog dog : dogsArray) {
            if (dog.isNeutered()) result.append(dog.toString()).append("\n");
        }

        return result.length() == 0 ? "No Neutered dogs at the moment" : result.toString();
    }

    /**
     * Lists all dogs associated with a given owner.
     *
     * @param owner Owner to match.
     * @return List of dogs or a message.
     */
    public String listAllDogsByOwner(Owner owner) {
        if (dogsArray.isEmpty()) return "There are no dogs registered at the moment.";

        ArrayList<Dog> foundDogs = searchDogsByOwner(owner);
        if (foundDogs.isEmpty()) return "No Dogs have that owner.";

        StringBuilder result = new StringBuilder();
        for (Dog dog : foundDogs) result.append(dog.toString()).append("\n");

        return result.toString();
    }

    /**
     * Lists all dogs that belong to an owner with the specified ID.
     *
     * @param ownerID ID of the owner.
     * @return List or not-found message.
     */
    public String listDogsByOwnerID(int ownerID) {
        Owner owner = getOwner(ownerID);
        return owner == null ? "Owner with ID " + ownerID + " not found." : listAllDogsByOwner(owner);
    }

    /**
     * Lists dogs by owner's name (partial match).
     *
     * @param name Part or full name of the owner.
     * @return Formatted dog list per owner.
     */
    public String listDogsByOwnerName(String name) {
        ArrayList<Owner> matchingOwners = new ArrayList<>();

        for (Owner owner : getOwners()) {
            if (owner.getName().toLowerCase().contains(name.toLowerCase())) {
                matchingOwners.add(owner);
            }
        }

        if (matchingOwners.isEmpty()) return "No owners found with name containing: " + name;

        StringBuilder result = new StringBuilder();
        for (Owner owner : matchingOwners) {
            result.append("Dogs owned by ").append(owner.getName())
                    .append(" (ID: ").append(owner.getId()).append("):\n")
                    .append(listAllDogsByOwner(owner)).append("\n");
        }

        return result.toString();
    }

    /**
     * Lists dogs whose owners match a given name part.
     *
     * @param namePart partial owner name
     * @return List of dogs or message.
     */
    public String getDogsByOwnersName(String namePart) {
        if (dogsArray.isEmpty()) return "There are no dogs registered at the moment.";

        ArrayList<Dog> dogs = searchDogsByOwnerName(namePart);
        if (dogs.isEmpty()) return "No Dogs have that owner";

        StringBuilder result = new StringBuilder();
        for (Dog dog : dogs) result.append(dog.toString()).append("\n");

        return result.toString();
    }

    /**
     * Lists owners for a specific dog.
     *
     * @param dogID ID of the dog
     * @return Owner list or message.
     */
    public String listAllOwnersByDogID(int dogID) {
        if (!isValidDogID(dogID)) return "Dog not found";

        int index = getDogIndexByID(dogID);
        if (index == -1) return "Dog not found";

        ArrayList<Owner> ownerList = dogsArray.get(index).getOwners();
        if (ownerList.isEmpty()) return "No Owners have that dog";

        StringBuilder result = new StringBuilder();
        for (Owner owner : ownerList) result.append(owner.toString()).append("\n");

        return result.toString();
    }

    /**
     * Lists all unique owners in the system.
     *
     * @return All owners or a message.
     */
    public String listOwners() {
        ArrayList<Owner> owners = getOwners();
        if (owners.isEmpty()) return "There are no owners at the moment.";

        StringBuilder result = new StringBuilder();
        for (Owner owner : owners) result.append(owner.toString()).append("\n");

        return result.toString();
    }

    /**
     * Lists dogs that stayed in the kennel more than the specified number of days.
     *
     * @param days threshold of days
     * @return Matching dogs or message.
     */
    public String listAllDogsThatStayMoreThanDays(int days) {
        if (dogsArray.isEmpty()) return "There are no dogs registered at the moment";

        StringBuilder result = new StringBuilder();
        for (Dog dog : dogsArray) {
            if (dog.numOfDaysInKennel() > days) {
                result.append(dog.toString()).append("\n");
            }
        }

        return result.length() == 0 ?
                "No Dogs stay longer than " + days + " days at the moment" :
                result.toString();
    }

    /**
     * Lists dogs in the kennel on a specific weekday (0=Monday, 4=Friday).
     *
     * @param day index of the day
     * @return Formatted dog list or error message.
     */
    public String listDogsByDayOfWeek(int day) {
        if (!Helper.validRange(day, 0, 4)) return "Invalid day selected";
        if (dogsArray.isEmpty()) return "There are no dogs registered at the moment";

        String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        StringBuilder result = new StringBuilder("Dogs in the kennel on " + dayNames[day] + ":\n\n");
        int counter = 0;

        for (Dog dog : dogsArray) {
            if (dog.isInKennel(day)) {
                counter++;
                result.append(counter).append(": ").append(dog.toString()).append("\n");
            }
        }

        return counter == 0
                ? "No Dogs in the kennel on " + dayNames[day]
                : result.append("\nTotal dogs: ").append(counter).toString();
    }

    //-------------------------------------
    //  Counting Methods
    //-------------------------------------

    /**
     * @return Total number of dogs
     */
    public int numberOfDogs() {
        return dogsArray.size();
    }

    /**
     * @return Number of dogs marked as dangerous
     */
    public int numberOfDangerousDogs() {
        int result = 0;
        for (Dog dog : dogsArray) {
            if (dog.isDangerousBreed()) result++;
        }
        return result;
    }

    /**
     * @return Number of neutered dogs
     */
    public int numberOfNeuteredDogs() {
        int neutered = 0;
        for (Dog dog : dogsArray) {
            if (dog.isNeutered()) neutered++;
        }
        return neutered;
    }

    /**
     * @return Combined weekly bill for all dogs
     */
    public double getWeeklyIncome() {
        double result = 0;
        for (Dog dog : dogsArray) {
            result += dog.getWeeklyBill();
        }
        return result;
    }

    /**
     * @return Average number of days dogs stay in the kennel
     */
    public float getAverageNumDaysPerWeek() {
        float result = 0;
        for (Dog dog : dogsArray) {
            result += dog.numOfDaysInKennel();
        }
        return result / dogsArray.size();
    }

    //------------------------------
    //  FINDING METHODS
    //------------------------------

    /**
     * Gets a unique list of owners from all dogs.
     *
     * @return List of unique owners.
     */
    public ArrayList<Owner> getOwners() {
        HashMap<Integer, Owner> uniqueOwners = new HashMap<>();
        for (Dog dog : dogsArray) {
            for (Owner owner : dog.getOwners()) {
                uniqueOwners.putIfAbsent(owner.getId(), owner);
            }
        }
        return new ArrayList<>(uniqueOwners.values());
    }

    /**
     * Finds a specific owner by ID.
     *
     * @param id the owner's ID
     * @return the Owner or null
     */
    public Owner getOwner(int id) {
        if (getOwnerIndexByID(id) == -1) return null;
        else {
            return getOwners().get(getOwnerIndexByID(id));
        }
    }

    /**
     * Finds a dog by exact name match.
     *
     * @param name the name
     * @return the dog or null
     */
    public Dog getDog(String name) {
        for (Dog dog : dogsArray) {
            if (dog.getName().equals(name)) return dog;
        }
        return null;
    }

    /**
     * Finds a dog by owner's name, breed, and age.
     *
     * @param ownerName the dog name (likely misnamed param)
     * @param breed     the breed
     * @param age       the age
     * @return the matched dog or null
     */
    public Dog findDogByOwnerAndBreedAndAge(String ownerName, String breed, int age) {
        for (Dog dog : dogsArray) {
            if (dog.getAge() == age && dog.getName().equals(ownerName) && dog.getBreed().equals(breed)) {
                return dog;
            }
        }
        return null;
    }

    //------------------------------
    //  SEARCHING METHODS
    //------------------------------

    /**
     * Searches for dogs whose owners match a name part.
     *
     * @param namePart partial name
     * @return list of matching dogs
     */
    public ArrayList<Dog> searchDogsByOwnerName(String namePart) {
        ArrayList<Dog> result = new ArrayList<>();
        for (Dog dog : dogsArray) {
            for (Owner owner : dog.getOwners()) {
                if (owner.getName().toLowerCase().contains(namePart.toLowerCase())) {
                    result.add(dog);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Searches for all dogs associated with a given owner.
     *
     * @param owner the owner
     * @return list of dogs
     */
    public ArrayList<Dog> searchDogsByOwner(Owner owner) {
        ArrayList<Dog> result = new ArrayList<>();
        for (Dog dog : dogsArray) {
            if (dog.getOwners().contains(owner)) result.add(dog);
        }
        return result;
    }

    /**
     * Searches dogs by partial name.
     *
     * @param namePart part of the dog's name
     * @return formatted string of matches
     */
    public String searchDogsByName(String namePart) {
        if (dogsArray.isEmpty()) return "No dogs registered.";

        StringBuilder result = new StringBuilder();
        int index = 1;
        for (Dog dog : dogsArray) {
            if (dog.getName().toLowerCase().contains(namePart.toLowerCase())) {
                result.append(index).append(": ").append(dog.toString()).append("\n");
            }
            index++;
        }

        return result.length() == 0 ? "No dogs found with that name." : result.toString();
    }

    //---------------------------------
    //  Methods for Persistence
    //---------------------------------

    /**
     * Loads daycare data from XML file (daycare.xml).
     *
     * @throws Exception if loading fails
     */
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        Class<?>[] classes = new Class[]{Dog.class, DayCare.class, Owner.class};

        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("daycare.xml"));
        DayCare loadedData = (DayCare) is.readObject();
        is.close();

        this.name = loadedData.getName();
        this.maxNumberOfDogs = loadedData.getMaxNumberOfDogs();
        this.dogsArray = loadedData.getDogsArray();
        this.dogAutoID = loadedData.getDogAutoID();
        this.ownerAutoID = loadedData.getOwnerAutoID();
    }

    /**
     * Saves the current daycare data to XML file (daycare.xml).
     *
     * @throws Exception if saving fails
     */
    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("daycare.xml"));
        out.writeObject(this);
        out.close();
    }
}