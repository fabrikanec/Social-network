package socialDist.cassandra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@UserDefinedType
@AllArgsConstructor
@NoArgsConstructor
@Data
//@Table
public class Article {

//    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
//    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

//    @PrimaryKeyColumn(name = "title", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String title;

//    @PrimaryKeyColumn(name = "publisher", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private String publisher;

//    @Column
    private Set<String> tags = new HashSet<>();

    /*@Column
    private String text;*/

}
