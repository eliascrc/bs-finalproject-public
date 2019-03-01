import { NEW_LOGIN, LOGOUT } from "../actions/UserActions";
import { updateLocalStorage, getLocalStorage, removeUserDataFromLocalStorage } from '../../utils/commons';
import { USER_STORAGE_KEY } from "../../constants/LocalStorageConstants";

const INITIAL_STATE = {
  loggedUser: null,
};

export function userReducer(state = INITIAL_STATE, action) {
  let newState;

  switch (action.type) {

    case NEW_LOGIN:
      newState = { ...state };
      newState.loggedUser = action.user;
      updateLocalStorage(USER_STORAGE_KEY, newState);

      return newState;

    case LOGOUT:
      newState = { ...state };
      newState.loggedUser = null;
      updateLocalStorage(USER_STORAGE_KEY, newState);
      removeUserDataFromLocalStorage();

      return newState;

    default:
      const localState = getLocalStorage(USER_STORAGE_KEY);
      if (localState !== null) {
        newState = { ...localState };
      } else {
        newState = { ...state };
      }

      return { ...newState };
  }
}