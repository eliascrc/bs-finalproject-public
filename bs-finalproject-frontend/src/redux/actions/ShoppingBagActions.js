export const ADD_TO_BAG = 'ADD_TO_BAG';
export const REMOVE_FROM_BAG = 'REMOVE_FROM_BAG';
export const EDIT_PRODUCT = 'EDIT_PRODUCT';
export const CLEAR_BAG = 'CLEAR_BAG';

export function addToBag(product) {
  return { type: ADD_TO_BAG, product };
}

export function removeFromBag(product) {
  return { type: REMOVE_FROM_BAG, product };
}

export function editProduct(product, index) {
  return { type: EDIT_PRODUCT, product, index };
}

export function clearBag() {
  return { type: CLEAR_BAG };
}