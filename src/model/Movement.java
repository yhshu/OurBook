package model;

public class Movement {
    String nickname; //用户名
    String comment; // 评论
    int thumb;     //点赞数？？存疑

    public Movement(String n,String c,int t){
        this.nickname = n;
        this.comment = c;
        this.thumb = t;
    }

    public String getNickname() {
        return name;
    }

    public void setNickname(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }
}
