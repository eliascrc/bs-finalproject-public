import React from 'react';
import { Link, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';

import './Header.scss';
import { logout } from '../../redux/actions/UserActions';
import { clearBag } from '../../redux/actions/ShoppingBagActions';
import { Auth } from 'aws-amplify';

const Header = ({ loggedUser, location, onLogout, shoppingBag }) => {
  const logout = (e) => {
    e.preventDefault();
    Auth.signOut()
      .then(data => onLogout())
      .catch(err => {
        console.error('Unexpected error while loggin out');
        console.error(err);
      });
  }

  const authenticated = loggedUser;
  const showLinks = !location.pathname.includes("login") && !location.pathname.includes("join");
  return (
    <div>
      {authenticated ?
        <header className="header">
          <nav className="header__nav header__container">
            <div className="header__brand-wrapper">
              <Link className="header__brand" to="/">
                <img className="header__app-logo" src="https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/assets/app-logo.png" alt="Guada's Market Logo"/>
              </Link>
            </div>

            <ul className="header__links">
              <li className="header__link-wrapper">
                <Link className="header__link" to="/my-profile">
                  <i className="header__icon material-icons">account_circle</i>
                </Link>
              </li>
              <li className="header__link-wrapper">
                <Link className="header__link" to="/search">
                  <i className="header__icon material-icons">search</i>
                </Link>
              </li>
              <li className="header__link-wrapper">
                <Link className="header__link header__link--bag" to="/shopping-bag">
                  <i className="header__icon material-icons">shopping_basket</i>
                  <div className="header__quantity">{shoppingBag.length}</div>
                </Link>
              </li>
              <li className="header__link-wrapper">
                <button onClick={(e) => logout(e)} className="header__link">
                  <i className="header__icon material-icons">exit_to_app</i>
                </button>
              </li>
            </ul>
          </nav>
        </header>
        :
        <header className="header">
          <nav className="header__nav header__container">
            <div className="header__brand-wrapper">
              <Link className="header__brand" to="/">
              <img className="header__app-logo" src="https://s3.us-east-2.amazonaws.com/bs-finalproject-uploads/public/assets/app-logo.png" alt="Guada's Market Logo"/>
              </Link>
            </div>

            {showLinks ?
              <ul className="header__links">
                <li className="header__link-wrapper">
                  <Link className="header__link header__login" to="/login">
                    <i className="fas fa-user-circle header__login-icon"></i> Login
                </Link>
                </li>
              </ul>
              : null
            }
          </nav>
        </header>
      }
    </div>
  );
}

const mapStateToProps = state => ({
  loggedUser: state.userReducer.loggedUser,
  shoppingBag: state.shoppingBagReducer.shoppingBag,
});

const mapDispatchToProps = dispatch => ({
  onLogout: () => { dispatch(logout()); dispatch(clearBag()) },
});

export default withRouter(connect(
  mapStateToProps,
  mapDispatchToProps
)(Header));