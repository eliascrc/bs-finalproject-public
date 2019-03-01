import { USER_URL } from "../constants/NetworkConstants";

export const getUserDetails = (loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Authorization', loggedUser);

  const properties = {
    method: 'GET',
    headers: reqHeaders,
  };

  return fetch(USER_URL, properties);
}