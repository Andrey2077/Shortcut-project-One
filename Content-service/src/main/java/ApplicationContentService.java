import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@ComponentScan(basePackages = {"ru.shortcut.content.controllers", "ru.shortcut.content.services", "ru.shortcut.content.mapper"})
@EntityScan(basePackages = {"ru.shortcut.common.entity"})
@EnableJpaRepositories(basePackages = {"ru.shortcut.content.repositories"})

public class ApplicationContentService{

    public static void main(String[] args) throws Exception {

        SpringApplication.run(ApplicationContentService.class, args);

    }

}
