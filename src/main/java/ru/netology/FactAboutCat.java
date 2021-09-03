package ru.netology;

public class FactAboutCat {
    private final String id;
    private final String text;
    private final String type;
    private final String user;
    private final Integer upvotes;

    public FactAboutCat(String id, String text, String type, String user, Integer upvotes) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.user = user;
        this.upvotes = upvotes;
    }

    public boolean hasUpvotes() {
        return upvotes != null && upvotes > 0;
    }

    @Override
    public String toString() {
        return "FactAboutCat{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", user='" + user + '\'' +
                ", upvotes=" + upvotes +
                '}';
    }
}
