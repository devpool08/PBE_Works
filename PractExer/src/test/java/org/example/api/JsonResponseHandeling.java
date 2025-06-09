package org.example.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import lombok.*;
import org.apache.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.*;

@Data
@ToString
class User {
    private int id;
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String avatar;
}

public class JsonResponseHandeling {
    private static final String URL = "https://reqres.in/api/users?page=2";

    @SneakyThrows
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(HttpStatus.SC_OK).build();
        //List of users
        List<User> list = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .jsonPath()
                .getList("data", User.class);
        System.out.println("list of all users : ");
        list.forEach(System.out::println);
        System.out.println();

        //Single user
        User user = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .jsonPath()
                .getObject("data.find{it.id==9}", User.class);
        System.out.println("Single user: ");
        System.out.println(user.toString());
        System.out.println();

        //List of users with id >= 10
        List<User> userFilter = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .jsonPath()
                .getList("data.findAll{it.id>=10}", User.class);
        System.out.println("user filter : ");
        userFilter.forEach(System.out::println);
        System.out.println();

        //First user
        Object obj = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .path("data[1]");
        User user1 = objectMapper.readValue(objectMapper.writeValueAsString(obj), User.class);
        System.out.println("user1 : ");
        System.out.println(user1.toString());
        System.out.println();

        //List of users with id <= 9
        List<User> list1 = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .jsonPath()
                .getList("data.findAll{it.id<=9}", User.class);
        System.out.println("list1 : ");
        list1.forEach(System.out::println);
        System.out.println();

        //List of email of all users
        List<String> emailList = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .jsonPath()
                .getList("data.email", String.class);
        System.out.println("emailList : ");
        emailList.forEach(System.out::println);
        System.out.println();

        //List all email whose id is greater than 94
        List<String> emailList1 = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .jsonPath()
                .getList("data.findAll{it.id>9}.email", String.class);
        System.out.println("emailList1 : ");
        emailList1.forEach(System.out::println);
        System.out.println();

        //User with first_name 'Lindsay'
        User lindsay = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract()
                .jsonPath()
                .getObject("data.find { it.first_name == 'Lindsay' }", User.class);

        System.out.println("User with first_name 'Lindsay': " + lindsay);
        System.out.println();

        // User with last_name starts with 'F'
        List<User> filterUsers = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .jsonPath()
                .getList("data.findAll{it.last_name.startsWith('F')}", User.class);
        System.out.println("last name starts with F :");
        filterUsers.forEach(System.out::println);
        System.out.println();

        //List of users last name starts with F and id >= 9
        List<User> usersDoubleFilter = given()
                .when()
                .get(URL)
                .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .jsonPath()
                .getList("data.findAll{it.last_name.startsWith('F') && it.id >= 9}", User.class);
        System.out.println("List of users last name starts with F and id >= 9");
        usersDoubleFilter.forEach(System.out::println);
        System.out.println();

        //List of users with even ID
        List<String> evenIdAvatars = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract()
                .jsonPath()
                .getList("data.findAll { it.id % 2 == 0 }.avatar");

        System.out.println("Avatars of users with even ID: " + evenIdAvatars);
        System.out.println();

    }
}
