package me.debugjoker.sell.repository;

import me.debugjoker.sell.domain.ProductCategory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Mengwei Zhang
 * @create: 2018-08-12 12:04
 **/
@CacheConfig(cacheNames = "ProductCategory")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    @Cacheable
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}