package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne(long id);

    Ingredient save(Ingredient ingredient);

}
