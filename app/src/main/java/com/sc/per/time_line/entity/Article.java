package com.sc.per.time_line.entity;


import java.util.List;

public class Article {

    private int status;
    private Object msg;
    private DataBean data;

    @Override
    public String toString() {
        return "Article{" +
                "status=" + status +
                ", msg=" + msg +
                ", data=" + data +
                '}';
    }

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
        /**
         * pageNum : 1
         * pageSize : 5
         * size : 4
         * orderBy : null
         * startRow : 1
         * endRow : 4
         * total : 4
         * pages : 1
         * list : [{"id":"47","userName":"虹猫","thematicUrl":"/a582596b-faae-4c82-b43c-1b48e6dcd17a.jpg","title":"SpringBoot 单元测试","data":"1.添加依赖&lt;dependency&gt;   &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;   &lt;artifactId&gt;spring-boot-starter-test&lt;/artifactId&gt;   &lt;scope&gt;test&lt;/scope&gt;&lt;/dependency&gt;2.创建测试文件夹, 注意该测试java程序虽然和应用程序入口的java文件不在一个目录下, 但它们是同一个package. 3.创建测试类","comments":0,"likenum":0,"userLikeNum":0,"firstMenu":null,"subMenu":null,"image":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2073196509,1698944778&fm=179&app=42&f=JPEG?w=121&h=140","text":"后端","createTime":"2019-12-11 11:41","updateTime":1576035686000},{"id":"43","userName":"虹猫","thematicUrl":null,"title":"Mysql 存储过程，插入1000条","data":"DROP PROCEDUREIF EXISTS proc_initData;DELIMITER $CREATE PROCEDURE proc_initData ()BEGINDECLAREi INT DEFAULT 1 ;WHILE i &lt;= 1000 DOINSERT INTO 表名 (字段1，字段2，字段3) VALUES(参数1, 参数2, 参数3) ;SET i = i + 1 ;ENDWHILE ;END$CALL proc_initData () ;生成之后，注意删除了，不然可能会造成BUGdrop procedure proc_initData","comments":0,"likenum":0,"userLikeNum":0,"firstMenu":null,"subMenu":null,"image":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2073196509,1698944778&fm=179&app=42&f=JPEG?w=121&h=140","text":"后端","createTime":"2019-11-27 16:54","updateTime":1574844881000},{"id":"42","userName":"虹猫","thematicUrl":null,"title":"使用PageHelper分页之后，排序怎么办？","data":"使用PageHelper分页之后，mybatis在使用 ORDER BY create_time DESC 报错？根据日志信息我们可以看到查询的sql不对了，众所周知，order by 需要放置sql语句的最后但是我们在mybatis中放了order by之后，再使用pageHelper分页之后，limit 就会在最后位置。这个时候我们就要用到PageHelper.startPage(当前页,每页条数,排序)中的第三个参数参数排序写法：排序字段 空格 排序方式&nbsp; &nbsp;eg: create_time desc注意：create_time&nbsp;都是数据库中的字段，不是实体pojo的String orderBy = \"create_time desc\";PageHelper.startPage(pageNum,10,orderBy);","comments":0,"likenum":0,"userLikeNum":0,"firstMenu":null,"subMenu":null,"image":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2073196509,1698944778&fm=179&app=42&f=JPEG?w=121&h=140","text":"后端","createTime":"2019-11-22 16:17","updateTime":1574410626000},{"id":"41","userName":"虹猫","thematicUrl":null,"title":"sql语句查询一条数据的上一条数据和下一条数据","data":"1.查询上一条数据select * from tbContent where id=(select max(id) from tbContent where id&lt;searchId)2.查询下一条数据select * from tbContent where id=(select min(id) from tbContent where id&gt;searchId)3.查询上一条和下一条数据select * from tbContent where id in((select max(id) from tbContent where id&lt; searchId), (select min(id) from tbContent where id&gt; searchId))","comments":0,"likenum":1,"userLikeNum":0,"firstMenu":null,"subMenu":null,"image":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2073196509,1698944778&fm=179&app=42&f=JPEG?w=121&h=140","text":"后端","createTime":"2019-11-21 10:38","updateTime":1574303912000}]
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 5
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * firstPage : 1
         * lastPage : 1
         */

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
            /**
             * id : 47
             * userName : 虹猫
             * thematicUrl : /a582596b-faae-4c82-b43c-1b48e6dcd17a.jpg
             * title : SpringBoot 单元测试
             * data : 1.添加依赖&lt;dependency&gt;   &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;   &lt;artifactId&gt;spring-boot-starter-test&lt;/artifactId&gt;   &lt;scope&gt;test&lt;/scope&gt;&lt;/dependency&gt;2.创建测试文件夹, 注意该测试java程序虽然和应用程序入口的java文件不在一个目录下, 但它们是同一个package. 3.创建测试类
             * comments : 0
             * likenum : 0
             * userLikeNum : 0
             * firstMenu : null
             * subMenu : null
             * image : https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2073196509,1698944778&fm=179&app=42&f=JPEG?w=121&h=140
             * text : 后端
             * createTime : 2019-12-11 11:41
             * updateTime : 1576035686000
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
    }
}
