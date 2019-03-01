import { ADDRESS_URL } from "../constants/NetworkConstants";

export const editAddress = (address, loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Content-Type', 'application/json');
  reqHeaders.append('Authorization', loggedUser);

  const properties = {
    method: 'PUT',
    headers: reqHeaders,
    body: JSON.stringify(address),
  };

  return fetch(ADDRESS_URL, properties);
}

export const createNewAddress = (address, loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Content-Type', 'application/json');
  reqHeaders.append('Authorization', loggedUser);

  const properties = {
    method: 'POST',
    headers: reqHeaders,
    body: JSON.stringify(address),
  };

  return fetch(ADDRESS_URL, properties);
}

export const deleteAddress = (id, loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Authorization', loggedUser);

  const properties = {
    method: 'DELETE',
    headers: reqHeaders,
  };

  return fetch(ADDRESS_URL + "/" + id, properties);
}