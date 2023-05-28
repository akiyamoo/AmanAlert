import {LOGIN_SUCCESS, NEWS_SUCCESS, OPEN_MODAL, OPEN_MODAL_EDIT, REMOVE_NEWS} from "../constants";
import {DELETE_USER, GET_ALL_USERS_SUCCESS} from "../constants";

const initialState = {
  authData: {},
  modalStatus: false,
  news: [],
  users:[],
  modalStatusEdit: false,
};

const login = (state = initialState, { type, payload }) => {
  switch (type) {
    case LOGIN_SUCCESS:
      return {
        ...state,
        authData: payload,
      };
      case OPEN_MODAL:
      return {
        ...state,
        modalStatus: payload,
      };
      case OPEN_MODAL_EDIT:
      return {
        ...state,
        modalStatusEdit: payload,
      };
    case NEWS_SUCCESS:
      return {
        ...state,
        news: payload,
      };
      case REMOVE_NEWS:
        console.log(payload,'pay')
      return {
        ...state,
        news: state.news.filter((item) => item.id !== payload),
      };
    case GET_ALL_USERS_SUCCESS:
      return {
        ...state,
        users: payload,
      };
    case DELETE_USER:
      console.log(payload)
      return {
        ...state,
        users: state.users.filter(it => it.id !== payload)
      }
    default: return state;
  }
};

export default login;
