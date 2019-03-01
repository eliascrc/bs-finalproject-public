import { combineReducers } from 'redux';
import { userReducer } from './UserReducer';
import { shoppingBagReducer } from './ShoppingBagReducer';

export default combineReducers({
  userReducer,
  shoppingBagReducer,
})