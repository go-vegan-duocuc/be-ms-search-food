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
@Relation(collectionRelation = "foodCategories")
@EqualsAndHashCode(callSuper=false)
public class FoodCategoryResource extends RepresentationModel<FoodCategoryResource> {
   
   private String id;
   private String categoryName;
   private int idCategory;
   
}
