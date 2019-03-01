import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';

import './PaymentList.scss';
import { Auth } from 'aws-amplify';
import { logout } from '../../redux/actions/UserActions';
import { clearBag } from '../../redux/actions/ShoppingBagActions';
import StripeCheckout from 'react-stripe-checkout';
import { createNewPayment, deletePayment } from '../../services/PaymentService';
import { STRIPE_API_KEY } from '../../config/stripe-config';

class PaymentList extends Component {

  constructor(props) {
    super(props);

    this.state = {
      paymentMessage: '',
      error: false,
    }
  }


  handleCreatePayment = (token) => {
    const { loggedUser, onLogout, saveNewPaymentLocal } = this.props;

    const expYear = '' + token.card.exp_year;

    const payment = {
      token: token.id,
      brand: token.card.brand,
      expiration: token.card.exp_month + "/" + expYear[2] + expYear[3],
      lastFourDigits: token.card.last4,
    };

    createNewPayment(payment, loggedUser)
      .then(res => {
        if (!res.ok) { throw res }
        return res.json();
      })
      .then(res => {
        saveNewPaymentLocal(res.response);
        this.setState({
          paymentMessage: 'Payment created succesfully',
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
            paymentMessage: 'There was an error while creating the payment',
            error: true,
          })
          console.error(err);
        }
      })
  }

  handleDelete = (e, index, id) => {
    e.preventDefault();
    const { loggedUser, onLogout, removePaymentLocal } = this.props;

    deletePayment(id, loggedUser)
      .then(res => {
        if (!res.ok) { throw res }
        removePaymentLocal(index);
        this.setState({
          paymentMessage: 'Payment deleted succesfully',
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
            paymentMessage: 'There was an error while deleting the payment',
            error: true,
          })
          console.error(err);
        }
      })
  }

  render() {
    const { paymentMessage, error } = this.state;
    const { payments, selectable, handleSelect, selectedPayment } = this.props;

    return (
      <React.Fragment>
        <div className={`${error ? "address-list__error-message" : "address-list__success-message"}`}>{paymentMessage}</div>
        <div className="payment-list__grid">
          {selectable ?
            <div className="payment-list__select"></div>
            : null
          }
          <div className="payment-list__brand">Brand</div>
          <div className="payment-list__expiration">Expiration</div>
          <div className="payment-list__card-number">Card Number</div>
        </div>
        {payments.map((payment, index) => (
          <div className="payment-list__grid" key={payment.id}>
            {selectable ?
              <button
                disabled={selectedPayment === index}
                onClick={(e) => handleSelect(e, index)} className="payment-list__select">
                {selectedPayment === index ? <i className="far fa-check-square"></i> : <i className="far fa-square"></i>}
              </button>
              :
              null
            }
            <div className="payment-list__brand">
              {payment.brand}
            </div>
            <div className="payment-list__expiration">
              {payment.expiration}
            </div>
            <div className="payment-list__card-number">
              **** **** **** {payment.lastFourDigits}
            </div>
            <button onClick={(e) => this.handleDelete(e, index, payment.id)} className="payment-list__delete">
              <i className="fas fa-trash-alt"></i>
            </button>
          </div>
        ))}

        <div className="payment-list__add-wrapper">
          <StripeCheckout
            token={this.handleCreatePayment}
            stripeKey={STRIPE_API_KEY}
            panelLabel="Add a card"
            name="Guada Market"
            description="A fresh ecommerce for local producers"
            allowRememberMe={false}>
            <button className="payment-list__add">
            <i className="fas fa-credit-card"></i> Add a card
            </button>
          </StripeCheckout>
        </div>

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
)(PaymentList));