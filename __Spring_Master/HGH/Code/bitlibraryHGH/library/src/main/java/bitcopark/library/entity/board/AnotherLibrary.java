package bitcopark.library.entity.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AnotherLibrary {

    @Id
    @GeneratedValue
    @Column(name = "anotherLibrary_id")
    private Long id;

    private String libraryAddress;

    private String libraryLatitude;

    private String libraryLongitude;

    @OneToOne(mappedBy = "anotherLibrary", fetch = FetchType.LAZY)
    private Board board;
}
