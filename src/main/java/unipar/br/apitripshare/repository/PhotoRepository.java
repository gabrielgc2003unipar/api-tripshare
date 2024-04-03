package unipar.br.apitripshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unipar.br.apitripshare.entities.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
