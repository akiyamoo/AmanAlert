const main_api = 'https://aman-alert.herokuapp.com/api'

const token = localStorage.getItem('token');
const role = token == null ? null : JSON.parse(token).message

const headers =  {
      'Accept': 'application/json',
      'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>',
      'Authorization': `Bearer ${role}`
}

export const loginWithPassword = async ({login, password}) => {

  const res = await fetch(`${main_api}/auth/signin`, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      "username": `${login}`,
      "password": `${password}`
    })
  });
  return await res.json();
};
export const getAllNewsApi = async () => {

  const res = await fetch(`${main_api}/news/get-all`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
  });
  return await res.json();
};

export const getAllAnkets = async () => {
  const res = await fetch(`${main_api}/form/get-all`, {
    method: 'GET',
    headers,
  });
  return await res.json();
};

export const getAllUsersApi = async () => {
  const res = await fetch(`${main_api}/user/get-all-web`,{
    method: 'GET',
    headers,
  })
  console.log(role)
  return await res.json()
}
export const AddNews = async (image, data) => {
  console.log(data)
  const res = await fetch(`${main_api}/news/save`, {
    method: 'POST',
    headers,
    body: JSON.stringify({
      "image": image,
      "data": data
    })
  })
}
  export const RemoveNews = async (id) => {
    const res = await fetch(`${main_api}/news/delete/${id}`, {
      method: 'POST',
      headers,
      body: JSON.stringify({
        "newsId": id
      })
    })

    return await res.json()
  }
export const deleteUserApi = async (id) => {
  console.log(id)
  const res = await fetch(`https://aman-alert.herokuapp.com/api/user/delete/${id}`, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${role}`
    },
  })
  console.log(res.ok)
  return res.json()
  // handleDeleteUser(res.ok, it)

};