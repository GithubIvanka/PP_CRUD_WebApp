package app;

import app.config.DBConfig;
import app.model.User;
import app.service.UserService;
import app.service.UsersService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConfig.class);
        UserService service = context.getBean("service", UserService.class);

//        service.saveUser(new User("name1", "surname1", "email1"));
//        service.saveUser(new User("name2", "surname2", "email2"));
//        service.saveUser(new User("name3", "surname3", "email3"));

        List<User> users = service.getAllUsers();

        for (User user : users) {
            System.out.println(user.getName());
            System.out.println(user.getSurname());
            System.out.println(user.getEmail());
            System.out.println("--- --- ---");
        }
    }
}
