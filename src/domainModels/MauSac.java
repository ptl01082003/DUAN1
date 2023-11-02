
package domainModels;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class MauSac implements Serializable{

    private UUID id;

    private int maMS;
    
    private String tenMS;
    
}
