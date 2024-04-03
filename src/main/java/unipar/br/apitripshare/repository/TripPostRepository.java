package unipar.br.apitripshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unipar.br.apitripshare.entities.TripPost;

@Repository
public interface TripPostRepository extends JpaRepository<TripPost, Long> {

}
