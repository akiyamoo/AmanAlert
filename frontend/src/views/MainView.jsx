import React, {useEffect} from "react";
import styled from "styled-components";
import {ReactComponent as MainIcon} from "../assets/MainViewIcon.svg";
import {useNavigate} from "react-router-dom";

const MainContainer = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
`;
const MainContent = styled.div``;
const MainContentTitle = styled.p`
  font-weight: 600;
  font-size: 48px;
  line-height: 84px;
  text-align: center;
  margin-bottom: 80px;
`;

function MainView() {
    const navigate = useNavigate();

    useEffect(() => {
        const token = JSON.parse(localStorage.getItem('token'))?.username
        if (!token){
            navigate('/login')
        }
    },)

    return (
       <MainContainer>
           <MainContent>
                <MainContentTitle>
                    Добро пожаловать!
                </MainContentTitle>
               <MainIcon style={{display:'flex',justifyContent:"center"}}/>
           </MainContent>
       </MainContainer>
    );
}

export default MainView;
