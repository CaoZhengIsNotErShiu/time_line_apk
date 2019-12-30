package com.sc.per.time_line.entity;

import java.util.List;

public class MenuBean {

    private int status;
    private Object msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String menuId;
        private String menuText;
        private String menuUrl;
        private String parentId;
        private String menuLevel;

        private String createTime;
        private List<ChildrenBean> children;

        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
        }

        public String getMenuText() {
            return menuText;
        }

        public void setMenuText(String menuText) {
            this.menuText = menuText;
        }

        public String getMenuUrl() {
            return menuUrl;
        }

        public void setMenuUrl(String menuUrl) {
            this.menuUrl = menuUrl;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getMenuLevel() {
            return menuLevel;
        }

        public void setMenuLevel(String menuLevel) {
            this.menuLevel = menuLevel;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "menuId='" + menuId + '\'' +
                    ", menuText='" + menuText + '\'' +
                    ", menuUrl='" + menuUrl + '\'' +
                    ", parentId='" + parentId + '\'' +
                    ", menuLevel='" + menuLevel + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", children=" + children +
                    '}';
        }

        public static class ChildrenBean {

            private String menuId;
            private String menuText;
            private String menuUrl;
            private String parentId;
            private String menuLevel;
            private String createTime;
            private List<?> children;

            public String getMenuId() {
                return menuId;
            }

            public void setMenuId(String menuId) {
                this.menuId = menuId;
            }

            public String getMenuText() {
                return menuText;
            }

            public void setMenuText(String menuText) {
                this.menuText = menuText;
            }

            public String getMenuUrl() {
                return menuUrl;
            }

            public void setMenuUrl(String menuUrl) {
                this.menuUrl = menuUrl;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getMenuLevel() {
                return menuLevel;
            }

            public void setMenuLevel(String menuLevel) {
                this.menuLevel = menuLevel;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }
        }
    }
}
