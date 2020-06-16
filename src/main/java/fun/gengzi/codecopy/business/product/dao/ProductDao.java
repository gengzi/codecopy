package fun.gengzi.codecopy.business.product.dao;

import fun.gengzi.codecopy.business.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    List<Product> findByType(String type);
}
