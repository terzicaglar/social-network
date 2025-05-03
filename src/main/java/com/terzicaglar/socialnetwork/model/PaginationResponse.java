package com.terzicaglar.socialnetwork.model;

import java.util.List;

public class PaginationResponse<T> {
    private long totalItems;
    private long totalPages;
    private int currentPage;
    private int itemsPerPage;
    private List<T> data;

    // Constructors, Getters, Setters
    public PaginationResponse(long totalItems, int currentPage, int itemsPerPage, List<T> data) {
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.data = data;
        this.totalPages = (long) Math.ceil((double) totalItems / itemsPerPage);
    }

    public long getTotalItems() {
        return totalItems;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
