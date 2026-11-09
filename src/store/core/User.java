package store.core;
/**
 * This class is the base user class.
 * It holds username and email and is used for customers in the store system.
 * I kept it simple and only added what the HW asked for.
 * my name is noa halevi
 */
public abstract class User {
    private String username;
    private String email;
    /**
     * constructor for User.
     * saves the username and email.
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    /**
     * returns the username and email as string.
     */
    public String getEmil(){return email;}
    /**
     * getter for username.
     */
    public String getUsername() {
        return username;
    }
    /**
     * compares users only by username.
     */
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof User u) {
            return this.getUsername().equals(u.getUsername());
        }
        return false;
    }
    /**
    * Returns a simple text representation of the user,
     * * showing the username and email.
     */
    public String toString() {
        return username + " " + email;
    }
}
