import { takeEvery, put, call } from 'redux-saga/effects';
import {ADD_NEWS, REMOVE_NEWS} from '../constants';
import {getAllNewsSuc, getAuthSuc, openCloseModal} from '../actions/actionCreator';
import {AddNews, getAllNewsApi, loginWithPassword, RemoveNews} from '../../api';
import {DELETE_USER, GET_ALL_USERS_REQUEST, LOGIN_REQUEST, NEWS_REQUEST, OPEN_MODAL} from '../constants';
import {deleteUserReq, getAllUsersSuc} from '../actions/actionCreator';
import {deleteUserApi, getAllUsersApi} from '../../api';

export function* handleLatestNews(login , password) {
  const hits = yield call(loginWithPassword, login, password);
  yield put(getAuthSuc(hits));
}
export function* openModal(status) {
  yield call(openCloseModal, status);
}
export function* getAllNews() {
  const hits = yield call(getAllNewsApi);
  yield put(getAllNewsSuc(hits));
}
export function* AddNewsSaga({image, data}) {
  console.log(image, data)
  yield call(AddNews, image, data);
}
export function* RemoveNewsSaga({id}) {
  yield call(RemoveNews, id);
}

export function* getAllUsersSaga() {
  const hits = yield call(getAllUsersApi)
  console.log(hits)
  yield put(getAllUsersSuc(hits))
}
export function* deleteUserSaga(id) {
  const hits = yield call(deleteUserApi,id)
  console.log(hits)
  yield put(deleteUserReq(hits))
}

export function* watchClickSaga() {
  yield takeEvery(LOGIN_REQUEST, handleLatestNews);
  yield takeEvery(OPEN_MODAL, openModal);
  yield takeEvery(NEWS_REQUEST, getAllNews);
  yield takeEvery(ADD_NEWS, AddNewsSaga);
  yield takeEvery(REMOVE_NEWS, RemoveNewsSaga);
  yield takeEvery(GET_ALL_USERS_REQUEST, getAllUsersSaga)
  yield takeEvery(DELETE_USER, deleteUserSaga)
}


export default function* rootSaga() {
  yield watchClickSaga();
}
