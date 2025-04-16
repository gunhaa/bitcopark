package bitcopark.library.entity.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String zipcode;
    private String detailed;

    public Address(String zipcode, String detailed) {
        this.zipcode = zipcode;
        this.detailed = detailed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(zipcode, address.zipcode) && Objects.equals(detailed, address.detailed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipcode, detailed);
    }
}
