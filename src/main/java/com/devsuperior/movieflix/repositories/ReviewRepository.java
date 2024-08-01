package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	@Query("SELECT new com.devsuperior.movieflix.dto.ReviewDTO(r.id, r.text, m.id, u.id, u.name, u.email) " +
		       "FROM Review r " +
		       "JOIN r.movie m " +
		       "JOIN r.user u " +
		       "WHERE m.id = :idMovie")
	List<ReviewDTO> findByReviewMovieId(@Param("idMovie") Long id);
	
}
