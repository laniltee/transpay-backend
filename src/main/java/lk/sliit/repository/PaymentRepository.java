package lk.sliit.repository;

import lk.sliit.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
