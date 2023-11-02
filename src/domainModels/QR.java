package domainModels;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class QR {

    private UUID id;

    private int maQR;
    
    private String anhQR;
}
