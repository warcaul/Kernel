package models;

import utils.Helper;
import java.util.Objects;

/**
 * The {@code Owner} class represents a dog owner with an ID, name, and phone number.
 * It provides methods to set and retrieve owner details with validation.
 */
public class Owner {

    /** Unique identifier for the owner (3-digit number, default 100). */
    private int id = 100;

    /** Name of the owner (maximum 30 characters). */
    private String name = "";

    /** Phone number of the owner (10 digits, default "087302000"). */
    private String phoneNumber = "087302000";

    /**
     * Constructs an {@code Owner} object with the specified ID, name, and phone number.
     * Validates and truncates the name if necessary, and ensures the phone number is valid.
     *
     * @param id          The unique identifier for the owner (must be between 100 and 999).
     * @param name        The name of the owner (truncated to 30 characters if longer).
     * @param phoneNumber The phone number of the owner (must be numeric and up to 10 digits).
     */
    public Owner(int id, String name, String phoneNumber) {
        setId(id);
        this.name = Helper.truncateString(name, 30);
        setPhoneNumber(phoneNumber);
    }

    /**
     * Gets the owner's ID.
     *
     * @return The owner's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the owner's ID if it falls within the valid range (100-999).
     *
     * @param id The ID to be set.
     */
    public void setId(int id) {
        if (Helper.validRange(id, 100, 999)) {
            this.id = id;
        }
    }

    /**
     * Gets the owner's name.
     *
     * @return The owner's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the owner's name if it does not exceed 30 characters.
     *
     * @param name The name to be set.
     */
    public void setName(String name) {
        if (Helper.validateStringLength(name, 30)) {
            this.name = name;
        }
    }

    /**
     * Gets the owner's phone number.
     *
     * @return The owner's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the owner's phone number if it consists only of numeric digits and does not exceed 10 characters.
     *
     * @param phoneNumber The phone number to be set.
     */
    public void setPhoneNumber(String phoneNumber) {
        if (Helper.validateStringLength(phoneNumber, 10) && Helper.onlyContainsNumbers(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
    }

    /**
     * Compares this owner to another object for equality.
     * Two owners are considered equal if they have the same ID, name, and phone number.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return getId() == owner.getId() &&
                Objects.equals(getName(), owner.getName()) &&
                Objects.equals(getPhoneNumber(), owner.getPhoneNumber());
    }

    /**
     * Returns a string representation of the owner.
     *
     * @return A formatted string containing the owner's ID, name, and phone number.
     */
    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber ;
    }
}
