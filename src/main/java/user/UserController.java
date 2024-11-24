package user;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
public class UserController {


    private final UserDao userDao;


    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String frontPage() {
        return "Front page!";
    }

    @GetMapping("/home")
    public String home() {
        return "Api home url";
    }

    @GetMapping("/info")
    public String info(Principal principal) {
        String user = principal != null ? principal.getName() : "";

        return "Current user: " + user;
    }

    @GetMapping("/admin/info")
    public String adminInfo(Principal principal) {
        return "Admin user info: " + principal.getName();
    }

    @GetMapping("/users/{username}")
    @PreAuthorize("#username == authentication.name || hasRole('ROLE_ADMIN')")
    public User getUserByName(@PathVariable("username") String username) {
        return userDao.getUserByUserName(username);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<String> getUsers() {
        return userDao.getAllUsers();
    }

    @GetMapping("/version")
    public String version() {return "?";}
}