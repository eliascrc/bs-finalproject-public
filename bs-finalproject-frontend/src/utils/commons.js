import { SHOPPINGBAG_STORAGE_KEY } from "../constants/LocalStorageConstants";

/* Common routine of setting the logged user to null and to 
stringify the state to JSON before saving it to local storage */
export function updateLocalStorage(LOCAL_STORAGE_KEY, state) {
  localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(state));
}

export function removeUserDataFromLocalStorage() {
  localStorage.setItem(SHOPPINGBAG_STORAGE_KEY, JSON.stringify(null));
}

/* Common routine of parsing the state to an object after getting it from local storage */
export function getLocalStorage(LOCAL_STORAGE_KEY) {
  return JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
}

export const capitalizeName = (name) => {
  return name.toLowerCase()
    .split(' ')
    .map((s) => s.charAt(0).toUpperCase() + s.substring(1))
    .join(' ');
}