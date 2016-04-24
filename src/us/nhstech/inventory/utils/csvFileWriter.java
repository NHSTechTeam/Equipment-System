package us.nhstech.inventory.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */

public class csvFileWriter {
    private static final String COMMA_SEPERATOR = ",";
    private static final String NEW_LINE = "\n";
    // file header
    private static final String FILE_HEADER = "Reference, name, availability, User, permission";
    // make arraylist
    private static ArrayList<Item> items = new ArrayList<Item>();

    /**
     * Gets the data that needs to be written
     *
     * @return
     */
    public ArrayList<Item> getData() {
        return items;
    }

    /**
     * Takes data
     *
     * @param list
     */
    public void enterData(ArrayList<Item> list) {
        items = list;
    }

    /**
     * Writes data to the csv file
     *
     * @param filename
     */
    public void writeCsvFile(String filename) {

        FileWriter fileWriter = null;
        try {
            // create writer
            fileWriter = new FileWriter(filename);

            // add header
            fileWriter.append(FILE_HEADER.toString());
            fileWriter.append(NEW_LINE);

            for (int i = 0; i < items.size(); i++) {
                // add data as a list of variables in string form separated by a
                // comma
                fileWriter.append(String.valueOf((items.get(i)).getReference()));
                fileWriter.append(COMMA_SEPERATOR);
                fileWriter.append((items.get(i)).getName());
                fileWriter.append(COMMA_SEPERATOR);
                fileWriter.append(String.valueOf((items.get(i)).getAvailable()));
                fileWriter.append(COMMA_SEPERATOR);
                fileWriter.append((items.get(i)).getID());
                fileWriter.append(COMMA_SEPERATOR);
                fileWriter.append(String.valueOf((items.get(i)).getPermission()));
                fileWriter.append(NEW_LINE);
            }

            // tell user write was successful
            System.out.println("File created Successfully");
        } catch (Exception e) {
            // output error information
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            try {
                // close writer
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                // output error info
                System.out.println("Error closing/flushing stream");
                e.printStackTrace();
            }
        }
    }

    /**
     * Change Password
     *
     * @param pass
     * @param fileName
     */
    public void newPass(String pass, String fileName) {
        // clear data from past changes
        items.clear();
        // add new item with password as its name
        items.add(new Item(1000, pass, true, "none", false));
        // rewrite file
        this.writeCsvFile(fileName);

    }
}
