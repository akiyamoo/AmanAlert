import React, {useCallback, useEffect, useState} from "react";
import styled from "styled-components";
import {ReactComponent as Back} from "../assets/back.svg";
import {useDispatch, useSelector} from "react-redux";
import {addNewsAction, openCloseModal, openCloseModalEdit, openForm, sendAnswerAction} from "../redux/actions/actionCreator";


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
  height: 140px;
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
const ModalFormContent = styled.div`
display: flex;
align-items: center;
justify-content: space-between;
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

function ModalEdit({show}) {
    const [showModal, setShowModal] = useState(null);
    const dispatch = useDispatch();
    const modal = useSelector(store => store.login)
    const data = modal.modalForm;
    const [descr, setDescr] = useState(modal?.modalForm?.answer);

    useEffect(() => {
        setShowModal(show)
    },[show]);

  
    const closeModal = () => {
        dispatch(openForm(null))
    }

    // const sendAnswer = () => {
    //     const answerData = {title:'hello', formId:'5'}
    //     dispatch(sendAnswer(answerData))
    // }

    const sendAnswerFunc = useCallback(() => {
        const answerData = {title: descr, formId: data.id}
        dispatch(sendAnswerAction(answerData))
        window.location.reload()
    }, [descr, data?.id],);

    console.log(modal?.modalForm);
    return <>{showModal ? <ModalContent >
        <ModalContentContainer>
            <Back style={{position: 'absolute', top:'40px', right:"40px",zIndex:105}} onClick={() => closeModal()}/>
            <StoryContentTitle>
                Ответить
            </StoryContentTitle>

            <ModalFormContent>

            <div>
            <ModalLabel>
                ФИО пациента
            </ModalLabel>
            <ModalInput value={data?.name}/>
            </div>
            
            <div>
            <ModalLabel>
            Местонахождение
            </ModalLabel>
            <ModalInput value={data?.eventLocation}/>
            </div>

            <div>
            <ModalLabel>
            Дата события
            </ModalLabel>
            <ModalInput value={data?.eventTime}/>
            </div>

            </ModalFormContent>


            <ModalLabel>
            Описание истории
            </ModalLabel>
            <ModalTextarea value={data?.eventDescription}/>

            <ModalLabel>
            Ответ психолога
            </ModalLabel>
            <ModalTextarea value={descr} onChange={(e) => setDescr(e.target.value)}/>

            <ModalButton onClick={sendAnswerFunc}>
                Сохранить
            </ModalButton>


        </ModalContentContainer>
    </ModalContent> : null}</>


}

export default ModalEdit