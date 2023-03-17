package user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {

    public User generic() {
        return new User("test-data@yandex.ru", "password", "Username");
    }
    public User random() {
        return new User(String.format(RandomStringUtils.randomNumeric(6) + "@yandex.ru"), "1234", "Mike");
    }
    public User emptyPassword() {
        return new User("test-data@yandex.ru", "", "Username");
    }
}
