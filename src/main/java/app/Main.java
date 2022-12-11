package app;

import app.config.DBConfig;
import app.model.User;
import app.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConfig.class);
        UserService service = context.getBean("service", UserService.class);

        List<User> users = service.getAllUsers();

        for (User user : users) {
            System.out.println(user.getName());
            System.out.println(user.getSurname());
            System.out.println(user.getEmail());
            System.out.println("--- --- ---");
        }
    }
}
