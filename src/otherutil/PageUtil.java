package otherutil;

public class PageUtil {

    /**
     * 当前页
     */
    private int index = 1;

    /**
     * 每页显示数
     */
    private int pageSize = 10;

    /**
     * 总页数
     */
    private int pageNumber;

    /**
     * 总行数
     */
    private int pageCount;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
        this.pageNumber = (pageCount + pageSize - 1) / pageSize;
    }
}
