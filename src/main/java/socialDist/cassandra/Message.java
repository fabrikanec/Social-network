package socialDist.cassandra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.Date;
import java.util.UUID;

@UserDefinedType
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    @Id
    private UUID message_id;
    
    private UUID user_id;

    private String text;

    private Date date;

}