 package rahmawati.eli.toco.kategories;

/**
 * Created by eli on 10/09/15.
 */
public class Item {
    private String folder;
    private Integer child;
    private String author;
    private String createdate;
    private String editdate;
    private String img;

    public Item(String img,String folder, Integer child, String editdate) {
        this.folder = folder;
        this.img = img;
        this.child = child;
        this.editdate = editdate;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {

    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getEditdate() {
        return editdate;
    }

    public void setEditdate(String editdate) {
        this.editdate = editdate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return folder;
    }
}
