package com.example.nfclibrary;

public class books {
    String category,name,url,author,description;
    public books(String categoty, String name, String url,String author, String description){
        this.category=categoty;
        this.name=name;
        this.url=url;
        this.author=author;
        this.description=description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
