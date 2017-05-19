package socialDist.cassandra;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.UUID;

@UserDefinedType
@Data
public class Comment {

    @Id
    private UUID comment_id;

    private UUID user_id;

    private UUID article_id;

    private UUID event_id;

    private String text;

    public Comment(String text) {
        this.text = text;
    }

    
}