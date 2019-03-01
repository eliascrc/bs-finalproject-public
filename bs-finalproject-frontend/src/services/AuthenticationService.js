import { Auth } from "aws-amplify";
import { SIGN_UP_URL } from '../constants/NetworkConstants';

export const signUp = (user) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Content-Type', 'application/json');
  
  const properties = {
    method: 'POST',
    headers: reqHeaders,
    body: JSON.stringify(user),
  };

  return fetch(SIGN_UP_URL, properties);
}

export const signIn = (user) => {
  return Auth.signIn(user.email, user.password);
}