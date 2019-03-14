package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.*;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;

    private IngredientRepository ingredientRepository;

    public JdbcTacoRepository(JdbcTemplate jdbcTemplate, IngredientRepository ingredientRepository) {
        this.jdbc = jdbcTemplate;
        this.ingredientRepository = ingredientRepository;
    }

    private List<Ingredient> convertToIngredients(List<String> modelIngredients){
        Map<String, Ingredient> dbIngredients = new HashMap<>();
        ingredientRepository.findAll().forEach(i-> dbIngredients.put(i.getId(),i));

        List<Ingredient> results = new ArrayList<>();
        for (String ingredientId: modelIngredients){
            results.add(dbIngredients.get(ingredientId));
        }

        return results;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);

        List<Ingredient> ingredients = convertToIngredients(taco.getIngredients());

        for(Ingredient ingredient: ingredients) {
            saveIngredientToTaco(ingredient, tacoId);
        }

        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());

        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco(name, createdAt) values (?,?)", Types.VARCHAR, Types.TIMESTAMP);

        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = preparedStatementCreatorFactory.newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(
            Ingredient ingredient, long tacoId) {
        jdbc.update(
                "insert into Taco_Ingredients (taco, ingredient)" +
                        " values (?,?)",
                tacoId, ingredient.getId()
        );
    }
}
