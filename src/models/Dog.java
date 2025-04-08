package models;

import java.util.ArrayList;
import java.util.Objects;

import utils.Helper;

/**
 * The {@code Dog} class represents a dog with its associated properties and behaviors.
 * This includes attributes like ID, name, breed, age, sex, whether it is dangerous or neutered,
 * kennel days, and associated owners.
 */
public class Dog {

    /**
     * Daily rate for dangerous dogs.
     */
    public static final float DANGEROUS_DAILY_RATE = 40;

    /**
     * Daily rate for non-dangerous dogs.
     */
    public static final float NONDANGEROUS_DAILY_RATE = 30;

    private int id = 1000;
    private String name = "";
    private String breed = "";
    private boolean dangerous = false;
    private int age = 5;
    private char sex = 'F';
    private boolean neutered = false;
    private ArrayList<Owner> owners = new ArrayList<>();
    private boolean[] daysInKennel = new boolean[5];

    /**
     * Constructs a {@code Dog} with a single owner.
     *
     * @param id        Unique ID (must be between 1000–9999).
     * @param name      Dog's name (truncated to 20 characters).
     * @param breed     Dog's breed.
     * @param dangerous Whether the dog is dangerous.
     * @param age       Dog's age (0–20).
     * @param sex       Dog's sex ('M' or 'F').
     * @param neutered  Whether the dog is neutered.
     * @param owner     Initial owner.
     */
    public Dog(int id, String name, String breed, boolean dangerous, int age, char sex, boolean neutered, Owner owner) {
        setId(id);
        this.name = Helper.truncateString(name, 20);
        setBreed(breed);
        setDangerousBreed(dangerous);
        setAge(age);
        setSex(sex);
        setNeutered(neutered);
        addOwner(owner);
    }

    /**
     * Constructs a {@code Dog} with a list of owners.
     */
    public Dog(int id, String name, String breed, boolean dangerous, int age, char sex, boolean neutered, ArrayList<Owner> owners) {
        setId(id);
        this.name = Helper.truncateString(name, 20);
        setBreed(breed);
        setDangerousBreed(dangerous);
        setAge(age);
        setSex(sex);
        setNeutered(neutered);
        this.owners = new ArrayList<>(owners);
    }

    /**
     * Constructs a {@code Dog} without owners.
     */
    public Dog(int id, String name, String breed, boolean dangerous, int age, char sex, boolean neutered) {
        setId(id);
        this.name = Helper.truncateString(name, 20);
        setBreed(breed);
        setDangerousBreed(dangerous);
        setAge(age);
        setSex(sex);
        setNeutered(neutered);
    }

    // ---------------- Getters and Setters ----------------

    public int getId() {
        return id;
    }

