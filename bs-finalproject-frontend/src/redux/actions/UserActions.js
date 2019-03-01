export const NEW_LOGIN = 'NEW_LOGIN';
export const LOGOUT = 'LOGOUT';

export function newLogin(user) {
  return { type: NEW_LOGIN, user};
}

export function logout() {
  return { type: LOGOUT };
}