
package com.devsuperior.movieflix.projections;

public interface MovieCardProjection {
	
	    Long getId();
	    String getTitle();
	    String getSubTitle(); // Correspondente à coluna SUB_TITLE
	    Integer getMovieYear(); // Correspondente à coluna MOVIE_YEAR
	    String getImgUrl(); // Correspondente à coluna IMG_URL
	
}

