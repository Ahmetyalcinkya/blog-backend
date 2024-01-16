package com.blog.BlogBackend.repositories;

import com.blog.BlogBackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
