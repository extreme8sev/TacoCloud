package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.data.IngredientRepository;
import tacos.data.UserRepository;
import tacos.model.Ingredient;
import tacos.model.User;

import java.util.Arrays;
import java.util.List;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository,
                                        UserRepository userRepository,
                                        PasswordEncoder passwordEncoder){
        return args -> {
            List<Ingredient> ingredients = Arrays.asList(
                    new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                    new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                    new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                    new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                    new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                    new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                    new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                    new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                    new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                    new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

            ingredientRepository.saveAll(ingredients);

            User user = new User("renat",
                    passwordEncoder.encode("123"), "Renat Ishkaev", "Street", "City", "State", "Zip", "+1234567890");

            userRepository.save(user);
        };
    }

}
