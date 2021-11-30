package fun.gengzi.codecopy.business.product.dao;

import fun.gengzi.codecopy.business.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    /**
     * 根据类型查找产品列表
     *
     * @param type 类型
     * @return List<Product>
     */
    List<Product> findByType(String type);

    @Query("select id from Product")
    List<Integer> getAllId();


}
