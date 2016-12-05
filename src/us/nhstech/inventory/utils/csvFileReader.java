package us.nhstech.inventory.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Devin Matte
 * @author James Sonne
 * @version v0.2-Beta
 * @since 2016-02-11
 */

public class csvFileReader {
    // Deliminators
    private static final String COMMA_SEPARATOR = ",";

    // index of info
    private static final int ITEM_REF_IDX = 0;
    private static final int ITEM_NAME_IDX = 1;
    private static final int ITEM_AVAIL_IDX = 2;
    private static final int ITEM_ID_IDX = 3;
    private static final int ITEM_PERMISSION_IDX = 4;

    /**
     * Print entire file
     *
     * @param fileName
     * @return
     */
    public String printFile(String fileName) {
        // create reader and strings
        BufferedReader fileReader;
        String out = "";
        String line;
        try {
            // set reader
            fileReader = new BufferedReader(new FileReader(fileName));
            // check that there is still information to read
            while ((line = fileReader.readLine()) != null) {
                // add line to string
                out += "\n" + line;
            }
            // close reader
            fileReader.close();
        } catch (Exception e) {
            // output error information
            System.out.println("Error");
            e.printStackTrace();

        }
        // return string
        return out;
    }

    /**
     * Check place in list
     *
     * @param ref
     * @param fileName
     * @return
     */
    public int checkPosition(int ref, String fileName) {
        // initialize reader and string
        BufferedReader fileReader = null;
        String line;

        try {
            // set reader
            fileReader = new BufferedReader(new FileReader(fileName));
            // skip line
            fileReader.readLine();
            // set count
            int count = 0;
            // check that there is info to read
            while ((line = fileReader.readLine()) != null) {
                // split info into array
                String[] tokens = line.split(COMMA_SEPARATOR);
                // check if reference equals the item we are looking for
                if (Integer.parseInt(tokens[ITEM_REF_IDX]) == ref) {
                    // return the place of the item in the list
                    return count;
                }
                // keep track of place in list
                count++;
            }
            // catch errors and close reader
        } catch (Exception e) {
            System.out.println("Error finding position");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (Exception e) {
                System.out.println("Error closing file while finding position");
                e.printStackTrace();
            }
        }
        // return should an error occur
        return -1;
    }

    /**
     * Return list of all Items
     *
     * @param fileName
     * @return
     */
    public ArrayList<Item> getData(String fileName) {
        BufferedReader fileReader = null;
        // create ArrayList
        ArrayList<Item> items = null;
        try {
            // initialize ArrayList
            items = new ArrayList<>();

            // initialize string
            String line;
            // initialize reader
            fileReader = new BufferedReader(new FileReader(fileName));

            // skip header
            fileReader.readLine();

            // check that the line contains information
            while ((line = fileReader.readLine()) != null) {
                // create string array with the read string, cut based on
                // location of commas
                String[] tokens = line.split(COMMA_SEPARATOR);

                // ensure information is formatted correctly, and the line.split
                // resulted in multiple strings
                if (tokens.length > 0) {
                    // create item
                    Item read = new Item(Integer.parseInt(tokens[ITEM_REF_IDX]), tokens[ITEM_NAME_IDX],
                            Boolean.parseBoolean(tokens[ITEM_AVAIL_IDX]), (tokens[ITEM_ID_IDX]),
                            Boolean.parseBoolean(tokens[ITEM_PERMISSION_IDX]));
                    // add item to arraylist
                    items.add(read);
                }
            }
            // catch errors and close reader
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {

            try {
                fileReader.close();
            } catch (final Exception e) {
                System.out.println("Error closing reader");
                e.printStackTrace();
            }
        }
        // return list of items
        return items;
    }

    /**
     * Return item given its reference
     *
     * @param ref
     * @param fileName
     * @return
     */
    public Item getItem(int ref, String fileName) {
        BufferedReader fileReader = null;
        try {
            // initialize item to be returned
            Item read = null;

            // initialize string
            String line;

            // initialize reader
            fileReader = new BufferedReader(new FileReader(fileName));

            // skip header
            fileReader.readLine();

            // check that the line contains information
            while ((line = fileReader.readLine()) != null) {

                // create string array with the read string, cut based on
                // location of commas
                String[] tokens = line.split(COMMA_SEPARATOR);

                // ensure information is formated correctly, and the line.split
                // resulted in multiple strings
                if (tokens.length > 0) {
                    // check if the information correlates to the correct item
                    if (Integer.parseInt(tokens[ITEM_REF_IDX]) == ref) {
                        // create item
                        read = new Item(Integer.parseInt(tokens[ITEM_REF_IDX]), tokens[ITEM_NAME_IDX],
                                Boolean.parseBoolean(tokens[ITEM_AVAIL_IDX]), (tokens[ITEM_ID_IDX]),
                                (Boolean.parseBoolean(tokens[ITEM_PERMISSION_IDX])));
                        System.out.println("File Read Successfully");
                        return read;
                    } else {
                        // create item to be returned if the item is not indexed
                        read = new Item(ref, "ITEM NOT RECORDED", false, "none", false);
                    }
                }
            }
            // let user know the program was successful
            System.out.println("File Read Successfully");
            return read;
        } catch (Exception e) {
            // output error information
            System.out.println("Error");
            Item read = new Item(0, "error", false, "none", false);
            e.printStackTrace();
            return read;
        } finally {
            // close reader
            try {
                fileReader.close();
            } catch (Exception e) {
                System.out.println("Error closing reader");
                e.printStackTrace();
            }
        }
    }
}
