package com.MyProject.ToyWorld.repository;

import com.MyProject.ToyWorld.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    @Query(value = "SELECT * FROM categories c where c.category_id = (?1)", nativeQuery = true)
//    Optional<Category> findByCategoryId(Long id);
    Optional<Category> findByCategoryName(String categoryName);

}
