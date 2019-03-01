import React, { Component } from 'react';
import { connect } from 'react-redux';

import './ConfirmUser.scss';
import { Auth } from 'aws-amplify';
import { signIn } from '../../services/AuthenticationService';
import { newLogin } from '../../redux/actions/UserActions';

class ConfirmUser extends Component {

  constructor(props) {
    super(props);

    this.state = {
      code: '',
      codeVisited: false,
      cognitoError: false,
      cognitoMessage: '',
      loggingIn: false,
    }
  }

  onCodeChange = (e) => {
    e.preventDefault();

    let newCode = e.target.value;

    if (!isNaN(newCode) && newCode.length < 7) {
      this.setState({ code: newCode });
    }
  }

  isCodeValid = (code, forForm) => {
    const { codeVisited } = this.state;
    if (!forForm && !codeVisited) return true;
    return (!isNaN(code) && code.length === 6);
  }

  handleCodeVisited = (e) => {
    e.preventDefault();
    this.setState({ codeVisited: true });
  }

  isFormValid = () => {
    const { code } = this.state;
    return this.isCodeValid(code, true);
  }

  onSubmit = (e) => {
    e.preventDefault();

    this.setState({ cognitoError: false, loggingIn: true });

    const { code } = this.state;
    const { email, password, onLogin } = this.props;
    const user = { email: email, password: password };

    Auth.confirmSignUp(email, code)
      .then(res => {
        signIn(user)
          .then(response => {
            onLogin(response.signInUserSession.idToken.jwtToken);
          })
          .catch(error => {
            this.setState({ cognitoError: true, cognitoMessage: error.message, loggingIn: false });
          })
      }).catch((error) => {
        this.setState({ cognitoError: true, cognitoMessage: error.message, loggingIn: false });
      });
  }

  render() {
    const { code, cognitoError, cognitoMessage, loggingIn } = this.state;

    return (
      <React.Fragment>
        <form onSubmit={e => this.onSubmit(e)} className="confirm__form">
          <div className="confirm__oops-message">You haven't verified your account!</div>

          {!cognitoError ? null :
            <div className="confirm__input-error">{cognitoMessage}</div>
          }

          <div className="confirm__message">
            <p className="confirm__paragraph">Please check your email and enter the verification code.</p>
          </div>
          <label className="confirm__input-label">
            <input className={`confirm__input ${this.isCodeValid(code) ? null : "confirm__input--invalid"}`}
              type="text" name="Verification Code" id="code"
              value={code} onChange={e => this.onCodeChange(e)} onBlur={e => this.handleCodeVisited(e)} />
          </label>

          <button className="confirm__submit" disabled={!this.isFormValid() || loggingIn}>
            {loggingIn ? "Verifying..." : "Verify my account"}
          </button>
        </form>
      </React.Fragment>
    )
  }
}

const mapStateToProps = state => ({
});

const mapDispatchToProps = dispatch => ({
  onLogin: (user) => dispatch(newLogin(user)),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ConfirmUser);
