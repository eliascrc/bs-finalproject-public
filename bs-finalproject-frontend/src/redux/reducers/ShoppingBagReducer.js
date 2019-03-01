import { updateLocalStorage, getLocalStorage } from '../../utils/commons';
import { ADD_TO_BAG, REMOVE_FROM_BAG, EDIT_PRODUCT, CLEAR_BAG } from "../actions/ShoppingBagActions";
import { SHOPPINGBAG_STORAGE_KEY } from '../../constants/LocalStorageConstants';

const INITIAL_STATE = {
  shoppingBag: [],
};

export function shoppingBagReducer(state = INITIAL_STATE, action) {
  let newState;
  let newShoppingBag;

  switch (action.type) {

    case ADD_TO_BAG:
      newShoppingBag = [...state.shoppingBag];
      newShoppingBag.push(action.product);
      newState = { ...state, shoppingBag: newShoppingBag };
      updateLocalStorage(SHOPPINGBAG_STORAGE_KEY, newState);

      return newState;

    case REMOVE_FROM_BAG:
      newShoppingBag = [...state.shoppingBag];
      let selectedIndex = -1;

      for (let index = 0; index < newShoppingBag.length; index++) {
        const productInBag = newShoppingBag[index];
        if (productInBag.id === action.product.id) {
          selectedIndex = index;
        }
      }

      if (selectedIndex !== -1) { newShoppingBag.splice(selectedIndex, 1); }
      newState = { ...state, shoppingBag: newShoppingBag };
      updateLocalStorage(SHOPPINGBAG_STORAGE_KEY, newState);

      return newState;
    
    case EDIT_PRODUCT:
      newShoppingBag = [...state.shoppingBag];
      newShoppingBag[action.index] = action.product;
      newState = { ...state, shoppingBag: newShoppingBag };
      updateLocalStorage(SHOPPINGBAG_STORAGE_KEY, newState);

      return newState;

    case CLEAR_BAG:
      newState = { ...state, shoppingBag: [] };
      updateLocalStorage(SHOPPINGBAG_STORAGE_KEY, newState);

      return newState;

    default:
      const localState = getLocalStorage(SHOPPINGBAG_STORAGE_KEY);
      if (localState !== null) {
        newState = { ...localState };
      } else {
        newState = { ...state };
      }

      return { ...newState };
  }
}