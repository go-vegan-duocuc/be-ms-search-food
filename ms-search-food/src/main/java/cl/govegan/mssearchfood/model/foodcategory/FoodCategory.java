package cl.govegan.mssearchfood.model.foodcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@Document(collection = "foodCategories")
public class FoodCategory {

    @Id
    private String id;
    private String categoryName;
    private int categoryCode;

}
