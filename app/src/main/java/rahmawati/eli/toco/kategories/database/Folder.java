package rahmawati.eli.toco.kategories.database;

/**
 * Created by eli on 08/09/15.
 */
public class Folder {
    private String folder;
    private Integer child;
    private String author;
    private String createdate;
    private String editdate;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
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

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return folder;
    }
}
