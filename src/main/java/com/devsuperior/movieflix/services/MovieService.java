package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieCardProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		return new MovieDetailsDTO(movie);
	}

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllPageMovieByGenre(Long genreId, Pageable pageable) {
	    Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.asc("TITLE")));
	    Page<MovieCardProjection> movieProjections = movieRepository.searchMoviesByGenre(genreId, sortedPageable);

	    List<MovieCardDTO> movieDTOs = movieProjections
	            .getContent().stream()
	            .map(projection -> new MovieCardDTO(projection.getId(), projection.getTitle(),
	                    projection.getSubTitle(), projection.getMovieYear(), projection.getImgUrl()))
	            .collect(Collectors.toList());
	    
	    return new PageImpl<>(movieDTOs, sortedPageable, movieProjections.getTotalElements());
	}

	public List<ReviewDTO> findByReviewMovieId(Long id) {
		List<ReviewDTO> list=reviewRepository.findByReviewMovieId(id);
		return list;
	}

}
