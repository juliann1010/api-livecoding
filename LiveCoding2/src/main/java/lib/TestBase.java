package lib;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static lib.Constants.*;

public class TestBase {

    protected String getToken(){
        return API_TOKEN;
    }

    protected Response callTopRatedAPI(){

        //Variable definition
        String endpoint = System.getProperty("url") + TOP_RATED_ENDPOINT;

        //Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Accept", "application/json");

        //Submit call
        Response topRatedResponse = given().headers(headers).when().get(endpoint);

        //Validations
        validateStatusCode(SUCCESS_GET_STATUS_CODE, topRatedResponse.getStatusCode());
        return topRatedResponse;

    }

    protected int takeRandomMovieId(Response topRatedResponse){
        JsonPath js = deserializeResponse(topRatedResponse);
        List<LinkedHashMap<String, Object>> results = js.get("results");
        int randomNumber = (int) (Math.random()*19);
        int id = (Integer) results.get(randomNumber).get("id");
        System.out.println("Movie id is: " + id);
        return id;
    }

    protected void addMovieToList(int movieId) throws IOException {

        //Variable definition
        String endpoint = System.getProperty("url") + ADD_MOVIE_TO_LIST_ENDPOINT;

        //Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Accept", "application/json");
        headers.put("content-type", "application/json");

        //Get payload and build payload
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/payloads/addMovieToList.json"));
        String payload = new String(bytes);
        String idString = String.valueOf(movieId);
        payload = payload.replace("changeThisMediaId", idString);
        System.out.println("Payload: " + payload);

        //Call add movie to list API
        Response addMovieToListResponse = given().headers(headers).body(payload).when().post(endpoint);
        JsonPath js = deserializeResponse(addMovieToListResponse);

        //Assertions
        validateStatusCode(SUCCES_POST_STATUS_CODE, addMovieToListResponse.getStatusCode());
        boolean success = js.getBoolean("success");
        Assert.assertEquals(success, true);


    }

    protected JsonPath deserializeResponse(Response response){
        String responseAsString = response.asString();
        return new JsonPath(responseAsString);
    }

    protected void validateStatusCode(int expectedStatusCode, int receivedStatusCode){
        Assert.assertEquals(expectedStatusCode, receivedStatusCode);
    }
}
