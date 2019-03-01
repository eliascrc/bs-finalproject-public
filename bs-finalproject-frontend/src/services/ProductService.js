import { FEATURED_PRODUCTS_URL, SEARCH_PRODUCTS_URL, PAGE_SIZE, MY_RATING_URL, RATING_URL } from '../constants/NetworkConstants';

export const getFeaturedProducts = () => {
  const reqHeaders = new Headers();

  const properties = {
    method: 'GET',
    headers: reqHeaders,
  };

  return fetch(FEATURED_PRODUCTS_URL, properties);
}

export const searchProducts = (page, category, sort, searchTerm) => {
  const reqHeaders = new Headers();

  const properties = {
    method: 'GET',
    headers: reqHeaders,
  };

  let url = SEARCH_PRODUCTS_URL + "?page=" + page + "&size=" + PAGE_SIZE;

  if (category) {
    url += "&category=" + category;
  }

  if (sort) {
    url += "&sort=" + sort;
  }

  if (searchTerm) {
    url += "&searchTerm=" + searchTerm;
  }

  return fetch(url, properties);
}

export const getProductById = (id) => {
  const reqHeaders = new Headers();

  const properties = {
    method: 'GET',
    headers: reqHeaders,
  };

  let url = SEARCH_PRODUCTS_URL + "/" + id;
  return fetch(url, properties);
}

export const getMyProductRating = (id, loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Authorization', loggedUser);

  const properties = {
    method: 'GET',
    headers: reqHeaders,
  };

  let url = MY_RATING_URL + "/" + id;
  return fetch(url, properties);
}

export const setProductRating = (rating, loggedUser) => {
  const reqHeaders = new Headers();
  reqHeaders.append('Content-Type', 'application/json');
  reqHeaders.append('Authorization', loggedUser);

  const properties = {
    method: 'POST',
    headers: reqHeaders,
    body: JSON.stringify(rating),
  };

  return fetch(RATING_URL, properties);
}
