package org.realityfn.models.paging;

public class Paging {
    private int start;
    private int count;
    private long total;

    public Paging(int start, int count, long total) {
        this.start = start;
        this.count = count;
        this.total = total;
    }

    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
}