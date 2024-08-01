package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieCardProjection;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	/*
	 * @Query("SELECT m FROM Movie m WHERE (:genre IS NULL OR m.genre = :genre)")
	 * Page<Movie> findMovieByGenre(@Param(value = "genre") Genre genre, Pageable
	 * pageable);
	 */
	@Query(
		    nativeQuery = true,
		    value = """
		        SELECT TB_MOVIE.ID AS id, TB_MOVIE.TITLE AS title, TB_MOVIE.SUB_TITLE AS subTitle, 
		               TB_MOVIE.MOVIE_YEAR AS movieYear, TB_MOVIE.IMG_URL AS imgUrl
		        FROM TB_MOVIE
		        WHERE (:genreId = 0 OR TB_MOVIE.GENRE_ID = :genreId)
		        ORDER BY TB_MOVIE.TITLE ASC
		    """,
		    countQuery = """
		        SELECT COUNT(*)
		        FROM TB_MOVIE
		        WHERE (:genreId = 0 OR TB_MOVIE.GENRE_ID = :genreId)
		    """
		)
		Page<MovieCardProjection> searchMoviesByGenre(@Param("genreId") Long genreId, Pageable pageable);



}
