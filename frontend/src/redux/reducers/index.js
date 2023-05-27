import { combineReducers } from 'redux';
import login from "./login";

const reducer = combineReducers({
  login: login,
});

export default reducer;
