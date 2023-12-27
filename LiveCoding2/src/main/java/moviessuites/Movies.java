package moviessuites;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Movies extends TestBase {

    @Test(description = "Add a movie from top top list to a personal list")
    public void addMovieToAList() throws IOException {

        //Call TOP rated movies API
        Response topRatedAPIResponse = callTopRatedAPI();

        //Take a random movie ID
        int movieId = takeRandomMovieId(topRatedAPIResponse);

        //Add it to the list
        addMovieToList(movieId);

        //This is the spaghetti code I wrote during the session

        /*//Variable definition
        String endpoint = System.getProperty("url") + "/3/movie/top_rated";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZjJjZTJjYTUwZTQyNjg4YTRhNTJiMGNkMWFjZTJkZSIsInN1YiI6IjY1OGFmMmI0ZTAzOWYxNTZmOGJkZTVlNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.DSwDaCNsT7BtNOGCGDwTIYUrThr0tDdV2eeHG4grdyE";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put("Accept", "application/json");

        //Call top rated movies API
        Response topRatedResponse = given().headers(headers).when().get(endpoint);
        Assert.assertEquals(topRatedResponse.statusCode(), 200);

        //Deserialization of the response
        String topRatedResponseString = topRatedResponse.getBody().asString();
        JsonPath js = new JsonPath(topRatedResponseString);
        int page = js.get("page");
        String title = js.get("results[0].title");
        System.out.println("Page: " + page);
        System.out.println("Title: " + title);

        List<LinkedHashMap<String, Object>> results = js.get("results");
        System.out.println("Results: " +  results.toString());
        int randomNumber = (int) (Math.random()*19);
        int id = (Integer) results.get(randomNumber).get("id");
        System.out.println("Movie id is: " + id);

        //Section 2. Call add movie API

        //Headers and enpoint
        String addMovieToListEndpoint = "https://api.themoviedb.org/3/list/8284608/add_item";
        headers.put("content-type", "application/json");

        //Get payload
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/payloads/addMovieToList.json"));
        String payload = new String(bytes);

        String idString = String.valueOf(id);
        payload = payload.replace("changeThisMediaId", idString);
        System.out.println("Payload: " + payload);

        //Call add movie to list API
        Response addMovieToListResponse = given().headers(headers).body(payload).when().post(addMovieToListEndpoint);

        //Deserialization
        String addMovieToListResponseString = addMovieToListResponse.asString();
        System.out.println("Response: " + addMovieToListResponse.getBody().asString());
        JsonPath jsAddMovieToList = new JsonPath(addMovieToListResponseString);

        //Assertions
        Assert.assertEquals(addMovieToListResponse.statusCode(), 201);
        boolean success = jsAddMovieToList.getBoolean("success");
        Assert.assertEquals(success, true);*/

    }
}
