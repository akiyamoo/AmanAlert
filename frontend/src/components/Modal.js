import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {ReactComponent as Back} from "../assets/back.svg";
import {useDispatch, useSelector} from "react-redux";
import {addNewsAction, openCloseModal, openCloseModalEdit} from "../redux/actions/actionCreator";


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
`;
const ModalTextarea = styled.textarea`
  height: 200px;
  width: 90%;
  background: #FFFFFF;
  border: 1px solid #D3D8E6;
  border-radius: 4px;
  font-weight: 400;
  font-size: 16px;
  line-height: 20px;
  display: flex;
  align-items: center;
  color: #172B4D;
  padding: 12px;
  margin: 0;
  margin-bottom: 44px;
`;
const ModalInputImg = styled.input`

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
  position: absolute;
  bottom: 40px;
  left: 40px;
  ${props => props.error && `background: red;`}
  
`;
const data = new window.FormData();
const imageForm = new window.FormData();
function Modal({show}) {
    const [showModal, setShowModal] = useState(false);
    const [title, setTitle] = useState('');
    const [descr, setDescr] = useState('');
    const [image, setImage] = useState('');
    const [error, setErroe] = useState(false);
    const dispatch = useDispatch();
    const modal = useSelector(store => store.login)


    useEffect(() => {
        data.append("data", {
            title: title,
            description: descr
        });

        imageForm.append("image", image);
    },[title, descr, image])

    const createNews = (imageForm, data) => {
        const formData = new FormData();
        formData.append("data", JSON.stringify({title, description: descr}));
        formData.append("image", image);
        if (image && title && descr) {
            dispatch(addNewsAction(formData))
            setShowModal(false)
            window.location.reload()
        } else {
            setErroe(true)
        }
    }

    const [user, setUser] = useState({})
    const userFormData = new FormData()
    useEffect(() => {
        setShowModal(show)
    },[show]);

    const hendleChange = (str, elem) => {
        userFormData.append(str, elem)
    }

    const onChangeImage = (e) => {
        const file = e.target.files?.[0]; // Предполагается, что переменная event содержит событие, переданное в функцию обработчика выбора файла

        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                const base64String = reader.result;
                setImage(base64String)
            };
            reader.readAsDataURL(file);
        }
    }
    const closeModal = () => {
        setErroe(false)
        dispatch(openCloseModal(false))
        dispatch(openCloseModalEdit(false))

    }

    return <>{showModal ? <ModalContent >
        <ModalContentContainer>
            <Back style={{position: 'absolute', top:'40px', right:"40px",zIndex:105}} onClick={() => closeModal()}/>
            <StoryContentTitle>
                {modal.modalStatusEdit ? "Редактировать новость" : "Добавить новость"}
            </StoryContentTitle>

            <ModalLabel>
                Название новости
            </ModalLabel>
            <ModalInput onChange={(e) => setTitle(e.target.value)}/>


            <ModalLabel>
                Описание новости
            </ModalLabel>
            <ModalTextarea onChange={(e) => setDescr(e.target.value)}/>

            <ModalLabel>
                Загрузить картину
            </ModalLabel>
            <ModalInputImg type="file" accept="image/*,.pdf"  onChange={(e) => setImage(e.target.files?.[0])}/>

            <ModalButton error={error} onClick={() =>  createNews(imageForm, data)}>
                Сохранить
            </ModalButton>

            {error && <p style={{color:'red',marginTop:'50px'}}>Заполните все поля</p>}

        </ModalContentContainer>
    </ModalContent> : null}</>


}

export default Modal