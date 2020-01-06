package com.sc.per.time_line.entity;

import java.util.List;

public class TopAndListViewEntity {

    private ListViewBean listView;
    private List<TopImageScrollBean> topImageScroll;

    public ListViewBean getListView() {
        return listView;
    }

    public void setListView(ListViewBean listView) {
        this.listView = listView;
    }

    public List<TopImageScrollBean> getTopImageScroll() {
        return topImageScroll;
    }

    public void setTopImageScroll(List<TopImageScrollBean> topImageScroll) {
        this.topImageScroll = topImageScroll;
    }

    public static class ListViewBean {

        private int pageNum;
        private int pageSize;
        private int size;
        private Object orderBy;
        private int startRow;
        private int endRow;
        private int total;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private int firstPage;
        private int lastPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Object getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(Object orderBy) {
            this.orderBy = orderBy;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {

            private String id;
            private Object userName;
            private Object thematicUrl;
            private String title;
            private String data;
            private Object comments;
            private Object likenum;
            private Object userLikeNum;
            private Object firstMenu;
            private Object subMenu;
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

            public Object getUserName() {
                return userName;
            }

            public void setUserName(Object userName) {
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

    public static class TopImageScrollBean {
        /**
         * id : 27
         * userName : null
         * thematicUrl : null
         * title : 新垣结衣图片
         * data : <p><img src="http://10.233.1.241:8088/image/522a8756-4597-4169-8c23-1e2f18b884bc.jpg" style="max-width:100%;"><img src="http://10.233.1.241:8088/image/d9190f8c-3a93-4f3b-a967-a3e2e3deedb4.jpg" style="max-width: 100%;"><br></p><p><img src="http://10.233.1.241:8088/image/4633c316-187b-42cf-89ab-cee25754d2eb.jpg" style="max-width:100%;"><img src="http://10.233.1.241:8088/image/47763402-9fd1-44fc-8bf9-0cf39737672b.jpg" style="max-width: 100%;"><br></p>
         * comments : null
         * likenum : null
         * userLikeNum : null
         * firstMenu : null
         * subMenu : null
         * image : null
         * text : null
         * createTime : 2019-12-11 16:35
         * updateTime : 1571042132000
         */

        private String id;
        private Object userName;
        private Object thematicUrl;
        private String title;
        private String data;
        private Object comments;
        private Object likenum;
        private Object userLikeNum;
        private Object firstMenu;
        private Object subMenu;
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

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
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
