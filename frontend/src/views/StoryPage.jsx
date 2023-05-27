import React, {useEffect} from "react";
import styled from "styled-components";
import {ReactComponent as Star} from "../assets/star-filled.svg";
import {ReactComponent as StarWhite} from "../assets/star.svg";
import {ReactComponent as Trash} from "../assets/trash.svg";
import {useNavigate} from "react-router-dom";
import {getAllAnkets} from "../api";

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

    useEffect(() => {
        const token = JSON.parse(localStorage.getItem('token'))?.username
        getAllAnkets()
        if (!token) {
            navigate('/login')
        }
    }, )

    return (
        <MainContainer>
            <StoryContent>
                <StoryContentTitle>
                    Анкеты
                </StoryContentTitle>
                <StoryTableMain>
                    <Star style={{width: "5%", margin: 0}}/>
                    <StoryTableTitle>ФИО пациента</StoryTableTitle>
                    <StoryTableTitle>Местонахождение</StoryTableTitle>
                    <StoryTableTitleSmall>Дата события</StoryTableTitleSmall>
                    <StoryTableTitleSmall>Статус</StoryTableTitleSmall>
                    <StoryTableTitle>Ответ психолога</StoryTableTitle>
                </StoryTableMain>

                <div>
                    <StoryTableCard>
                        <StarWhite style={{width: "5%", margin: 0, cursor: "pointer"}}/>
                        <StoryTableTitle>
                            <StoryTableName>
                                <StoryTableNameImg src='https://pbs.twimg.com/profile_images/1356606198435438595/4Nad8CDb_400x400.jpg'/>
                                <StoryTableNameTitle>Aman</StoryTableNameTitle>
                            </StoryTableName>
                            <StoryTableNameDescr>Эта ситуация произошла со мной
                                год назад, мой муж поднимал на
                                меня руку и иногда приходилось
                            </StoryTableNameDescr>
                        </StoryTableTitle>
                        <StoryTableTitle>Бишкек</StoryTableTitle>
                        <StoryTableTitleSmall>23/09/2022</StoryTableTitleSmall>
                        <StoryTableTitleSmall><StoryTableTitleSmallSuccess>Отправлено</StoryTableTitleSmallSuccess></StoryTableTitleSmall>
                        <StoryTableTitle>Жаль что так случилось, надеюсь мои консультации помогут вам и так
                            далее...</StoryTableTitle>
                        <Trash style={{position: "absolute", margin: 0, top: "10px", right: "10px", cursor: "pointer"}}/>
                    </StoryTableCard>

                    <StoryTableCard>
                        <StarWhite style={{width: "5%", margin: 0, cursor: "pointer"}}/>
                        <StoryTableTitle>
                            <StoryTableName>
                                <StoryTableNameImg src='https://pbs.twimg.com/profile_images/1356606198435438595/4Nad8CDb_400x400.jpg'/>
                                <StoryTableNameTitle>Sasha</StoryTableNameTitle>
                            </StoryTableName>
                            <StoryTableNameDescr>Эта ситуация произошла со мной
                                год назад, мой муж поднимал на
                                меня руку и иногда приходилось
                            </StoryTableNameDescr>
                        </StoryTableTitle>
                        <StoryTableTitle>Бишкек</StoryTableTitle>
                        <StoryTableTitleSmall>23/09/2022</StoryTableTitleSmall>
                        <StoryTableTitleSmall><StoryTableTitleSmallProcess>В процессе</StoryTableTitleSmallProcess></StoryTableTitleSmall>
                        <StoryTableTitle>Жаль что так случилось, надеюсь мои консультации помогут вам и так
                            далее...</StoryTableTitle>
                        <Trash style={{position: "absolute", margin: 0, top: "10px", right: "10px", cursor: "pointer"}}/>
                    </StoryTableCard>

                    <StoryTableCard>
                        <StarWhite style={{width: "5%", margin: 0, cursor: "pointer"}}/>
                        <StoryTableTitle>
                            <StoryTableName>
                                <StoryTableNameImg src='https://pbs.twimg.com/profile_images/1356606198435438595/4Nad8CDb_400x400.jpg'/>
                                <StoryTableNameTitle>Arslan</StoryTableNameTitle>
                            </StoryTableName>
                            <StoryTableNameDescr>Эта ситуация произошла со мной
                                год назад, мой муж поднимал на
                                меня руку и иногда приходилось
                            </StoryTableNameDescr>
                        </StoryTableTitle>
                        <StoryTableTitle>Бишкек</StoryTableTitle>
                        <StoryTableTitleSmall>23/09/2022</StoryTableTitleSmall>
                        <StoryTableTitleSmall><StoryTableTitleSmallNew>Новый</StoryTableTitleSmallNew></StoryTableTitleSmall>
                        <StoryTableTitle>Жаль что так случилось, надеюсь мои консультации помогут вам и так
                            далее...</StoryTableTitle>
                        <Trash style={{position: "absolute", margin: 0, top: "10px", right: "10px", cursor: "pointer"}}/>
                    </StoryTableCard>
                </div>
            </StoryContent>
        </MainContainer>
    );
}

export default StoryPage;
