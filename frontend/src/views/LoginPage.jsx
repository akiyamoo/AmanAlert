import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {useNavigate} from 'react-router-dom';
import {ReactComponent as PasswordLogo} from "../assets/Icon.svg";
import {useDispatch, useSelector} from "react-redux";
import {getAuthReq} from "../redux/actions/actionCreator";

const LoginContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background: white;
  display: flex;
  align-content: center;
  justify-content: center;
`;

const LoginContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;
const LoginBlock = styled.div`
  width: 456px;
  height: 266px;
  border: 1px solid #E2E4E5;
  border-radius: 8px;
  padding: 32px;
`;
const LoginButton = styled.button`
  width: 456px;
  background: #007AFF;
  border-radius: 4px;
  padding: 18px;
  color: white;
  margin-top: 34px;
  text-align: center;
  font-weight: 600;
  font-size: 20px;
  line-height: 24px;
  border: none;
`;
const DisplayBlock = styled.div`
  display: block;
`;
const LoginLabel = styled.div`
  border-bottom: 1px solid gray;
  ${props => props.error && 'border-bottom: 1px solid red;'}
  width: 100%;
  margin-top: ${props => props.mt && props.mt};
`;
const LoginTitle = styled.p`
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  ${props => props.error && 'color: red; margin-top: 10px;'}
`;
const PasswordContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
const LoginInput = styled.input`
  border: none;
  background: none;
  width: 100%;
  padding: 8px 16px;
  font-weight: 400;
  font-size: 18px;
  line-height: 28px;
  margin-top: 8px;
  outline: none;
  ${props => props.error && 'color: red;'}
`;

function LoginPage() {
    const [inputType, setInputType] = useState(true)
    const [error, setError] = useState(false)
    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate();
    const changeInputType = () => {
        setInputType(!inputType)
    }
    const dispatch = useDispatch()
    const userData = useSelector(store => store.login)
    const loginUser = () => {
        if (login && password) {
            dispatch(getAuthReq(login, password))
        }
    }

    useEffect(() => {
        if (userData?.authData.id) {
            localStorage.setItem('token', JSON.stringify(userData.authData))
            navigate('/')
            // window.location.reload();
        } else if (userData.authData?.message) {
            setError(true)
        }
    },[userData?.authData])

    useEffect(() => {
            setError(false)
    },[login, password])

    return (
        <LoginContainer>
            <LoginContent>
                <DisplayBlock>
                    <LoginBlock>

                        <LoginLabel error={error}>
                            <LoginTitle error={error}>Логин</LoginTitle>
                            <LoginInput value={login} onChange={(e) => setLogin(e.target.value)} placeholder='Логин'/>
                        </LoginLabel>

                        <LoginLabel error={error} mt='32px'>
                            <LoginTitle error={error}>
                                Введите пароль
                            </LoginTitle>
                            <PasswordContent>
                                <LoginInput onChange={(e) => setPassword(e.target.value)} value={password}
                                            type={inputType ? "password" : "text"} placeholder='Пароль'/>
                                <PasswordLogo onClick={changeInputType}/>
                            </PasswordContent>
                        </LoginLabel>

                        {error ? <LoginTitle error={error} >{userData.authData?.message}</LoginTitle> : null}

                    </LoginBlock>
                    <LoginButton onClick={loginUser}>
                        Войти в систему
                    </LoginButton>
                </DisplayBlock>
            </LoginContent>
        </LoginContainer>
    );
}

export default LoginPage;
