package net.flow9.dcjt.firebase.model;

public class Post {

    private String documentId;
    private String title;
    private String contents;
    private int date;
    private String L_category, M_category;
    private String image;
    private String writer;
    private String mImageUrl;

    public Post(){

    }
    public Post(String title, String contents, int date, String l_category, String m_category, String image, String writer, String mImageUrl) {
        this.title = title;
        this.contents = contents;
        this.date = date;
        this.L_category = l_category;
        this.M_category = m_category;
        this.image = image;
        this.writer = writer;
        this.mImageUrl = mImageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void post() {

    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getL_category() {
        return L_category;
    }

    public void setL_category(String l_category) {
        L_category = l_category;
    }

    public String getM_category() {
        return M_category;
    }

    public void setM_category(String m_category) {
        M_category = m_category;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", L_category='" + L_category + '\'' +
                ", M_category='" + M_category + '\'' +
                ", date='" + date + '\'' +
                ", imgUri='" + image + '\'' +
                '}';
    }
}


