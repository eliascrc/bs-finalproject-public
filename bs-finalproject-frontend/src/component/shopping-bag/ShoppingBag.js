import React, { Component } from 'react';
import { withRouter, Link, Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

import './ShoppingBag.scss';
import { removeFromBag, editProduct, clearBag } from '../../redux/actions/ShoppingBagActions';
import { capitalizeName } from '../../utils/commons';
import { createOrder } from '../../services/OrderService';
import { logout } from '../../redux/actions/UserActions';
import { Auth } from 'aws-amplify';
import AddressList from '../address-list/AddressList';
import { getUserDetails } from '../../services/UserService';
import PaymentList from '../payment-list/PaymentList';

class ShoppingBag extends Component {

  constructor(props) {
    super(props);

    const amountsSelected = [];
    props.shoppingBag.forEach(product => {
      amountsSelected.push(product.amountSelected);
    });

    this.state = {
      amountsSelected: amountsSelected,
      addresses: [],
      payments: [],
      addressSelected: -1,
      paymentSelected: -1,
      successfulOrder: false,
      orderError: '',
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

  handleAddressSelect = (e, index) => {
    e.preventDefault();

    this.setState({ addressSelected: index });
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

  handlePaymentSelect = (e, index) => {
    e.preventDefault();

    this.setState({ paymentSelected: index });
  }

  handleAmountOnBlur = (e, index) => {
    e.preventDefault();
    const { amountsSelected } = this.state;
    const { shoppingBag, onEditProduct } = this.props;
    const newProduct = { ...shoppingBag[index] };
    const newAmountsSelected = [...amountsSelected];
    newAmountsSelected[index] = parseInt(amountsSelected[index]);

    if (amountsSelected[index] === '' || amountsSelected[index] < 1) {
      newAmountsSelected[index] = 1;
    } else if (amountsSelected[index] > newProduct.inStock) {
      newAmountsSelected[index] = newProduct.inStock
    }

    newProduct.amountSelected = newAmountsSelected[index];
    this.setState({ amountsSelected: newAmountsSelected });
    onEditProduct(newProduct, index);
  }

  handleAmountOnChange = (e, index) => {
    e.preventDefault();
    let newAmount = e.target.value;
    const { amountsSelected } = this.state;
    const newAmountsSelected = [...amountsSelected];

    if (!isNaN(newAmount)) {
      newAmountsSelected[index] = newAmount;
      this.setState({ amountsSelected: newAmountsSelected });
    }

  }

  handleRemove = (e, product) => {
    e.preventDefault();
    const { onRemoveFromBag } = this.props;
    onRemoveFromBag(product);
  }

  handleCheckout = (e) => {
    e.preventDefault();

    const { shoppingBag, loggedUser, onClearBag, onLogout } = this.props;
    const { addresses, addressSelected, payments, paymentSelected } = this.state;
    const order = { productOrders: [] };
    shoppingBag.forEach(product => {
      order.productOrders.push({ productId: product.id, quantity: product.amountSelected });
    });
    order.address = addresses[addressSelected];
    order.creditCard = payments[paymentSelected];
    createOrder(order, loggedUser)
      .then(res => {
        if (!res.ok) { throw res }
        return res.json();
      })
      .then(res => {
        this.setState({
          successfulOrder: true,
          addressSelected: -1,
          paymentSelected: -1,
          orderError: '',
        });
        onClearBag();
      })
      .catch(err => {
        if (err.status === 401) {
          Auth.signOut()
            .then(data => onLogout())
            .catch(err => {
              console.error('Unexpected error while loggin out');
              console.error(err);
            });
        } if (err.status === 500) {
          err.json().then(err =>
            this.setState({
              orderError: 'There was an error while creating the order: ' + err.errorMessages
            })
          )

        } else {
          console.error(err);
          this.setState({ orderError: 'There was an unexpected error while creating the order' });
        }
      })
  }

  calculateTotal = () => {
    let total = 0;
    const { shoppingBag } = this.props;
    shoppingBag.forEach(product => {
      total += product.price * product.amountSelected;
    });

    return total;
  }

  loadUserDetails = () => {
    const { loggedUser, onLogout } = this.props;
    this.setState({ orderError: '' });

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

  componentDidMount() {
    this.loadUserDetails();
  }

  render() {
    const { shoppingBag, loggedUser } = this.props;
    const { amountsSelected, addresses, addressSelected, payments, paymentSelected,
      successfulOrder, orderError } = this.state;

    if (!loggedUser) {
      return <Redirect to="/" />
    }

    return (
      <div className="shopping-bag">

        <section className="shopping-bag__section">

          <div className="shopping-bag__heading-wrapper">
            <div className="shopping-bag__heading-container">
              <h1 className="shopping-bag__heading">Your bag</h1>
            </div>
          </div>

          <div className="shopping-bag__content-container">

            {successfulOrder ?
              <div className="shopping-bag__success-order">
                <i class="fas fa-check-circle shopping-bag__success-order-icon"></i>
                <p className="shopping-bag__success-text">
                  The order was created successfully! You'll get it in the next 24 hours.
                </p>
                <p className="shopping-bag__success-text">
                  Thanks for shopping with us!
                </p>
              </div>

              :
              <React.Fragment>
                <div className="shopping-bag__summary">
                  <div className="shopping-bag__summary-product">Product</div>
                  <div className="shopping-bag__summary-price">Price</div>
                  <div className="shopping-bag__summary-quantity">Quantity</div>
                  <div className="shopping-bag__summary-total">Total</div>
                </div>

                {shoppingBag.map((product, index) => (
                  <div className="shopping-bag__summary" key={product.id}>
                    <div className="shopping-bag__summary-product">
                      <div className="shopping-bag__image-wrapper">
                        <img className="shopping-bag__image" src={product.imageUrl} alt="" />
                      </div>
                      <div className="shopping-bag__product-details">
                        <div className="shopping-bag__product-name">{capitalizeName(product.name)}</div>
                        <div className="shopping-bag__product-size">Size: {product.amount}</div>
                        <button onClick={(e) => { this.handleRemove(e, product) }} className="shopping-bag__product-remove">Remove</button>
                      </div>
                    </div>
                    <div className="shopping-bag__summary-price">${product.price}</div>
                    <div className="shopping-bag__summary-quantity">
                      <input
                        onBlur={(e) => this.handleAmountOnBlur(e, index)} onChange={(e) => this.handleAmountOnChange(e, index)}
                        type="text" name="quantity" id="quantity"
                        value={amountsSelected[index]} className="shopping-bag__product-quantity-input" />
                    </div>
                    <div className="shopping-bag__summary-total">${(product.price * product.amountSelected).toFixed(2)}</div>
                  </div>
                ))}

                <div className="shopping-bag__subtotal-wrapper">
                  <div className="shopping-bag__subtotal-column">
                    Total
                  </div>
                  <div className="shopping-bag__subtotal-column">
                    ${this.calculateTotal().toFixed(2)}
                  </div>
                </div>

                <div className="shopping-bag__address-wrapper">
                  <h2 className="shopping-bag__sec-heading">Select an address for delivery</h2>
                  <AddressList
                    addresses={addresses}
                    editAddressLocal={this.editAddressLocal}
                    saveNewAddressLocal={this.saveNewAddressLocal}
                    removeAddressLocal={this.removeAddressLocal}
                    selectable={true}
                    handleSelect={this.handleAddressSelect}
                    addressSelected={addressSelected}
                  />
                </div>

                <div className="shopping-bag__address-wrapper">
                  <h2 className="shopping-bag__sec-heading">Select a payment method</h2>
                  <PaymentList
                    payments={payments}
                    saveNewPaymentLocal={this.saveNewPaymentLocal}
                    removePaymentLocal={this.removePaymentLocal}
                    selectable={true}
                    handleSelect={this.handlePaymentSelect}
                    selectedPayment={paymentSelected}
                  />
                </div>
              </React.Fragment>
            }

            <div className="shopping-bag__error-message">{orderError}</div>

            <div className="shopping-bag__checkout-wrapper">
              <div className="shopping-bag__checkout-column">
                <Link className="shopping-bag__checkout shopping-bag__checkout--secondary"
                  to="/search">Continue Shopping</Link>
              </div>
              {successfulOrder ?
                null :
                <div className="shopping-bag__checkout-column">
                  <button onClick={(e) => this.handleCheckout(e)}
                    disabled={shoppingBag.length === 0 || addressSelected === -1 || paymentSelected === -1}
                    className="shopping-bag__checkout">Checkout</button>
                </div>
              }
            </div>
          </div>
        </section>
      </div>
    )
  }
}

const mapStateToProps = state => ({
  shoppingBag: state.shoppingBagReducer.shoppingBag,
  loggedUser: state.userReducer.loggedUser,
});

const mapDispatchToProps = dispatch => ({
  onRemoveFromBag: (product) => dispatch(removeFromBag(product)),
  onEditProduct: (product, index) => dispatch(editProduct(product, index)),
  onClearBag: () => dispatch(clearBag()),
  onLogout: () => { dispatch(logout()); dispatch(clearBag()) },
});

export default withRouter(connect(
  mapStateToProps,
  mapDispatchToProps
)(ShoppingBag));
