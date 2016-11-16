package us.nhstech.inventory.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author James Sonne & Devin Matte
 * @version v0.1-Beta
 * @since 2016-02-11
 */

public class ItemManager {

    /**
     * Checkout an Item
     *
     * @param ref
     * @param fileName The filename containing all item data
     * @param passFile The password file, containing the password
     * @param member
     * @param ID
     * @param Pass
     * @return
     */
    public String[] checkOut(int ref, String fileName, String passFile, String member, int ID, String Pass) {

        // create reader and writer
        csvFileReader read = new csvFileReader();
        csvFileWriter write = new csvFileWriter();

        // get member name
        Item item1 = read.getItem(ID, member);
        String name = item1.getName();

        // find items position in list
        int pos = read.checkPosition(ref, fileName);
        System.out.println(pos);

        // create array
        ArrayList<Item> items = read.getData(fileName);
        // check for permission
        String pass = (read.getItem(1000, passFile)).getName();
        if (((!items.get(pos).getPermission()) && (Pass == "")) || (Pass.equals(pass))) {

            // checkout item
            boolean checked = (items.get(pos)).checkOut(name);
            log(items.get(pos), name);
            // rewrite file
            write.enterData(items);
            write.writeCsvFile(fileName);
            if (checked) {
                return ("true, Item checked out successfully").split(",");
            } else {
                return ("true, Item not available").split(",");
            }

        } else {
            System.out.println("Password Incorrect");
            return ("false,Password Required").split(",");
        }
    }

    public void addMember(String fileName, int num, String name) {
        register(num, name, false, fileName);
    }


    private void log(Item item, String name) {
        //make reader and writer
        csvFileReader read = new csvFileReader();
        csvFileWriter write = new csvFileWriter();

        //set filename
        String logFileName = System.getProperty("user.home") + "/Log.csv";

        //get data
        ArrayList<Item> list = new ArrayList<Item>();
        list = read.getData(logFileName);

        //get date
        Date date = new Date();
        String time = (new Timestamp(date.getTime())).toString();

        //add item
        Item log = new Item(item.getReference(), item.getName(), item.getAvailable(), name + " - " + time, false);
        list.add(log);
        write.enterData(list);
        write.writeCsvFile(logFileName);

    }

    /**
     * Erase a file entirely
     *
     * @param fileName
     */
    public void clearFile(String fileName) {
        csvFileWriter write = new csvFileWriter();
        ArrayList<Item> list = new ArrayList<Item>();

        // enter empy data
        write.enterData(list);

        // rewrite file with no data
        write.writeCsvFile(fileName);
    }

    /**
     * Check back in an item that was checked out
     *
     * @param ref
     * @param fileName
     */
    public void checkIn(int ref, String fileName) {
        // create reader and writer
        csvFileReader read = new csvFileReader();
        csvFileWriter write = new csvFileWriter();

        // find items position in list
        int pos = read.checkPosition(ref, fileName);

        // create array
        ArrayList<Item> items = read.getData(fileName);
        String person = items.get(pos).getID();
        // checkin item
        (items.get(pos)).checkIn();
        log(items.get(pos), person);
        // rewrite file
        write.enterData(items);
        write.writeCsvFile(fileName);
    }

    /**
     * Register an Item into the system
     *
     * @param ref
     * @param name
     * @param permission
     * @param fileName
     */
    public void register(int ref, String name, boolean permission, String fileName) {
        // create reader and writer
        Item item = new Item(ref, name, true, "none", permission);
        csvFileReader read = new csvFileReader();
        csvFileWriter write = new csvFileWriter();

        // create array
        ArrayList<Item> items = read.getData(fileName);

        // check if ref is unused
        if (read.checkPosition(item.getReference(), fileName) == -1) {
            // add item
            items.add(item);
        } else {
            // output error
            System.out.println("Reference already used.");
        }

        // rewrite file
        write.enterData(items);
        write.writeCsvFile(fileName);
    }

    /**
     * Remove an item from the Registry
     *
     * @param ref
     * @param fileName
     */
    public void remove(int ref, String fileName) {
        // create reader and writer
        csvFileReader read = new csvFileReader();
        csvFileWriter write = new csvFileWriter();

        // create array
        ArrayList<Item> items = read.getData(fileName);

        // get position
        int pos = read.checkPosition((read.getItem(ref, fileName)).getReference(), fileName);

        // remove item
        items.remove(pos);

        // rewrite file
        write.enterData(items);
        write.writeCsvFile(fileName);
    }

    /**
     * Returns an item when called
     *
     * @param ref
     * @param fileName
     * @return
     */
    public Item getItem(int ref, String fileName) {
        // get item through reader
        csvFileReader read = new csvFileReader();
        return read.getItem(ref, fileName);
    }

    /**
     * Prints out the values of the csv file
     *
     * @param fileName
     * @return
     */
    public String printFile(String fileName) {
        csvFileReader read = new csvFileReader();
        return read.printFile(fileName);
    }

    /**
     * Prints an ArrayList nicely for easy viewing
     *
     * @param list
     */
    public void printList(ArrayList<Item> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\n" + list.get(i).toString());
        }
    }

    /**
     * Allows for items to be searched for within the ArrayList and returns only
     * the items the fit with the values searched for.
     *
     * @param fileName
     * @param search
     * @return
     */
    public ArrayList<Item> searchFor(String fileName, String search) {
        // create reader
        csvFileReader read = new csvFileReader();

        // create arraylists
        ArrayList<String> keyword = new ArrayList<String>();
        ArrayList<Item> info = read.getData(fileName);
        ArrayList<Item> returned = new ArrayList<Item>();

        // add the whole string as a keyword, covers for one word searches
        keyword.add(search);

        // add each word entered into the search as a separate keyword
        while (search.indexOf(' ') != -1) {
            keyword.add(search.substring(0, search.indexOf(' ')));
            search = search.substring(search.indexOf(' ') + 1);
        }

        // add the remaining word
        keyword.add(search);

        // check if each item name contains each keyword
        for (int i = 0; i < info.size(); i++) {
            for (int e = 0; e < keyword.size(); e++) {
                if (info.get(i).getName().toLowerCase().contains(keyword.get(e).toLowerCase())) {
                    returned.add(info.get(i));
                    // prevent items from being added twice for containing two
                    // keywords
                    break;
                }
            }
        }
        // return list of items
        return returned;

    }
}