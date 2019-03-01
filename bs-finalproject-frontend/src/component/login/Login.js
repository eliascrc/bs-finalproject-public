import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link, Redirect } from "react-router-dom";

import './Login.scss';
import { signIn } from '../../services/AuthenticationService';
import { newLogin } from '../../redux/actions/UserActions';
import ConfirmUser from '../confirm-user/ConfirmUser';

class Login extends Component {

  constructor(props) {
    super(props);

    this.state = {
      email: '',
      emailVisited: false,
      password: '',
      passwordVisited: false,
      cognitoError: false,
      cognitoMessage: '',
      loggingIn: false,
      confirmUser: false,
    }
  }

  onInputChange = (e, key) => {
    e.preventDefault();

    const newState = { ...this.state };
    newState[key] = e.target.value;
    this.setState(newState);
  }

  isEmailValid = (email, forForm) => {
    const { emailVisited } = this.state;
    if (!forForm && !emailVisited) return true;
    return (/^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/.test(email));
  }

  isPasswordValid = (password, forForm) => {
    const { passwordVisited } = this.state;
    if (!forForm && !passwordVisited) return true;
    return (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&\\.])[A-Za-z\d@$!%*#?&\\.]{8,}$/).test(password);
  }

  isFormValid = () => {
    const { email, password } = this.state;
    return this.isEmailValid(email, true)
      && this.isPasswordValid(password, true);
  }

  handleEmailVisited = (e) => {
    e.preventDefault();
    this.setState({ emailVisited: true });
  }

  handlePasswordVisited = (e) => {
    e.preventDefault();
    this.setState({ passwordVisited: true });
  }

  onSubmit = (e) => {
    e.preventDefault();

    this.setState({ cognitoError: false, loggingIn: true });

    const { onLogin } = this.props;
    const { email, password } = this.state;
    const user = { email: email, password: password };

    signIn(user)
      .then(response => {
        onLogin(response.signInUserSession.idToken.jwtToken);
      })
      .catch(error => {
        if (error.code === 'UserNotConfirmedException') {
          this.setState({ confirmUser: true, loggingIn: false });
        } else {
          this.setState({ cognitoError: true, cognitoMessage: error.message, loggingIn: false });
        }
      })


  }

  render() {
    const { email, password, cognitoError, cognitoMessage, loggingIn, confirmUser } = this.state;
    const { loggedUser } = this.props;

    if (loggedUser) {
      return <Redirect to="/" />
    }

    return (
      <section className="login">
        <div className="login__side-column">
          {confirmUser ?

            <ConfirmUser
              email={email}
              password={password}
            />
            :
            <React.Fragment>
              <h1 className="login__heading">Login</h1>
              <p className="login__create-acc">or <Link className="login__create-acc-link" to="/join">create an account</Link></p>
              <form onSubmit={e => this.onSubmit(e)} className="login__form">
                {!cognitoError ? null :
                  <div className="login__input-error">{cognitoMessage}</div>
                }
                <label className="login__input-label">
                  <input className={`login__input ${this.isEmailValid(email) ? null : "login__input--invalid"}`}
                    type="email" name="Email" id="email-input" placeholder="&nbsp;"
                    value={email} onChange={e => this.onInputChange(e, 'email')} onBlur={e => this.handleEmailVisited(e)} />
                  <span className="login__input-content">Email</span>
                  <span className="login__input-border"></span>
                </label>

                <label className="login__input-label">
                  <input className={`login__input ${this.isPasswordValid(password) ? null : "login__input--invalid"}`}
                    type="password" name="Password" id="password-input" placeholder="&nbsp;"
                    value={password} onChange={e => this.onInputChange(e, 'password')} onBlur={e => this.handlePasswordVisited(e)} />
                  <span className="login__input-content">Password</span>
                  <span className="login__input-border"></span>
                </label>

                <button className="login__submit" disabled={!this.isFormValid() || loggingIn}>
                  {loggingIn ? "Logging in..." : "Let's shop!"}
                </button>
              </form>
            </React.Fragment>
          }
        </div>
      </section>
    )
  }

}

const mapStateToProps = state => ({
  loggedUser: state.userReducer.loggedUser
});

const mapDispatchToProps = dispatch => ({
  onLogin: (user) => dispatch(newLogin(user)),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Login);
