package cr.brainstation.bsfinalproject.model;

import cr.brainstation.bsfinalproject.db.dtos.AddressDTO;

/**
 * The address object used by the endpoints in order to receive and give information about an address.
 */
public class Address {

    private int id;
    private String address;

    public Address() {
    }

    public Address(AddressDTO dto) {
        this.id = dto.getId();
        this.address = dto.getAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
