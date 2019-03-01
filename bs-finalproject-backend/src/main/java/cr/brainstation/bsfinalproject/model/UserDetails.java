package cr.brainstation.bsfinalproject.model;

import cr.brainstation.bsfinalproject.db.dtos.UserDTO;
import org.aspectj.weaver.ast.Or;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetails {

    private String id;
    private String email;
    private String name;
    private Set<Address> addresses;
    private Set<CreditCard> creditCards;

    public UserDetails() {
    }

    public UserDetails(UserDTO dto) {
        this.id = dto.getId();
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.addresses = dto.getAddresses().stream()
                .map(Address::new)
                .collect(Collectors.toSet());
        this.creditCards = dto.getCreditCards().stream()
                .map(CreditCard::new)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails user = (UserDetails) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }
}
