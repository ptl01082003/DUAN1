package domainModels;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class LoaiGiay {
    private UUID id;
    
    private int maLoaiGiay;
    
    private String tenLoaiGiay;
}
