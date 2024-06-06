# Stellar Burgers API Testing

## Task 2: API

You need to test the API endpoints for Stellar Burgers. The [API documentation](https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf) will be useful. It describes all the endpoints of the service. Only test those specified in the task. Everything else is just for context.

### Test Contents

#### User Creation:
1. Create a unique user.
2. Create a user who is already registered.
3. Create a user without filling in one of the required fields.

#### User Login:
1. Login with an existing user.
2. Login with incorrect username and password.

#### User Data Modification:
1. With authorization.
2. Without authorization.
3. For both situations, check that any field can be changed.
4. For an unauthorized user, also check that the system returns an error.

#### Order Creation:
1. With authorization.
2. Without authorization.
3. With ingredients.
4. Without ingredients.
5. With incorrect ingredient hash.

#### Retrieving User Orders:
1. Authorized user.
2. Unauthorized user.

### What Needs to Be Done

1. Create a separate repository for API tests.
2. Create a Maven project.
3. Add JUnit 4, RestAssured, and Allure.
4. Write tests.
5. Create an Allure report.

### How to Complete and Submit the Work

1. Create the project in IntelliJ IDEA, upload it to GitHub, push the `develop2` branch, and create a pull request. [Detailed instructions](https://github.com/your-repo/instructions).

### How Your Work Will Be Evaluated

- Tests for each endpoint are in a separate class.
- Tests run and pass.
- All specified tests are written.
- Tests check the response body and status code.
- All tests are independent.
- The `@Step` annotation is used for all test steps.
- Necessary test data is created before the test and deleted after it completes.
- An Allure report is created. The report is added to the pull request.
- Tests are in `test/java`.
- The `pom.xml` file contains nothing unnecessary.

### References

- [Stellar Burgers Website](https://stellarburgers.nomoreparties.site/)
- [API Documentation](https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf)

### How to run tests

`mvn clean test allure:report allure:serve`
