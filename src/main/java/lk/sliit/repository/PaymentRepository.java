package lk.sliit.repository;

import lk.sliit.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> getAllByTokenId(Long tokenId);
}
