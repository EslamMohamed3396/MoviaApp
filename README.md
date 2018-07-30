<h1> Popular Movies App </h>

This App Show The Description For The Movies,From Api The MovieDB 

<h4> User Interface - Layout: </h>

-UI contains an element (settings menu) to toggle the sort order of the movies by: most popular, highest rated.

-Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.

-UI contains a screen for displaying the details for a selected movie.

-Movie Details layout contains title, release date, movie poster, vote average, and OverView.

-Movie Details layout contains a section for displaying trailer videos and user reviews.

<h4> User Interface - Function : </h>

-When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.

-When a movie poster thumbnail is selected, the movie details screen is launched.

-When a trailer is selected, app uses a webView to launch the trailer.

-In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite.

<h4> Network API Implementation : </h>

-In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.

-App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.

-App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

<h4> Data Persistence : </h>

It is made with latest Android Architecture Components like LiveData, and Room 


<h4> Steps to run the app : </h4>

In order to run this Application , an API KEY is Needed which can be obtained from <a href="https://www.themoviedb.org/">MovieDB</a> for API Calling.
Please add the API_KEY in gradle.properties file for build.gradle

