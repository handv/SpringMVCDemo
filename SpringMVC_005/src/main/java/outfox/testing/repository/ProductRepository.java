package han.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import han.testing.model.ProductEntity;

import java.util.List;

/**
 * Created by handv on 2016/5/12.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    // 定义查询
    // @Param注解用于提取参数
    @Query("select de from ProductEntity de where de.product=:qType")
    public ProductEntity findByProduct(@Param("qType") String product);
}

