package cl.govegan.mssearchfood.repositories.foodrepository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cl.govegan.mssearchfood.models.food.Food;
import cl.govegan.mssearchfood.models.food.FoodCategory;

@Repository
public class CustomFoodRepositoryImpl implements CustomFoodRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomFoodRepositoryImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Food> findAllWithCategory(Pageable pageable) {
        logger.info("Finding all foods with categories...");
        try {
            LookupOperation lookupOperation = LookupOperation.newLookup()
                    .from("foodCategories")
                    .localField("categoryId")
                    .foreignField("_id")
                    .as("category");

            ProjectionOperation projectOperation = Aggregation
                    .project("name", "categoryId", "waterPercentage", "caloriesKcal", "proteinG", "totalFatG",
                            "carbohydratesG", "dietaryFiberG", "calciumMg", "phosphorusMg", "ironMg", "thiamineMg",
                            "riboflavinMg", "niacinMg", "vitaminCmg", "vitaminAEquivRetinolMcg", "monounsaturatedFatG",
                            "polyunsaturatedFatG", "saturatedFatG", "cholesterolMg", "potassiumMg", "sodiumMg",
                            "zincMg", "magnesiumMg", "vitaminB6Mg", "vitaminB12Mcg", "folicAcidMcg", "folateEquivFDMcg",
                            "edibleFractionPercentage")
                    .and("category.categoryName").as("categoryName");

            Aggregation aggregation = Aggregation.newAggregation(
                    lookupOperation,
                    Aggregation.unwind("category", true),
                    projectOperation,
                    Aggregation.skip((long) pageable.getOffset()),
                    Aggregation.limit(pageable.getPageSize()));

            List<Food> results = mongoTemplate.aggregate(aggregation, "foods", Food.class).getMappedResults();
            results.forEach(food -> logger.info("Food: " + food));

            long total = mongoTemplate.count(new Query(), Food.class);
            return new PageImpl<>(results, pageable, total);
        } catch (DataAccessException e) {
            logger.error("Data access error while retrieving foods with categories", e);
            throw new RuntimeException("Data access error while retrieving foods with categories", e);
        } catch (Exception e) {
            logger.error("Error retrieving foods with categories", e);
            throw new RuntimeException("Error retrieving foods with categories", e);
        }
    }

    @Override
    public Page<Food> findByNameContainingWithCategory(String keywords, Pageable pageable) {
        try {
            LookupOperation lookupOperation = LookupOperation.newLookup()
                    .from("foodCategories")
                    .localField("categoryId")
                    .foreignField("_id")
                    .as("category");

            ProjectionOperation projectOperation = Aggregation
                    .project("name", "categoryId", "waterPercentage", "caloriesKcal", "proteinG", "totalFatG",
                            "carbohydratesG", "dietaryFiberG", "calciumMg", "phosphorusMg", "ironMg", "thiamineMg",
                            "riboflavinMg", "niacinMg", "vitaminCmg", "vitaminAEquivRetinolMcg", "monounsaturatedFatG",
                            "polyunsaturatedFatG", "saturatedFatG", "cholesterolMg", "potassiumMg", "sodiumMg",
                            "zincMg",
                            "magnesiumMg", "vitaminB6Mg", "vitaminB12Mcg", "folicAcidMcg", "folateEquivFDMcg",
                            "edibleFractionPercentage")
                    .and("category.categoryName").as("categoryName");

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("name").regex(keywords, "i")),
                    lookupOperation,
                    Aggregation.unwind("category", true),
                    projectOperation,
                    Aggregation.skip((long) pageable.getOffset()),
                    Aggregation.limit(pageable.getPageSize()));

            List<Food> results = mongoTemplate.aggregate(aggregation, "foods", Food.class).getMappedResults();
            results.forEach(food -> logger.info("Food: " + food));
            long total = mongoTemplate.count(new Query(Criteria.where("name").regex(keywords, "i")), Food.class);
            return new PageImpl<>(results, pageable, total);
        } catch (DataAccessException e) {
            logger.error("Data access error while retrieving foods by name with categories", e);
            throw new RuntimeException("Data access error while retrieving foods by name with categories", e);
        } catch (Exception e) {
            logger.error("Error retrieving foods by name with categories", e);
            throw new RuntimeException("Error retrieving foods by name with categories", e);
        }
    }

    @Override
    public Optional<Food> findByIdWithCategory(String id) {
        try {
            LookupOperation lookupOperation = LookupOperation.newLookup()
                    .from("foodCategories")
                    .localField("categoryId")
                    .foreignField("_id")
                    .as("category");

            ProjectionOperation projectOperation = Aggregation
                    .project("name", "categoryId", "waterPercentage", "caloriesKcal", "proteinG", "totalFatG",
                            "carbohydratesG", "dietaryFiberG", "calciumMg", "phosphorusMg", "ironMg", "thiamineMg",
                            "riboflavinMg", "niacinMg", "vitaminCmg", "vitaminAEquivRetinolMcg", "monounsaturatedFatG",
                            "polyunsaturatedFatG", "saturatedFatG", "cholesterolMg", "potassiumMg", "sodiumMg",
                            "zincMg",
                            "magnesiumMg", "vitaminB6Mg", "vitaminB12Mcg", "folicAcidMcg", "folateEquivFDMcg",
                            "edibleFractionPercentage")
                    .and("category.categoryName").as("categoryName");

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("_id").is(new ObjectId(id))),
                    lookupOperation,
                    Aggregation.unwind("category", true),
                    projectOperation);

            List<Food> results = mongoTemplate.aggregate(aggregation, "foods", Food.class).getMappedResults();
            results.forEach(food -> logger.info("Food: " + food));
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (DataAccessException e) {
            logger.error("Data access error while retrieving food by ID with category", e);
            throw new RuntimeException("Data access error while retrieving food by ID with category", e);
        } catch (Exception e) {
            logger.error("Error retrieving food by ID with category", e);
            throw new RuntimeException("Error retrieving food by ID with category", e);
        }
    }

    @Override
    public List<FoodCategory> findAllCategories() {
        try {
            List<FoodCategory> categories = mongoTemplate.findAll(FoodCategory.class);
            categories.forEach(category -> logger.info("Category: " + category));
            return categories;
        } catch (DataAccessException e) {
            logger.error("Data access error while retrieving all categories", e);
            throw new RuntimeException("Data access error while retrieving all categories", e);
        } catch (Exception e) {
            logger.error("Error retrieving all categories", e);
            throw new RuntimeException("Error retrieving all categories", e);
        }
    }
}