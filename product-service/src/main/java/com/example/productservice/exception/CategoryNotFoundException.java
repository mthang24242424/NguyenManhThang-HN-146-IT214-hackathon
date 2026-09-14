package com.example.productservice.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long categoryId) {
        super("Danh mục không tồn tại hoặc không hợp lệ");
    }
}
