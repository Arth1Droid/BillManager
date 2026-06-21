package dev.arthdroid.billmanagerapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.arthdroid.billmanagerapp.models.Bill;
import dev.arthdroid.billmanagerapp.models.BillStatus;
import dev.arthdroid.billmanagerapp.models.Category;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>{
	
	
	List<Bill> findByStatus(BillStatus status);
	
    List<Bill> findByUserId(Long userId);
    
	@Query("""
			SELECT b FROM Bill b
			WHERE (:userId IS NULL OR b.user.id = :userId)
			AND (:category IS NULL OR b.category = :category)
			AND (:status IS NULL OR b.status = :status)
			""")
			List<Bill> findWithFilters(
			    @Param("userId") Long userId,
			    @Param("category") Category category,
			    @Param("status") BillStatus status
			);
	
}
