package socialDist.cassandra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.UUID;

/**
 * Created by cezar on 5/18/17.
 */
@UserDefinedType
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {

    @Id
    private UUID event_id;

    private UUID user_id;

    private String name;

    private String text;

    private String subj;

}