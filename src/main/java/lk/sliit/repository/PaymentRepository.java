package lk.sliit.repository;

import lk.sliit.constants.CustomQueries;
import lk.sliit.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Lanil Marasinghe on 12-Nov-17.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /**
     * This method is used to get all the payments of a given Token
     * @param tokenId Id value of a Token
     * @return List of Payment objects which are referenced by a given Token
     */
    List<Payment> getAllByTokenId(Long tokenId);

    /**
     * Get total amount paid as recharges for a given customer
     * @param id Id value of a Customer
     * @return List of Customer objects with the total amount paid as a JSON array
     */
    @Query(value = CustomQueries.GET_PAYMENT_SUM, nativeQuery = true)
    List<Object> getPaymentSum(@Param("id") Long id);
}
