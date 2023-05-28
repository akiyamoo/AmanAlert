import React, {useEffect} from "react";
import styled from "styled-components";
import {ReactComponent as Star} from "../assets/star-filled.svg";
import {ReactComponent as StarWhite} from "../assets/star.svg";
import {ReactComponent as Trash} from "../assets/trash.svg";
import {useNavigate} from "react-router-dom";
import {getAllAnkets} from "../api";
import { useDispatch, useSelector } from "react-redux";
import { deleteStory, getAllStoryAction, openForm } from "../redux/actions/actionCreator";
import {ReactComponent as Edit} from "../assets/edit.svg";

const MainContainer = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
`;
const StoryContent = styled.div`
  padding: 30px 50px;
  width: 100%;
  height: 100%;
`;
const StoryContentTitle = styled.p`
  font-weight: 600;
  font-size: 48px;
  line-height: 84px;
  text-align: center;
  margin-bottom: 80px;
  text-align: start;
`;
const StoryTableMain = styled.div`
  margin-top: 40px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
const StoryTableTitle = styled.p`
  text-align: start;
  font-weight: 600;
  font-size: 12px;
  line-height: 16px;
  color: #6B778C;
  margin: 0;
  width: 20%;
  padding: 10px;
`;
const StoryTableTitleSmall = styled.p`
  text-align: start;
  font-weight: 600;
  font-size: 12px;
  line-height: 16px;
  color: #6B778C;
  margin: 0;
  width: 12%;
  padding: 10px;
`;

const StoryTableTitleSmallSuccess = styled.div`
  background: rgba(0, 171, 154, 0.12);
  border-radius: 12px;
  color: #1BC5BD;
  max-width: 100px;
  width: 100%;
  margin: 0;
  padding: 8px;
  text-align: center;
`;
const StoryTableTitleSmallProcess = styled.p`
  background: #FFD5C4;
  border-radius: 12px;
  max-width: 100px;
  width: 100%;
  margin: 0;
  padding: 8px;
  text-align: center;
`;
const StoryTableTitleSmallNew = styled.p`
  background: rgba(103, 173, 255, 0.28);
  border-radius: 12px;
  color: #007AFF;
  max-width: 100px;
  width: 100%;
  margin: 0;
  padding: 8px;
  text-align: center;
`;
const StoryTableName = styled.p`
  display: flex;
  align-items: center;
  justify-content: start;
`;
const StoryTableNameTitle = styled.p`
  margin: 0;
  margin-left: 12px;
`;
const StoryTableNameImg = styled.img`
  margin: 0;
  width: 24px;
`;

const StoryTableNameDescr = styled.p`
  margin: 0;
  font-weight: 500;
  font-size: 13px;
  line-height: 20px;
  color: #ADB1B5;
  margin-top: 20px;
`;

const StoryTableCard = styled.div`
  border-radius: 8px;
  padding: 20px 0;
  display: flex;
  align-items: start;
  justify-content: space-between;
  position: relative;
  &:hover {
    border: 1px solid #D3D8E6;
    box-shadow: 0px 4px 24px rgba(106, 122, 152, 0.15);
  }
`;

function StoryPage() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {storyUsers} = useSelector(store => store.login)
    const role = JSON.parse(localStorage.getItem('token'))?.role

    useEffect(() => {
        const token = JSON.parse(localStorage.getItem('token'))?.username
        getAllAnkets()
        if (!token) {
            navigate('/login')
        }
    }, )

    useEffect(() =>{
      dispatch(getAllStoryAction())
    },[])

    const reload = () => {
      window.location.reload()
    }

    const deleteHandler = (id) => {
      dispatch(deleteStory(id))
      setTimeout(reload, 1000);
    }

    const openEditModal = (item) => {
      dispatch(openForm(item))
    }

    return (
        <MainContainer>
            <StoryContent>
                <StoryContentTitle>
                    Анкеты
                </StoryContentTitle>
                <StoryTableMain>
                    <StoryTableTitle>ФИО пациента</StoryTableTitle>
                    <StoryTableTitle>Местонахождение</StoryTableTitle>
                    <StoryTableTitleSmall>Дата события</StoryTableTitleSmall>
                    <StoryTableTitleSmall>Статус</StoryTableTitleSmall>
                    <StoryTableTitle>Ответ психолога</StoryTableTitle>
                </StoryTableMain>


                <div>
                {storyUsers?.map((item) => <StoryTableCard>
                        <StoryTableTitle>
                            <StoryTableName>
                                <StoryTableNameImg src='https://pbs.twimg.com/profile_images/1356606198435438595/4Nad8CDb_400x400.jpg'/>
                                <StoryTableNameTitle>{item.name}</StoryTableNameTitle>
                            </StoryTableName>
                            <StoryTableNameDescr>{item.eventDescription}
                            </StoryTableNameDescr>
                        </StoryTableTitle>
                        <StoryTableTitle>{item.eventLocation}</StoryTableTitle>
                        <StoryTableTitleSmall>{item.eventTime}</StoryTableTitleSmall>
                        <StoryTableTitleSmall>{
                          item.answer ?
                          <StoryTableTitleSmallSuccess>Отправлено</StoryTableTitleSmallSuccess>
                        :
                        <StoryTableTitleSmallNew>Новое</StoryTableTitleSmallNew>
                        }
                          </StoryTableTitleSmall>
                        <StoryTableTitle>{item.answer}</StoryTableTitle>
                        {role === 'ADMIN' &&
                          <>
                          <Trash onClick={() => deleteHandler(item.id)} style={{position: "absolute", margin: 0, top: "10px", right: "10px", cursor: "pointer"}}/>
                          <Edit onClick={() => openEditModal(item)} style={{position: "absolute", margin: 0, top: "6px", right: "30px", cursor: "pointer", width:"20px"}}/>
                          </>}
                    </StoryTableCard>)}
                </div>
            </StoryContent>
        </MainContainer>
    );
}

export default StoryPage;
