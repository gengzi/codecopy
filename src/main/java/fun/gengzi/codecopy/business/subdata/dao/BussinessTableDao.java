package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BussinessTableDao extends JpaRepository<BussinessTable, String> {

}
