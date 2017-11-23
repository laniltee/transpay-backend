package lk.sliit.repository;

import lk.sliit.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface TokenRepository extends JpaRepository<UserToken, Long> {
    UserToken findByCustomer_Id(long id);
}
