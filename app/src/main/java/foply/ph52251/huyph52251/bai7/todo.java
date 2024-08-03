package foply.ph52251.huyph52251.bai7;

import java.util.HashMap;

public class todo {
    private String id, title, content;

    public todo(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    //viet phuong thuc xu lý du lieu voi firebase
    public HashMap<String, Object> convert() {
        HashMap<String, Object> work = new HashMap<>();
        work.put("id", id);
        work.put("title", title);
        work.put("content", content);
        return work;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public todo() {
    }
}
