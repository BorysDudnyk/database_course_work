import static org.junit.Assert.*;

import com.example.database.User;
import org.junit.Test;

public class UserTest {

    @Test
    public void testUser() {
        // Створення об'єкта User
        User user = new User("John", "Doe", "john.doe", "password123", "New York", "Male");

        // Перевірка значень полів
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe", user.getUserName());
        assertEquals("password123", user.getPassword());
        assertEquals("New York", user.getLocation());
        assertEquals("Male", user.getGender());

        // Зміна значень полів та перевірка
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setUserName("jane.smith");
        user.setPassword("newpassword456");
        user.setLocation("Los Angeles");
        user.setGender("Female");

        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane.smith", user.getUserName());
        assertEquals("newpassword456", user.getPassword());
        assertEquals("Los Angeles", user.getLocation());
        assertEquals("Female", user.getGender());
    }

    @Test
    public void testEmptyUser() {
        // Створення пустого об'єкта User
        User user = new User();

        // Перевірка, чи всі поля є порожніми (null)
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getUserName());
        assertNull(user.getPassword());
        assertNull(user.getLocation());
        assertNull(user.getGender());
    }
}
