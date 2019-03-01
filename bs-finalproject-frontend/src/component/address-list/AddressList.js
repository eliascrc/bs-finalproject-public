import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';

import './AddressList.scss';
import { editAddress, createNewAddress, deleteAddress } from '../../services/AddressService';
import { Auth } from 'aws-amplify';
import { logout } from '../../redux/actions/UserActions';
import { clearBag } from '../../redux/actions/ShoppingBagActions';

class AddressList extends Component {

  constructor(props) {
    super(props);

    this.state = {
      addressMessage: '',
      newAddress: null,
      error: false,
    }
  }

  handleAddressChange = (e, index) => {
    e.preventDefault();

    const { editAddressLocal } = this.props;
    editAddressLocal(index, e.target.value);
  }

  handleNewAddressChange = (e) => {
    e.preventDefault();

    const newAddress = { ...this.state.newAddress };
    newAddress.address = e.target.value;
    this.setState({ newAddress: newAddress });
  }

  handleStartNewAddress = (e) => {
    e.preventDefault();

    const newAddress = { address: '' };
    this.setState({ newAddress: newAddress });
  }

  handleSaveAddress = (e, index) => {
    e.preventDefault();
    const { loggedUser, onLogout, addresses } = this.props;

    editAddress(addresses[index], loggedUser)
      .then(res => {
        if (!res.ok) { throw res }
        return res.json();
      })
      .then(res => {
        this.setState({
          addressMessage: 'Address saved succesfully',
          error: false,
        })
      })
      .catch(err => {
        if (err.status === 401) {
          Auth.signOut()
            .then(data => onLogout())
            .catch(err => {
              console.error('Unexpected error while loggin out');
              console.error(err);
            });
        } else {
          this.setState({
            addressMessage: 'There was an error while saving the address',
            error: true,
          })
          console.error(err);
        }
      })
  }

  handleCreateNewAddress = (e) => {
    e.preventDefault();
    const { loggedUser, onLogout, saveNewAddressLocal } = this.props;
    const { newAddress } = this.state;

    createNewAddress(newAddress, loggedUser)
      .then(res => {
        if (!res.ok) { throw res }
        return res.json();
      })
      .then(res => {
        saveNewAddressLocal(res.response);
        this.setState({
          addressMessage: 'Address created succesfully',
          newAddress: null,
          error: false,
        })
      })
      .catch(err => {
        if (err.status === 401) {
          Auth.signOut()
            .then(data => onLogout())
            .catch(err => {
              console.error('Unexpected error while loggin out');
              console.error(err);
            });
        } else {
          this.setState({
            addressMessage: 'There was an error while creating the address',
            error: true,
          })
          console.error(err);
        }
      })
  }

  handleDeleteAddress = (e, index) => {
    e.preventDefault();
    const { loggedUser, onLogout, addresses, removeAddressLocal } = this.props;

    deleteAddress(addresses[index].id, loggedUser)
      .then(res => {
        if (!res.ok) { throw res }
        removeAddressLocal(index);
        this.setState({
          addressMessage: 'Address deleted succesfully',
          error: false,
        })
      })
      .catch(err => {
        if (err.status === 401) {
          Auth.signOut()
            .then(data => onLogout())
            .catch(err => {
              console.error('Unexpected error while loggin out');
              console.error(err);
            });
        } else {
          this.setState({
            addressMessage: 'There was an error while deleting the address',
            error: true,
          })
          console.error(err);
        }
      })
  }

  render() {
    const { addressMessage, newAddress, error } = this.state;
    const { addresses, selectable, handleSelect, addressSelected } = this.props;

    return (
      <React.Fragment>
        <div className={`${error ? "address-list__error-message" : "address-list__success-message"}`}>{addressMessage}</div>
        <div className="address-list__grid">
          {selectable ?
            <div className="address-list__select"></div>
            : null
          }
          <div className="address-list__city">City</div>
          <div className="address-list__address">Address</div>
        </div>
        {addresses.map((address, index) => (
          <div className="address-list__grid" key={address.id}>
            {selectable ?
              <button
                disabled={addressSelected === index}
                onClick={(e) => handleSelect(e, index)} className="address-list__select">
                {addressSelected === index ? <i className="far fa-check-square"></i> : <i className="far fa-square"></i>}
              </button>
              :
              null
            }
            <div className="address-list__city"><strong>Guadalupe</strong></div>
            <textarea value={address.address} onChange={(e) => this.handleAddressChange(e, index)}
              className="address-list__address address-list__address--textarea" name={`Address ${address.index}`} id={`address-${address.index}`} />
            <button onClick={(e) => this.handleSaveAddress(e, index)} className="address-list__save">
              <i className="fas fa-save"></i>
            </button>
            <button onClick={(e) => this.handleDeleteAddress(e, index)} className="address-list__delete">
              <i className="fas fa-trash-alt"></i>
            </button>
          </div>
        ))}
        {newAddress ?
          <div className="address-list__grid">
            <div className="address-list__city"><strong>Guadalupe</strong></div>
            <textarea value={newAddress.address} onChange={(e) => this.handleNewAddressChange(e)}
              className="address-list__address  address-list__address--textarea" name="New Address" id="new-address" />
            <button onClick={(e) => this.handleCreateNewAddress(e)} className="address-list__save">
              <i className="fas fa-save"></i>
            </button>
          </div>
          :
          <button onClick={(e) => { this.handleStartNewAddress(e) }} className="address-list__add">
            <i className="fas fa-home"></i> Add an address
          </button>
        }
      </React.Fragment>
    )
  }
}

const mapStateToProps = state => ({
  loggedUser: state.userReducer.loggedUser,
});

const mapDispatchToProps = dispatch => ({
  onLogout: () => { dispatch(logout()); dispatch(clearBag()) },
});

export default withRouter(connect(
  mapStateToProps,
  mapDispatchToProps
)(AddressList));