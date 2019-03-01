import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link, withRouter, Redirect } from "react-router-dom";

import './SignUp.scss';
import { signUp } from '../../services/AuthenticationService';

class SignUp extends Component {

  constructor(props) {
    super(props);

    this.state = {
      name: '',
      nameVisited: false,
      email: '',
      emailVisited: false,
      password: '',
      passwordVisited: false,
      confirmPassword: '',
      confirmPasswordVisited: false,
      userAlreadyExists: false, 
      unexpectedError: false,
      signingUp: false, 
    }
  }

  onInputChange = (e, key) => {
    e.preventDefault();

    const newState = {...this.state};
    newState[key] = e.target.value;
    this.setState(newState);
  }

  isEmailValid = (email, forForm) => {
    const { emailVisited } = this.state;
    if(!forForm && !emailVisited) return true;
    return (/^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/.test(email));
  }

  isNameValid = (name, forForm) => {
    const { nameVisited } = this.state;
    if(!forForm && !nameVisited) return true;
    return !(!name || /^\s*$/.test(name));
  }

  isPasswordValid = (password, forForm) => {
    const { passwordVisited } = this.state;
    if(!forForm && !passwordVisited) return true;
    return (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&\\.])[A-Za-z\d@$!%*#?&\\.]{8,}$/).test(password);
  }

  isConfirmPasswordValid = (password, confirm, forForm) => {
    const { confirmPasswordVisited } = this.state;
    if(!forForm && !confirmPasswordVisited) return true;
    return password === confirm;
  }

  isFormValid = () => {
    const { name, email, password, confirmPassword } = this.state;
    return this.isEmailValid(email, true) 
            && this.isPasswordValid(password, true) 
            && this.isConfirmPasswordValid(password, confirmPassword, true)
            && this.isNameValid(name, true);
  }

  handleEmailVisited = (e) => {
    e.preventDefault();
    this.setState({ emailVisited: true });
  }

  handlePasswordVisited = (e) => {
    e.preventDefault();
    this.setState({ passwordVisited: true });
  }
  
  handleNameVisited = (e) => {
    e.preventDefault();
    this.setState({ nameVisited: true });
  }

  handleConfirmPasswordVisited = (e) => {
    e.preventDefault();
    this.setState({ confirmPasswordVisited: true });
  }

  onSubmit = (e) => {
    e.preventDefault();
    
    this.setState({ userAlreadyExists: false, unexpectedError: false, signingUp: true });

    const { history } = this.props;
    const { email, name, password } = this.state;
    const newUser = { email: email, password: password, name: name };
    
    signUp(newUser)
      .then( response => {
        if (!response.ok) { throw response }
        history.push('/login');
      })
      .catch(error => {
        if (error.status === 409) {
          this.setState({ userAlreadyExists: true, signingUp: false });
        } else {
          this.setState({ unexpectedError: true, signingUp: false });
        }
      })
    
    
  }

  render() {
    const { name, email, password, confirmPassword, userAlreadyExists, unexpectedError, signingUp } = this.state;
    const { loggedUser } = this.props;

    if (loggedUser) {
      return <Redirect to="/" />
    }
    
    return (
      <section className="sign-up">
        <div className="sign-up__side-column">
          <h1 className="sign-up__heading">Sign Up</h1>
          <p className="sign-up__create-acc">or <Link className="sign-up__create-acc-link" to="/login">login to your account</Link></p>
          <form onSubmit={e => this.onSubmit(e)} className="sign-up__form">
            { !userAlreadyExists? null:
              <div className="sign-up__input-error">There's already a user with the selected email</div>
            }
            { !unexpectedError? null:
              <div className="sign-up__input-error">An unexpected error has ocurred</div>
            }
            <label className="sign-up__input-label">
              <input className={`sign-up__input ${this.isNameValid(name)? null : "sign-up__input--invalid" }`} 
                type="text" name="Name" id="name-input" placeholder="&nbsp;" 
                value={name} onChange={e => this.onInputChange(e, 'name')} onBlur={e => this.handleNameVisited(e)} />
              <span className="sign-up__input-content">Name</span>
              <span className="sign-up__input-border"></span>
            </label>

            <label className="sign-up__input-label">
              <input className={`sign-up__input ${this.isEmailValid(email)? null : "sign-up__input--invalid" }`} 
                type="email" name="Email" id="email-input" placeholder="&nbsp;" 
                value={email} onChange={e => this.onInputChange(e, 'email')} onBlur={e => this.handleEmailVisited(e)} />
              <span className="sign-up__input-content">Email</span>
              <span className="sign-up__input-border"></span>
            </label>

            <label className="sign-up__input-label sign-up__input-label--password">
              <input className={`sign-up__input ${this.isPasswordValid(password)? null : "sign-up__input--invalid" }`} 
                type="password" name="Password" id="password-input" placeholder="&nbsp;" 
                value={password} onChange={e => this.onInputChange(e, 'password')} onBlur={e => this.handlePasswordVisited(e)} />
              <span className="sign-up__input-content">Password</span>
              <span className="sign-up__input-border"></span>
            </label>
            { this.isPasswordValid(password)? null:
              <div className="sign-up__input-error">Password must contain at least 8 characters, one number and a special character.</div>
            }

            <label className="sign-up__input-label">
              <input  className={`sign-up__input ${this.isConfirmPasswordValid(password, confirmPassword)? null : "sign-up__input--invalid" }`} 
                type="password" name="ConfirmPassword" id="confirm-password-input" placeholder="&nbsp;" 
                value={confirmPassword} onChange={e => this.onInputChange(e, 'confirmPassword')} onBlur={e => this.handleConfirmPasswordVisited(e)} />
              <span className="sign-up__input-content">Confirm Password</span>
              <span className="sign-up__input-border"></span>
            </label>

            <button className="sign-up__submit" disabled={!this.isFormValid() || signingUp}>
            { signingUp ? "Just a sec..." : "Join" }
            </button>
          </form>
        </div>
      </section>
    )
  }

}

const mapStateToProps = state => ({
  loggedUser: state.loggedUser
});

const mapDispatchToProps = dispatch => ({
  
});

export default withRouter(connect(
  mapStateToProps,
  mapDispatchToProps
)(SignUp));
