import { takeEvery, put, call } from 'redux-saga/effects';

import {ADD_NEWS, CREATE_USER, DELETE_STORY, GET_ALL_STORY_ACTION, REMOVE_NEWS, SEND_ANSWER, EDIT_USER,} from '../constants';
import {getAllNewsSuc,  getAuthSuc, openCloseModal, getAllStoryActionSuc} from '../actions/actionCreator';
import {AddNews, addUser, deleteStoryApi,editUser, getAllNewsApi, getAllStorySaga, loginWithPassword, RemoveNews, sendAnswerApi} from '../../api/index';
import {DELETE_USER, GET_ALL_USERS_REQUEST, LOGIN_REQUEST, NEWS_REQUEST, OPEN_MODAL} from '../constants';
import {deleteUserReq, getAllUsersSuc} from '../actions/actionCreator';
import {deleteUserApi, getAllUsersApi} from '../../api/index';

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

export function* CreateUserSaga({image, data, setErr}){
  console.log(data, image,"saga")
  yield call(addUser, image, data, setErr)
}

export function* EditUserSaga({data}){
  yield call(editUser, data)
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

export function* getStorySagaAll() {
  const hits = yield call(getAllStorySaga)
  yield put(getAllStoryActionSuc(hits))
}

export function* deleteStorySaga(id) {
  yield call(deleteStoryApi, id)
}

export function* sendAnswer (data) {
  yield call(sendAnswerApi, data)
}

export function* watchClickSaga() {
  yield takeEvery(LOGIN_REQUEST, handleLatestNews);
  yield takeEvery(OPEN_MODAL, openModal);
  yield takeEvery(NEWS_REQUEST, getAllNews);
  yield takeEvery(ADD_NEWS, AddNewsSaga);
  yield takeEvery(CREATE_USER, CreateUserSaga);
  yield takeEvery(EDIT_USER, EditUserSaga);
  yield takeEvery(REMOVE_NEWS, RemoveNewsSaga);
  yield takeEvery(GET_ALL_USERS_REQUEST, getAllUsersSaga)
  yield takeEvery(DELETE_USER, deleteUserSaga)
  yield takeEvery(GET_ALL_STORY_ACTION, getStorySagaAll)
  yield takeEvery(DELETE_STORY, deleteStorySaga)
  yield takeEvery(SEND_ANSWER, sendAnswer)
}

export default function* rootSaga() {
  yield watchClickSaga();
}
