package fun.gengzi.codecopy.business.shorturl.dao;

import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import fun.gengzi.codecopy.dao.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlGeneratorDao extends JpaRepository<Shorturl, Long> {
}
