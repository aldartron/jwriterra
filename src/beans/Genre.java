package beans;

import java.io.Serializable;

/**
 * Created by Aldartron on 08.04.2017.
 */
public class Genre implements Serializable{

    private String name;
    private long id;
    private String desc;

    public Genre() {}

    public Genre(String name, long id, String desc) {
        this.name = name;
        this.id = id;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
