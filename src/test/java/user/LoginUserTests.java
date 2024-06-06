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

@DisplayName("Tests for user log in")
public class LoginUserTests {
    private UserAPI userAPI;
    private UserAssertions userAssertions;
    private User user;
    private User userLogin;
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

        userLogin = new User(user.getEmail(), user.getPassword(), user.getName());
    }

    @After
    public void cleanUp() {
        if (accessToken != null && user != null) {
            Response deleteUserResponse = userAPI.deleteUser(user, accessToken);
            userAssertions.assertDeleteUserSuccess(deleteUserResponse);
        }
    }

    @Test
    @DisplayName("Check status code of login user")
    public void loginUserTest() {
        Response loginResponse = userAPI.loginUser(userLogin);
        userAssertions.assertLoginUserSuccess(loginResponse);
    }

    @Test
    @DisplayName("Check status code of login user with wrong email")
    public void loginUserWithWrongEmailTest() {
        userLogin.setEmail("1234" + userLogin.getEmail());
        Response loginResponse = userAPI.loginUser(userLogin);
        userAssertions.assertLoginUserFailure(loginResponse);
    }

    @Test
    @DisplayName("Check status code of login user with wrong password")
    public void loginUserWithWrongPasswordTest() {
        userLogin.setPassword(userLogin.getPassword() + "1234");
        Response loginResponse = userAPI.loginUser(userLogin);
        userAssertions.assertLoginUserFailure(loginResponse);
    }

    @Test
    @DisplayName("Check status code of logout user")
    public void logoutUserTest() {
        Response loginResponse = userAPI.loginUser(userLogin);
        userAssertions.assertLoginUserSuccess(loginResponse);
        String refreshToken = loginResponse.path("refreshToken");
        Response logoutResponse = userAPI.logoutUser(refreshToken);
        userAssertions.assertLogoutUserSuccess(logoutResponse);
    }
}
