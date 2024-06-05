package cl.govegan.mssearchfood.HATEOAS;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "foods")
@EqualsAndHashCode(callSuper=false)
public class FoodResource extends RepresentationModel<FoodResource> {
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
    private String categoryId;
    private String categoryName;
}