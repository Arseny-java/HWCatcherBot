import io.restassured.http.Cookie;
import io.restassured.response.ValidatableResponse;
import model.Response;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.Utils;

import static utils.Utils.*;

public class Main {
    private static final String LOGIN = "gwentsenya@gmail.com";
    private static final String PASSWORD = "Reactor19101988";
    private static final String REMEMBER = "1";
    private static final String X_AUTHENTICATION_TOKEN = "http_x_authentication";

    public static void main(String[] args) throws TelegramApiException {
        Response responseObject;
        Utils utils = new Utils();
        ValidatableResponse authResponse = utils.getAuthResponse(LOGIN, PASSWORD, REMEMBER);
        Cookie authToken = authResponse.extract().detailedCookie(X_AUTHENTICATION_TOKEN);
        while (true) {
            String response = utils.goReviewTaskFirstPage(authToken);
            responseObject = getResponseObject(response);

            if (responseObject.getCurrent_page() == responseObject.getTotal_pages()) {
                utils.takeTaskToReview(authToken, responseObject);
            } else {
                String responseFromLastPage = utils.goReviewTaskLastPage(authToken, responseObject.getTotal_pages());
                responseObject = getResponseObject(responseFromLastPage);
                utils.takeTaskToReview(authToken, responseObject);
            }
        }
    }
}
