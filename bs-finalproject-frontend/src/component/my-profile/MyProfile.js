import React, { Component } from 'react';
import { withRouter, Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

import './MyProfile.scss';
import { getUserDetails } from '../../services/UserService';
import { logout } from '../../redux/actions/UserActions';
import { clearBag } from '../../redux/actions/ShoppingBagActions';
import { capitalizeName } from '../../utils/commons';
import { Auth } from 'aws-amplify';
import AddressList from '../address-list/AddressList';
import { getHistory } from '../../services/OrderService';
import PaymentList from '../payment-list/PaymentList';

class MyProfile extends Component {

  constructor(props) {
    super(props);

    this.state = {
      name: '',
      email: '',
      addresses: [],
      history: [],
      payments: [],
    }
  }

  editAddressLocal = (index, address) => {
    const { addresses } = this.state;
    const newAddresses = [...addresses];
    newAddresses[index].address = address;
    this.setState({ addresses: newAddresses });
  }

  saveNewAddressLocal = (newAddress) => {
    const { addresses } = this.state;
    const newAddresses = [...addresses];
    newAddresses.push(newAddress);
    this.setState({ addresses: newAddresses });
  }

  removeAddressLocal = (index) => {
    const { addresses } = this.state;
    const newAddresses = [...addresses];
    newAddresses.splice(index, 1);
    this.setState({ addresses: newAddresses });
  }

  saveNewPaymentLocal = (newPayment) => {
    const { payments } = this.state;
    const newPayments = [...payments];
    newPayments.push(newPayment);
    this.setState({ payments: newPayments });
  }

  removePaymentLocal = (index) => {
    const { payments } = this.state;
    const newPayments = [...payments];
    newPayments.splice(index, 1);
    this.setState({ payments: newPayments });
  }

  loadUserDetails = () => {
    const { loggedUser, onLogout } = this.props;

    getUserDetails(loggedUser)
      .then(res => {
        if (!res.ok) { throw res }
        return res.json();
      })
      .then(res => {
        this.setState({
          name: res.response.name,
          email: res.response.email,
          addresses: res.response.addresses,
          payments: res.response.creditCards,
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
          console.error('Unexpected error while fetching user data');
          console.error(err);
        }
      })
  }

  loadHistory = () => {
    const { loggedUser, onLogout } = this.props;

    getHistory(loggedUser)
      .then(res => {
        if (!res.ok) { throw res }
        return res.json();
      })
      .then(res => {
        this.setState({
          history: res.response,
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
          console.error('Unexpected error while fetching user data');
          console.error(err);
        }
      })
  }

  componentDidMount() {
    this.loadUserDetails();
    this.loadHistory();
  }

  render() {
    const { name, email, addresses, history, payments } = this.state;
    const { loggedUser } = this.props;

    if (!loggedUser) {
      return <Redirect to="/" />
    }

    return (
      <div className="my-profile">

        <section className="my-profile__section">

          <div className="my-profile__content-container">

            <div className="my-profile__user-section">
              <div className="my-profile__profile-photo">
                <i className="fas fa-user-circle"></i>
              </div>

              <div className="my-profile__details">
                <div className="my-profile__name">{name}</div>
                <div className="my-profile__email">{email}</div>
              </div>
            </div>

            <div className="my-profile__user-section" >
              <h1 className="my-profile__address-heading">Delivery Addresses</h1>
              <AddressList
                addresses={addresses}
                editAddressLocal={this.editAddressLocal}
                saveNewAddressLocal={this.saveNewAddressLocal}
                removeAddressLocal={this.removeAddressLocal}
              />
            </div>

            <div className="my-profile__user-section" >
              <h1 className="my-profile__payment-heading">Payments</h1>
              <PaymentList
                payments={payments}
                saveNewPaymentLocal={this.saveNewPaymentLocal}
                removePaymentLocal={this.removePaymentLocal}
              />
            </div>

            <div className="my-profile__user-section">
            <h1 className="my-profile__history-heading">History</h1>
            <div className="my-profile__summary">
              <div className="my-profile__summary-product">Product</div>
              <div className="my-profile__summary-price">Price</div>
              <div className="my-profile__summary-quantity">Quantity</div>
              <div className="my-profile__summary-total">Total</div>
            </div>

            {history.map((productOrder, index) => (
              <div className="my-profile__summary" key={productOrder.id}>
                <div className="my-profile__summary-product">
                  <div className="my-profile__image-wrapper">
                    <img className="my-profile__image" src={productOrder.imageUrl} alt="" />
                  </div>
                  <div className="my-profile__product-details">
                    <div className="my-profile__product-name">{capitalizeName(productOrder.productName)}</div>
                    <div className="my-profile__product-size">Size: {productOrder.amount}</div>
                  </div>
                </div>
                <div className="my-profile__summary-price">${productOrder.price}</div>
                <div className="my-profile__summary-quantity">{productOrder.quantity}</div>
                <div className="my-profile__summary-total">${(productOrder.total)}</div>
              </div>
            ))}
            </div>

          </div>
        </section>
      </div>
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
)(MyProfile));