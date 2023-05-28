import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {ReactComponent as Back} from "../assets/back.svg";
import {useDispatch} from "react-redux";
import {editUserAction, openCloseModal} from "../redux/actions/actionCreator";
import {ReactComponent as PasswordLogo} from "../assets/Icon.svg";


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
  margin-top: 20px;
  cursor: pointer;
`;

const PasswordContent = styled.div`
  position: relative;
  width: 200px;
  margin: 0;
`;



function EditUserModal({show, setShowModal, it}) {

    const [user, setUser] = useState({...it})
    const [image, setImage] = useState("")
    const [password, setPassword] = useState("")
    const [inputType, setInputType] = useState(true)
    const changeInputType = () => {
        setInputType(!inputType)
    }
    const onChangeImage = (e) => {
        const file = e?.target?.files?.[0]; // Предполагается, что переменная event содержит событие, переданное в функцию обработчика выбора файла

        if (file) {
            setImage(file)
        }
    }
    
    const dispatch = useDispatch()
    useEffect(() => {
        setShowModal(show)
    },[show]);
    console.log(user)
    const handleChange = (str, elem) => {
        const updatedUser = { ...user, [str]: elem , birthDate : new Date(user.birthDate).getTime() };
        updatedUser.experience = updatedUser.experience?.split(" ")[0]
        delete updatedUser?.urlImage

        setUser(updatedUser);

    };

    const sentDate = () => {
        const userFormData = new FormData();
        userFormData.append("data",  JSON.stringify(user));
        userFormData.append("image",  image);

        dispatch(editUserAction(userFormData))

        console.log(userFormData.get("image"))

    }

    return <>{show ? <ModalContent >
        <ModalContentContainer>
            <Back style={{position: 'absolute', top:'40px', right:"40px",zIndex:105}} onClick={() => setShowModal(false)}/>
            <StoryContentTitle>
            Редактировать пользователя
            </StoryContentTitle>

            <ModalLabel>
                ФИО
            </ModalLabel>
            <ModalInput value={user.name} onChange={(e) => handleChange("name", e.target.value )} placeholder={"ФИО"}/>

            <ModalLabel>
                Образование
            </ModalLabel>
            <ModalInput value={user.education} onChange={(e) => handleChange("education", e.target.value )} placeholder={"Образование"}/>

            <ModalLabel>
                Специальность
            </ModalLabel>
            <ModalInput value={user.position} onChange={(e) => handleChange("position", e.target.value )} placeholder={"Специальность"}/>
            <ModalLabel>
                Опыт
            </ModalLabel>
            <ModalInput value={user.experience?.split(" ")[0]} onChange={(e) => handleChange("experience", e.target.value )} placeholder={"Опыт"}/>
            <ModalLabel>
                Номер
            </ModalLabel>
            <ModalLabel >
                Введите пароль
            </ModalLabel>
            <PasswordContent>
                <ModalInput onChange={e =>handleChange("password", e.target.value )} type={inputType ? "password" : "text"} placeholder='Пароль'/>
                <PasswordLogo style={{position:"absolute", right:"10px", top:"12px"}} onClick={changeInputType}/>
            </PasswordContent>
            <ModalInput value={user.phone} onChange={(e) => handleChange("phone", e.target.value )} placeholder={"Номер"}/>
            <ModalLabel>
                Фотография
            </ModalLabel>
            <input onChange={onChangeImage}  type="file" accept="image/*,.pdf"/>

            <ModalButton onClick={sentDate}>
                Сохранить
            </ModalButton>

        </ModalContentContainer>
    </ModalContent> : null}</>


}

export default EditUserModal