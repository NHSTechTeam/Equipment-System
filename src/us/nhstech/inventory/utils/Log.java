package us.nhstech.inventory.utils;

/**
 * @author Devin Matte
 * @author James Sonne
 * @version v0.2-Beta
 * @since 2016-02-11
 */

public class Log {
    private int ref;
    private String name, person, time, in;

    public Log(int ref, String name, String person, String time, String in) {
        this.ref = ref;
        this.person = person;
        this.name = name;
        this.time = time;
        this.in = in;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }


}
