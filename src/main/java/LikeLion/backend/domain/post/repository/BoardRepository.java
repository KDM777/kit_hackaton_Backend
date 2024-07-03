package LikeLion.backend.domain.post.repository;

import LikeLion.backend.domain.post.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> { }
