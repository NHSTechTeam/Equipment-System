package DataRecorderAndRetriever;

/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.3-Alpha
 * @since 2016-02-06
 */

public class Item {
	private int reference;
	private String name;
	private boolean available;
	private String ID;
	private boolean permission;

	/**
	 * Constructor
	 * 
	 * @param reference
	 *            Reference number of the item
	 * @param name
	 *            Name of the item
	 * @param available
	 *            Boolean of whether or not the item is available
	 * @param ID
	 *            ID of the member who has the item checked out
	 * @param permission
	 *            Boolean for whether or not the item needs executive permission
	 *            in order to be checked out
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
	 * 
	 * @param ID
	 */
	public boolean checkOut(String ID) {
		if (available) {
			this.ID = ID;
			available = false;
			return true;
		} else {
			System.out.println("Item not available.");
			return false;
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

	/**
	 * Returns Permission
	 * @return {@link permission}
	 */
	public boolean getPermission() {
		return permission;
	}

	/**
	 * Set Permission
	 * @param permission sets {@link permission}
	 */
	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	/**
	 * Returns ID
	 * @return {@link ID}
	 */
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * Returns Reference
	 * @return {@link reference}
	 */
	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	/**
	 * Returns Name
	 * @return {@link name}
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return Available
	 * @return {@link available}
	 */
	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

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