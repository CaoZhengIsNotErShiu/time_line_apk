package com.sc.per.time_line.entity;


public class Article {
    /**
     * id : 27
     * userName : 虹猫
     * thematicUrl : http://10.233.1.241:8088/image/522a8756-4597-4169-8c23-1e2f18b884bc.jpg
     * title : 新垣结衣图片
     * data :
     * comments : 4
     * likenum : 2
     * userLikeNum : 0
     * firstMenu : null
     * subMenu : null
     * image : https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2073196509,1698944778&fm=179&app=42&f=JPEG?w=121&h=140
     * text : 个人日记
     * createTime : 2019-12-11 16:35
     * updateTime : 1571042132000
     */

    private String id;
    private String userName;
    private String thematicUrl;
    private String title;
    private String data;
    private int comments;
    private int likenum;
    private int userLikeNum;
    private Object firstMenu;
    private Object subMenu;
    private String image;
    private String text;
    private String createTime;
    private long updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getThematicUrl() {
        return thematicUrl;
    }

    public void setThematicUrl(String thematicUrl) {
        this.thematicUrl = thematicUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public int getUserLikeNum() {
        return userLikeNum;
    }

    public void setUserLikeNum(int userLikeNum) {
        this.userLikeNum = userLikeNum;
    }

    public Object getFirstMenu() {
        return firstMenu;
    }

    public void setFirstMenu(Object firstMenu) {
        this.firstMenu = firstMenu;
    }

    public Object getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(Object subMenu) {
        this.subMenu = subMenu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
