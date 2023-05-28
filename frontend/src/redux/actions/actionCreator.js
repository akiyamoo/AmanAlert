import {
  ADD_NEWS,
  LOGIN_REQUEST, LOGIN_SUCCESS, NEWS_REQUEST, NEWS_SUCCESS, OPEN_MODAL, REMOVE_NEWS,
  GET_ALL_USERS_REQUEST, GET_ALL_USERS_SUCCESS, DELETE_USER, OPEN_MODAL_EDIT,
} from "../constants";

export const getAuthReq = (login, password) => ({
  type: LOGIN_REQUEST, login, password
});

export const getAuthSuc = (payload) => ({
  type: LOGIN_SUCCESS,
  payload,
});

export const openCloseModal = (payload) => ({
  type: OPEN_MODAL,
  payload
});
export const openCloseModalEdit = (payload) => ({
  type: OPEN_MODAL_EDIT,
  payload
});
export const getAllNewsReq = () => ({
  type: NEWS_REQUEST,
});
export const getAllNewsSuc = (payload) => ({
  type: NEWS_SUCCESS,
  payload
});
export const addNewsAction = (image, data) => ({
  type: ADD_NEWS, image, data
});
export const removeNewsAction = (id) => ({
  type: REMOVE_NEWS, id
});
export const getAllUsersReq = () => ({
  type: GET_ALL_USERS_REQUEST,
})

export const getAllUsersSuc = (payload) => ({
  type: GET_ALL_USERS_SUCCESS,
  payload
})

export const deleteUserReq = (payload) => ({
  type: DELETE_USER,
  payload
})
