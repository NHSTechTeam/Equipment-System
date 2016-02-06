package DataRecorderAndRetriever;

/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.3-Alpha
 * @since 2016-02-06
 */

public class Item {
	// variables
	private int reference;
	private String name;
	private boolean available;
	private String ID;
	private boolean permission;

	/**
	 * Constructor
	 * @param reference
	 * @param name
	 * @param available
	 * @param ID
	 * @param permission
	 */
	public Item(int reference, String name, boolean available, String ID, boolean permission) {
		this.reference = reference;
		this.name = name;
		this.available = available;
		this.ID = ID;
		this.permission = permission;
	}

	/**
	 * Checkout an Item
	 * @param ID
	 */
	public void checkOut(String ID) {
		if (available) {
			this.ID = ID;
			available = false;
		} else {
			System.out.println("Item not available.");
		}
	}

	/**
	 * Checkin an Item
	 */
	public void checkIn() {
		if (!available) {
			ID = "none";
			available = true;
		} else {
			System.out.println("Item already checked in.");
		}
	}

	// getters and setters
	public boolean getPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	// toString method
	public String toString() {
		String out = "";
		out += name + " has the reference of " + reference + " and ";
		if (available) {
			out += "is available.";
		} else {
			out += "is not available. Member - " + ID + " has checked this item out.";
		}
		if (permission) {
			out += "\nYou need permission to check out this item";
		}
		return out;
	}

}
