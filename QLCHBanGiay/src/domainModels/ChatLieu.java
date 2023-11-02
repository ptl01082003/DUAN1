package domainModels;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class ChatLieu {

    private UUID id;

    private int maChatLieu;
    

    private String chatLieuThan;

    private String chatLieuDe;

}
