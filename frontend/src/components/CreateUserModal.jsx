import React, {useEffect, useRef, useState} from 'react';
import {ReactComponent as Back} from "../assets/back.svg";
import styled from "styled-components";
import {ReactComponent as PasswordLogo} from "../assets/Icon.svg";
import {createUserAction} from "../redux/actions/actionCreator";
import {useDispatch} from "react-redux";
const ModalContent = styled.body`
  width: 100%;
  position: fixed;
  height: 100vh;
  top: 0;
  right: 0;
  background: rgba(0, 0, 0, 0);
  backdrop-filter: blur(5px);
  z-index: 99;
  overflow: hidden;
`;

const ModalContentContainer = styled.div`
  width: 60%;
  background: #FFFFFF;
  border: 1px solid #D3D8E6;
  box-shadow: 0px 1px 12px rgba(106, 122, 152, 0.12);
  border-radius: 8px;
  height: 80%;
  z-index: 100;
  position: absolute;
  top: 10%;
  left: 20%;
  padding: 40px;
  overflow: auto;
`;
const StoryContentTitle = styled.p`
  margin: 0;
  font-weight: 600;
  font-size: 48px;
  line-height: 84px;
  text-align: start;
`;

const ModalLabel = styled.p`
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  color: #172B4D;
  margin-bottom: 8px;
`;
const ModalInput = styled.input`
  background: #FFFFFF;
  border: 1px solid #D3D8E6;
  border-radius: 4px;
  width: 200px;
  font-weight: 400;
  font-size: 16px;
  line-height: 20px;
  display: flex;
  align-items: center;
  color: #172B4D;
  margin: 0;
  margin-bottom: 32px;
  height: 50px;
  padding-left: 5px;
`;

const ModalSelect = styled.select`
  background: #FFFFFF;
  border: 1px solid #D3D8E6;
  border-radius: 4px;
  width: 200px;
  font-weight: 400;
  font-size: 16px;
  line-height: 20px;
  display: flex;
  align-items: center;
  color: #172B4D;
  margin: 0;
  margin-bottom: 32px;
  height: 50px;
  padding-left: 5px;
`

const ModalButton = styled.div`
  background: #007AFF;
  border-radius: 4px;
  padding: 16px 84px;
  font-weight: 600;
  font-size: 20px;
  line-height: 24px;
  text-align: center;
  color: #FFFFFF;
  width: 280px;
  margin-top: 50px;
  
`;

const PasswordContent = styled.div`
  position: relative;
  width: 200px;
  margin: 0;
`;

const ErrorText = styled.p`
  color: red;
  margin-top: 10px;
`


const CreateUserModal = ({show, setShowModal}) => {
    const [inputType, setInputType] = useState(true)
    const [err, setErr] = useState(false)
    const nicknameRef = useRef(null);
    const passwordRef = useRef(null);
    const phoneNumberRef = useRef(null);
    const roleRef = useRef(null);
    const birthDateRef = useRef(null);
    const fullNameRef = useRef(null);
    const emailRef = useRef(null);
    const experienceRef = useRef(null);
    const specialtyRef = useRef(null);
    const educationRef = useRef(null);
    const photoRef = useRef(null);
    const dispatch = useDispatch();
    const [image, setImage] = useState("")
    const changeInputType = () => {
        setInputType(!inputType)
    }
    useEffect(() => {
        setShowModal(show)
    },[show]);

    const onChangeImage = (e) => {
        const file = e?.target?.files?.[0]; // Предполагается, что переменная event содержит событие, переданное в функцию обработчика выбора файла

        if (file) {
                setImage(file)
        }
    }

    useEffect(() => {
        console.log(err)
    },[err])


    const sentDate = async () => {
        try {
            const date = new Date(birthDateRef.current.value).getTime();
            if (
                nicknameRef.current.value &&
                passwordRef.current.value &&
                phoneNumberRef.current.value &&
                roleRef.current.value &&
                date &&
                fullNameRef.current.value &&
                emailRef.current.value &&
                experienceRef.current.value &&
                specialtyRef.current.value &&
                educationRef.current.value &&
                image
            ) {
                setErr(false);
                const userFormData = new FormData();
                const data = JSON.stringify({
                    username: nicknameRef.current.value,
                    password: passwordRef.current.value,
                    phone: phoneNumberRef.current.value,
                    role: roleRef.current.value,
                    birthDate: date,
                    name: fullNameRef.current.value,
                    email: emailRef.current.value,
                    experience: experienceRef.current.value,
                    position: specialtyRef.current.value,
                    education: educationRef.current.value,
                });
                userFormData.append("data", data);
                userFormData.append("image", image);

               const res = dispatch(createUserAction(userFormData, setErr));
                console.log(res)
            } else {
                setErr(true);
            }
        } catch (error) {
         return console.error("Произошла ошибка при создании пользователя:", error);
        }
        setShowModal(false);
    };


    return <>{show ? <ModalContent >
        <ModalContentContainer>
            <Back style={{position: 'absolute', top:'40px', right:"40px",zIndex:105}} onClick={() => setShowModal(false)}/>
            <StoryContentTitle>
                Создать пользователя
            </StoryContentTitle>

            <ModalLabel>
                Никнейм
            </ModalLabel>
            <ModalInput ref={nicknameRef} placeholder={"Никнейм"}/>
                <ModalLabel >
                    Введите пароль
                </ModalLabel>
                <PasswordContent>
                    <ModalInput ref={passwordRef} type={inputType ? "password" : "text"} placeholder='Пароль'/>
                    <PasswordLogo style={{position:"absolute", right:"10px", top:"12px"}} onClick={changeInputType}/>
                </PasswordContent>

            <ModalLabel>
                Номер
            </ModalLabel>
            <ModalInput ref={phoneNumberRef} placeholder={"Номер"}/>
            <ModalLabel>
                Роль
            </ModalLabel>
            <ModalSelect ref={roleRef} name="" id="">
                <option defaultChecked value="WEB_USER">Пользователь</option>
                <option value="ADMIN">Админ</option>
            </ModalSelect>

            <ModalLabel>
                Дата рождения
            </ModalLabel>
            <ModalInput ref={birthDateRef} type={"date"} placeholder={" Дата рождения "}/>

            <ModalLabel>
                ФИО
            </ModalLabel>
            <ModalInput ref={fullNameRef} placeholder={"ФИО"}/>

            <ModalLabel>
                Почта
            </ModalLabel>
            <ModalInput ref={emailRef} type={"email"} placeholder={"Почта"}/>

            <ModalLabel>
                Опыт
            </ModalLabel>
            <ModalInput ref={experienceRef} placeholder={"Опыт"}/>

            <ModalLabel>
                Специальность
            </ModalLabel>
            <ModalInput ref={specialtyRef} placeholder={"Специальность"}/>

            <ModalLabel>
                Образование
            </ModalLabel>
            <ModalInput ref={educationRef} placeholder={"Образование"}/>

            <ModalLabel>
                Фотография
            </ModalLabel>
            <input onChange={onChangeImage}  type="file" accept="image/*,.pdf"/>

            {
                err && <ErrorText>Поля заполнены не правильно</ErrorText>
            }

            <ModalButton onClick={sentDate}>
                Создать
            </ModalButton>

        </ModalContentContainer>
    </ModalContent> : null}</>


};

export default CreateUserModal;