package utils;

import bot.HWCatcherBot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAuth.AuthRequest;
import dataAuth.Event;
import io.restassured.http.Cookie;
import io.restassured.response.ValidatableResponse;
import model.HomeWork;
import model.Response;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static specification.Specification.requestAuthSpec;
import static specification.Specification.requestTakeTaskToReview;

public class Utils {
    static ObjectMapper mapper = new ObjectMapper();
    static Response responseObject;
    static HWCatcherBot bot = new HWCatcherBot();

    public Utils() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        bot.sendStart();
    }

    public ValidatableResponse getAuthResponse(String login, String password, String remember) {
        AuthRequest authRequest = new AuthRequest(login, password, remember);
        return given()
                .spec(requestAuthSpec())
                .body(authRequest)
                .when()
                .post("/backend/api/user/sign_in")
                .then()
                .statusCode(200);
    }

    public String goReviewTaskFirstPage(Cookie cookie) {
        return given()
                .urlEncodingEnabled(false)
                .cookie(cookie)
                .baseUri("https://netology.ru/backend/api/expert/homeworks")
                .queryParam("q%5Blesson_task_task_type_eq%5D", "1")
                .queryParam("q%5Bstatus_eq%5D", "0")
                .get()
                .then()
                .statusCode(200)
                .extract().body().jsonPath().prettify();
    }

    public String goReviewTaskLastPage(Cookie cookie, int page) {
        return given()
                .urlEncodingEnabled(false)
                .cookie(cookie)
                .baseUri("https://netology.ru/backend/api/expert/homeworks")
                .queryParam("page", page)
                .queryParam("q%5Blesson_task_task_type_eq%5D", "1")
                .queryParam("q%5Bstatus_eq%5D", "0")
                .get()
                .then()
                .statusCode(200)
                .extract().body().jsonPath().prettify();
    }

    public void takeTaskToReview(Cookie cookie, int id) {
        Event event = new Event("take_for_review");
        given()
                .cookie(cookie)
                .spec(requestTakeTaskToReview())
                .body(event)
                .when()
                .put("https://netology.ru/backend/api/expert/homeworks/" + id)
                .then()
                .statusCode(200);
    }

    public static Response getResponseObject(String response) {
        try {
            responseObject = mapper.readValue(response, Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return responseObject;
    }

    public void takeTaskToReview(Cookie cookie, Response responseObject) {
        List<HomeWork> homeworks = responseObject
                .getHomeworks()
                .stream()
                .filter(value -> value.getReviewer().isNull())
                .collect(Collectors.toList());
        if (!homeworks.isEmpty()) {
            for (HomeWork h : homeworks) {
                takeTaskToReview(cookie, h.getId());
                System.out.println("DONE");
                bot.sendAlert(h.getTask().get("title").asText());
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
