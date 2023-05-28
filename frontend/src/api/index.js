const main_api = 'https://aman-alert.herokuapp.com/api'

const user = localStorage.getItem('token');
const token = user && JSON.parse(user).message;
console.log(token, "token")
const headers =  {
  'Accept': 'application/json',
  // 'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>',
  'Authorization': `Bearer ${token}`
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
  return await res.json()
}
export const AddNews = async (data) => {
  const res = await fetch(`${main_api}/news/save`, {
    method: 'POST',
    headers,
    body: data
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
  const res = await fetch(`${main_api}/user/delete/${id}`, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
  })
  console.log(res.ok)
  return res.json()
  // handleDeleteUser(res.ok, it)

};

export const getAllStorySaga = async () => {
  const res = await fetch(`${main_api}/form/get-all`, {
    method: 'GET',
    headers
  })

  return await res.json()
};

export const addUser = async (data, setErr) => {
  console.log(data.get("data"))
  const res = await fetch(`${main_api}/user/create`, {
        method: 'POST',
        headers: headers,
    body: data
  })
  console.log(res)
  if(res.ok){
    return res
  }
  else {
    setErr(true)
  }
}

export const deleteStoryApi = async (id) => {
 await fetch(`${main_api}/form/delete/${id.payload}`, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify({
          "formID": id.payload
        })
  })

}

export const sendAnswerApi = async (id) => {
  await fetch(`${main_api}/form/save-answer`, {
         method: 'POST',
        //  headers: headers,
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
         body: JSON.stringify({
          "formId": id.payload.formId,
          "answer": id.payload.title
         })
   })

 }

export const editUser = async (data) => {
  console.log(data.get("data"));
  const res = await fetch(`${main_api}/user/edit`, {
    method: 'POST',
    headers: headers,
    body: data
  })
  return res.json()
}