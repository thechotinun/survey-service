package com.survey.v1.models.response;

import java.util.List;

public class PagedResponse<T> {
    private List<T> content;
    private PageInfo pageInfo;

    public PagedResponse(List<T> content, PageInfo pageInfo) {
        this.content = content;
        this.pageInfo = pageInfo;
    }

    // Getters
    public List<T> getContent() {
        return content;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    // Setters
    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class PageInfo {
        private int currentPage;
        private int size;
        private long totalItems;
        private int totalPages;
        private int countItemPerPage;

        public PageInfo(int currentPage, int size, long totalItems, int totalPages, int countItemPerPage) {
            this.currentPage = currentPage;
            this.size = size;
            this.totalItems = totalItems;
            this.totalPages = totalPages;
            this.countItemPerPage = countItemPerPage;
        }

        // Getters
        public int getCurrentPage() {
            return currentPage;
        }

        public int getSize() {
            return size;
        }

        public long getTotalItems() {
            return totalItems;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getCountItemPerPage() {
            return countItemPerPage;
        }

        // Setters
        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setTotalItems(long totalItems) {
            this.totalItems = totalItems;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public void setCountItemPerPage(int countItemPerPage) {
            this.countItemPerPage = countItemPerPage;
        }
    }
}
