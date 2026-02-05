package edu.aitu.oop3.common;

import java.util.List;

public class ListResult<T>{
    private final List<T> items;
    private final int totalCount;

    public ListResult(List<T> items) {
        this.items = items;
        this.totalCount = items == null ? 0 : items.size();
    }
    public List<T> getItems() {
        return items;
    }


    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public String toString() {
        return "ListResult{" + " totalCount=" + totalCount + ", items=" + items +  '}';
    }
    //121
}
