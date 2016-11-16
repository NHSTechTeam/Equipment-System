package us.nhstech.inventory.utils;

import java.util.ArrayList;

/**
 * @author James Sonne & Devin Matte
 * @version v0.1-Beta
 * @since 2016-02-11
 */

public class LogManager {

    private final csvFileReader read = new csvFileReader();

    /**
     * @param fileName
     * @return
     */
    public ArrayList<Log> getList(String fileName) {
        ArrayList<Log> list = new ArrayList<Log>();
        ArrayList<Item> items = read.getData(fileName);
        String inOut;
        for (int i = 0; i < items.size(); i++) {
            String[] nameTimeStamp = items.get(i).getID().split(" - ");
            if (items.get(i).getAvailable()) {
                inOut = "Checked In";
            } else {
                inOut = "Checked Out";
            }
            list.add(new Log(items.get(i).getReference(), items.get(i)
                    .getName(), nameTimeStamp[0], nameTimeStamp[1], inOut));
        }
        return list;
    }

    /**
     * @param search
     * @param fileName
     * @return
     */
    public ArrayList<Log> searchLog(String search, String fileName) {
        // get list of logs
        ArrayList<Log> list = this.getList(fileName);

        // make list to be returned
        ArrayList<Log> returned = new ArrayList<Log>();

        // make string arraylist
        ArrayList<String> keyword = new ArrayList<String>();

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
        for (int i = 0; i < list.size(); i++) {
            for (int e = 0; e < keyword.size(); e++) {
                if (list.get(i).getName().toLowerCase()
                        .contains(keyword.get(e).toLowerCase())) {
                    returned.add(list.get(i));
                    // prevent items from being added twice for containing two
                    // keywords
                    break;
                }
            }
        }

        return returned;

    }
}
