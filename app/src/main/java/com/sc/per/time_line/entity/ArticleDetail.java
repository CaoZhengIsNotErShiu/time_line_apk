package com.sc.per.time_line.entity;

public class ArticleDetail {



    private int status;
    private Object msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private String id;
        private String userName;
        private Object thematicUrl;
        private String title;
        private String data;
        private Object comments;
        private Object likenum;
        private Object userLikeNum;
        private String firstMenu;
        private String subMenu;
        private Object image;
        private Object text;
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

        public Object getThematicUrl() {
            return thematicUrl;
        }

        public void setThematicUrl(Object thematicUrl) {
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

        public Object getComments() {
            return comments;
        }

        public void setComments(Object comments) {
            this.comments = comments;
        }

        public Object getLikenum() {
            return likenum;
        }

        public void setLikenum(Object likenum) {
            this.likenum = likenum;
        }

        public Object getUserLikeNum() {
            return userLikeNum;
        }

        public void setUserLikeNum(Object userLikeNum) {
            this.userLikeNum = userLikeNum;
        }

        public String getFirstMenu() {
            return firstMenu;
        }

        public void setFirstMenu(String firstMenu) {
            this.firstMenu = firstMenu;
        }

        public String getSubMenu() {
            return subMenu;
        }

        public void setSubMenu(String subMenu) {
            this.subMenu = subMenu;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public Object getText() {
            return text;
        }

        public void setText(Object text) {
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
}
