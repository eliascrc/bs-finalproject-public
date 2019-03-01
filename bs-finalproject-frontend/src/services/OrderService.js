import { ORDER_URL, ORDER_HISTORY_URL } from "../constants/NetworkConstants";

export const createOrder = (order, loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Content-Type', 'application/json');
  reqHeaders.append('Authorization', loggedUser);
  
  const properties = {
    method: 'POST',
    headers: reqHeaders,
    body: JSON.stringify(order),
  };

  return fetch(ORDER_URL, properties);
}

export const getHistory = (loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Authorization', loggedUser);
  
  const properties = {
    method: 'GET',
    headers: reqHeaders,
  };

  return fetch(ORDER_HISTORY_URL, properties);
}