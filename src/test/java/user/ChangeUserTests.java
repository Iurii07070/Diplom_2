package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import praktikum.constants.RestApiConstants;
import praktikum.user.User;
import praktikum.user.UserAPI;
import praktikum.user.UserAssertions;
import praktikum.user.UserUtils;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Tests for user change")
public class ChangeUserTests {
    private UserAPI userAPI;
    private UserAssertions userAssertions;
    private User user;
    private String accessToken;
    @Before
    public void setUp() {
        RestAssured.baseURI = RestApiConstants.BASE_URI;
        userAPI = new UserAPI();
        userAssertions = new UserAssertions();
        user = UserUtils.getRandomCredentials();
        Response createUserResponse = userAPI.createUser(user);
        userAssertions.assertUserCreationSuccessful(createUserResponse);
        accessToken = createUserResponse.path("accessToken");
    }

    @After
    public void cleanUp() {
        if (accessToken != null && user != null) {
            Response deleteUserResponse = userAPI.deleteUser(user, accessToken);
            userAssertions.assertDeleteUserSuccess(deleteUserResponse);
        }
    }

    @Test
    @DisplayName("Check status code of change authorized user")
    public void changeAuthorizedUserTest() {
        User changedUser = UserUtils.getRandomCredentials();
        Response changeResponse = userAPI.changeUser(changedUser, accessToken);
        userAssertions.assertChangeUserResponse(changeResponse, changedUser);
        Response loginResponse = userAPI.loginUser(changedUser);
        userAssertions.assertLoginUserSuccess(loginResponse);
    }

    @Test
    @DisplayName("Check status code of change authorized user with existing email")
    public void changeAuthorizedUserWithExistingEmailTest() {
        User secondUser = UserUtils.getRandomCredentials();
        Response createSecondUserResponse = userAPI.createUser(secondUser);
        userAssertions.assertUserCreationSuccessful(createSecondUserResponse);

        User changedUser = new User(secondUser.getEmail(), user.getPassword(), user.getName());
        Response changeResponse = userAPI.changeUser(changedUser, accessToken);
        userAssertions.assertChangeUserWithExistingEmailFailure(changeResponse);

        String secondUserAccessToken = createSecondUserResponse.path("accessToken");
        Response deleteSecondUserResponse = userAPI.deleteUser(secondUser, secondUserAccessToken);
        userAssertions.assertDeleteUserSuccess(deleteSecondUserResponse);
    }

    @Test
    @DisplayName("Check status code of change unauthorized user")
    public void changeUnauthorizedUserTest() {
        User changedUser = UserUtils.getRandomCredentials();
        Response changeResponse = userAPI.changeUser(changedUser, "");
        userAssertions.assertChangeUnauthorizedUserResponse(changeResponse);
    }
}