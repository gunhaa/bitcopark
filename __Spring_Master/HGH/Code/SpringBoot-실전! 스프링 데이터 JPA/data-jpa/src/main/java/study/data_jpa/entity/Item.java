package study.data_jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    // persist를 한 후 id값이 들어간다.
}
