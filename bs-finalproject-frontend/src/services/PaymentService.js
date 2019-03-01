import { PAYMENT_URL } from "../constants/NetworkConstants";

export const createNewPayment = (payment, loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Content-Type', 'application/json');
  reqHeaders.append('Authorization', loggedUser);

  const properties = {
    method: 'POST',
    headers: reqHeaders,
    body: JSON.stringify(payment),
  };

  return fetch(PAYMENT_URL, properties);
}

export const deletePayment = (id, loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Authorization', loggedUser);

  const properties = {
    method: 'DELETE',
    headers: reqHeaders,
  };

  return fetch(PAYMENT_URL + "/" + id, properties);
}