    /**
     * Sets the dog ID if it is between 1000 and 9999.
     */
    public void setId(int id) {
        if (Helper.validRange(id, 1000, 9999)) {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the dog's name if length is 20 or less.
     */
    public void setName(String name) {
        if (Helper.validateStringLength(name, 20)) {
            this.name = name;
        }
    }

    public boolean isDangerousBreed() {
        return dangerous;
    }

    public void setDangerousBreed(boolean dangerous) {
        this.dangerous = dangerous;
    }

    public int getAge() {
        return age;
    }

    /**
     * Sets the age if between 0 and 20.
     */
    public void setAge(int age) {
        if (Helper.validRange(age, 0, 20)) {
            this.age = age;
        }
    }

    public char getSex() {
        return sex;
    }

    /**
     * Sets the sex if the input is 'M' or 'F'.
     */
    public void setSex(char sex) {
        if (Helper.MFSexCheck(Character.toUpperCase(sex))) {
            this.sex = Character.toUpperCase(sex);
        }
    }

    public boolean isNeutered() {
        return neutered;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }

    public ArrayList<Owner> getOwners() {
        return owners;
    }

    /**
     * Returns owner by their ID.
     */
    public Owner getOwnerByID(int id) {
        int index = getOwnerIndexById(id);
        return index != -1 ? owners.get(index) : null;
    }


    /**
     * Returns index of the owner by ID or -1 if not found.
     */
    private int getOwnerIndexById(int id) {
        for (Owner owner : owners) {
            if (owner.getId() == id) {
                return owners.indexOf(owner);
            }
        }
        return -1;
    }

    /**
     * Adds a new owner to the dog.
     *
     * @param owner Owner to be added.
     * @return {@code true} if added, {@code false} if null.
     */
    public boolean addOwner(Owner owner) {
        if (owner != null && !owners.contains(owner)) {
            owners.add(owner);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Replaces an existing owner with the given one.
     */
    public Owner updateOwner(int ownerID, Owner owner) {
        int index = getOwnerIndexById(ownerID);
        if (index != -1) {
            owners.set(index, owner);
            return owner;
        }
        return null;
    }


    /**
     * Removes owner by ID.
     */
    public Owner removeOwner(int ownerID) {
        int index = getOwnerIndexById(ownerID);
        if (index != -1) {
            return owners.remove(getOwnerIndexById(ownerID));
        } else {
            return null;
        }
    }

    public boolean[] getDaysInKennel() {
        return daysInKennel;
    }

    public boolean getDaysInKennelByIndex(int index) {
        return daysInKennel[index];
    }

    /**
     * Sets presence in kennel on a specific day (0–4).
     */
    public void setDaysInKennel(int day, boolean present) {
        if (Helper.validRange(day, 0, 4)) {
            this.daysInKennel[day] = present;
        }
    }

    /**
     * Sets kennel attendance for the week.
     */
    public void setDaysInKennel(boolean[] daysInKennel) {
        if (daysInKennel.length == 5) {
            this.daysInKennel = daysInKennel.clone();
        }
    }


    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    // ---------------- Utility Methods ----------------

    /**
     * Counts how many days the dog was in the kennel.
     *
     * @return Number of days present in kennel.
     */
    public int numOfDaysInKennel() {
        int result = 0;
        for (boolean day : daysInKennel) {
            if (day) result++;
        }
        return result;
    }

    /**
     * Returns a formatted string of all owners.
     */
    public String listOwners() {
        StringBuilder result = new StringBuilder();
        for (Owner owner : owners) {
            result.append(owner.toString()).append("\n");
        }
        return result.toString();
    }

    /**
     * Calculates weekly bill based on number of kennel days and daily rate.
     *
     * @return The total weekly bill.
     */
    public float getWeeklyBill() {
        float total =  numOfDaysInKennel() * (dangerous ? DANGEROUS_DAILY_RATE : NONDANGEROUS_DAILY_RATE);
        return Math.round(total * 100) / 100f;

    }

    /**
     * Checks if the dog was in kennel on a specific day.
     *
     * @param day Day index (0–4).
     * @return {@code true} if present, {@code false} otherwise.
     */
    public boolean isInKennel(int day) {
        if (Helper.validRange(day, 0, 4)) {
            return Helper.validRange(day, 0, 4) && daysInKennel[day];
        } else return false;
    }

    /**
     * Returns the number of owners associated with the dog.
     */
    public int getNumOwners() {
        return owners.size();
    }

    // ---------------- Overrides ----------------

    /**
     * Compares two dogs for equality based on all attributes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return getId() == dog.getId()
                && isDangerousBreed() == dog.isDangerousBreed()
                && getAge() == dog.getAge()
                && getSex() == dog.getSex()
                && isNeutered() == dog.isNeutered()
                && Objects.equals(getName(), dog.getName())
                && Objects.equals(getBreed(), dog.getBreed())
                && Objects.equals(getOwners(), dog.getOwners())
                && Objects.deepEquals(getDaysInKennel(), dog.getDaysInKennel());
    }

    /**
     * Returns a human-readable string representation of the dog.
     */
    @Override
    public String toString() {
        return "ID: " + id +
                " Name: " + name +
                ", Breed: " + breed +
                " Dangerous: " + (dangerous ? "Yes " : "No ") +
                ", Age: " + age +
                ", Sex: " + sex +
                ", Neutered: " + (neutered ? "is neutered" : "is not neutered");
    }
}
