package com.ahmedhossny61.booklisting;

public class Book {
    private String title;
    private String author;
    private String image_url;
    private String info_url;
    public Book(String mtitle,String mauthor,String mimageUrl,String murl){
        title=mtitle;
        author=mauthor;
        image_url=mimageUrl;
        info_url=murl;
    }

    public String getInfo_url() {
        return info_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
