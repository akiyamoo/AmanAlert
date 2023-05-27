import React, {useEffect} from "react";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getAllNewsReq, getLatestNews, openCloseModal, removeNewsAction} from "../redux/actions/actionCreator";
import {ReactComponent as More} from "../assets/more.svg";

const MainContainer = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  position: relative;
`;
const StoryContent = styled.div`
  padding: 30px 30px;
  width: 100%;
  height: 100%;
`;
const StoryContentTitle = styled.p`
  margin: 0;
  font-weight: 600;
  font-size: 48px;
  line-height: 84px;
  text-align: start;
`;

const StoryContentTitleContent = styled.div`
    display: flex;
  align-items: center;
  justify-content: space-between;
`;
const StoryContentTitleButton = styled.div`
  margin: 0;
  background: #007AFF;
  border-radius: 4px;
  margin-right: 100px;
  padding: 6px 32px;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  color: white;
  font-weight: bold;
  cursor: pointer;
`;

const MainNewsContent = styled.div`
  border-radius: 12px;
  border: 1px solid gray;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-height: 500px;
  height: 100%;
`;

const MainNewsContentPhoto = styled.div`
  border-radius: 12px;
  max-height: 500px;
  margin: 0;
  width: 75%;
  position: relative;
`;
const MainNewsPhoto = styled.img`
  border-radius: 12px;
  object-fit: cover;
  width: 100%;
  max-height: 500px;
  height: 100%;
`;

const MainNewsContentTitle = styled.p`
  width: 25%;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 14;
  -webkit-box-orient: vertical;
  margin-left: 15px;
  font-size: 25px;
`;
const MainNewsContentTitleYear = styled.span`
  text-transform: uppercase;
  font-size: 30px;
  margin-right: 4px;
`;

const MainNewsContentImageTextMain = styled.span`
  position: absolute;
  left: 50px;
  bottom: 100px;
  color: white;
  font-size: 45px;
  width: 70%;
  font-weight: bold;
`;
const MainNewsContentImageTextContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 30%;
  left: 50px;
  bottom: 50px;
  position: absolute;
`;
const MainNewsContentImageText = styled.span`
  color: white;
  font-weight: bold;
`;

const NewsCartContent = styled.div`
  padding-bottom: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
`;

const NewsCart = styled.div`
  border: 1px solid #D3D8E6;
  box-shadow: 0px 1px 2px rgba(106, 122, 152, 0.15);
  border-radius: 8px;
  padding: 8px;
  max-width: 290px;
  width: 100%;
  height: 300px;
  margin: 5px;
`;

const NewsCartPhoto = styled.img`
 object-fit: cover;
  max-width: 350px;
  width: 100%;
  height: 175px;
  border-radius: 8px;
`;
const NewsCartTitle = styled.h3`
  margin-top: 10px;
`;
const NewsCartDesc = styled.p`
  font-size: 10px;
  margin-top: 10px;
  color: #949494;;
`;
const NewsCartDescContent = styled.div`
  display: flex;
  max-width: 120px;
  margin: 0;
`;

const NewsCartPhotoContent = styled.div`
  position: relative;
`;

function StoryPage() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const news = useSelector(store => store.login)
    const token = localStorage.getItem('token');
    const role = JSON.parse(token).role

    useEffect(() => {
        dispatch(getAllNewsReq())
        if (!token) {
            navigate('/login')
        }
    }, [])

    return (
        <MainContainer>
            <StoryContent>
                <StoryContentTitleContent>
                    <StoryContentTitle>
                        Главные новости
                    </StoryContentTitle>
                    {role === 'ADMIN' && <StoryContentTitleButton onClick={() => dispatch(openCloseModal(true))}>
                        Добавить +
                    </StoryContentTitleButton>}
                </StoryContentTitleContent>

                <MainNewsContent>
                    <MainNewsContentPhoto>
                        {role === 'ADMIN' && <More onClick={() => dispatch(removeNewsAction(news?.news[0].id))} style={{position: "absolute", bottom: "20px", right: "20px"}}/>}
                        <MainNewsPhoto src={news?.news[0]?.urlImage}/>
                        <MainNewsContentImageTextMain>{news?.news[0]?.title}</MainNewsContentImageTextMain>
                        <MainNewsContentImageTextContent>
                            <MainNewsContentImageText>2h ago</MainNewsContentImageText>
                            <MainNewsContentImageText>7Kanal</MainNewsContentImageText>
                        </MainNewsContentImageTextContent>
                    </MainNewsContentPhoto>
                    <MainNewsContentTitle>
                        <MainNewsContentTitleYear>2030</MainNewsContentTitleYear> ,
                        {news?.news[0]?.description}
                    </MainNewsContentTitle>
                </MainNewsContent>

                <StoryContentTitle style={{marginTop: '40px'}}>
                    Последние новости
                </StoryContentTitle>

                <NewsCartContent>
                    {news?.news.map((item) => {
                        return (
                            <NewsCart>
                                <NewsCartPhotoContent>
                                    {role === 'ADMIN' && <More onClick={() => dispatch(removeNewsAction(item.id))} style={{position: "absolute", bottom: "10px", right: "10px", width: "5px"}}/>}
                                    <NewsCartPhoto src={item.urlImage}/>
                                </NewsCartPhotoContent>
                                <NewsCartTitle>{item.title}</NewsCartTitle>
                                <NewsCartDescContent>
                                    <NewsCartDesc>1 Hour Ago</NewsCartDesc>
                                    <NewsCartDesc>Бишкек</NewsCartDesc>
                                </NewsCartDescContent>
                            </NewsCart>
                        )
                    })}
                </NewsCartContent>

            </StoryContent>

        </MainContainer>
    );
}

export default StoryPage;
