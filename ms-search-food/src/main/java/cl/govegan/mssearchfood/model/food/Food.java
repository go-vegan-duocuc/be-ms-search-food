package cl.govegan.mssearchfood.model.food;

import cl.govegan.mssearchfood.model.foodcategory.FoodCategory;
import cl.govegan.mssearchfood.utils.jsonutils.IgnoreDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@Document(collection = "foods")
public class Food {
    @Id
    private String id;
    private String name;
    private double waterPercentage;
    private double caloriesKcal;
    private double proteinG;
    private double totalFatG;
    private double carbohydratesG;
    private double dietaryFiberG;
    private double calciumMg;
    private double phosphorusMg;
    private double ironMg;
    private double thiamineMg;
    private double riboflavinMg;
    private double niacinMg;
    private double vitaminCmg;
    private double vitaminAEquivRetinolMcg;
    private double monounsaturatedFatG;
    private double polyunsaturatedFatG;
    private double saturatedFatG;
    private double cholesterolMg;
    private double potassiumMg;
    private double sodiumMg;
    private double zincMg;
    private double magnesiumMg;
    private double vitaminB6Mg;
    private double vitaminB12Mcg;
    private double folicAcidMcg;
    private double folateEquivFDMcg;
    private double edibleFractionPercentage;

    @JsonDeserialize(using = IgnoreDeserializer.class)
    private String nameNormalized;

    @DBRef
    @JsonDeserialize(using = IgnoreDeserializer.class)
    private FoodCategory category;
}
