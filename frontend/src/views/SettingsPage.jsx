import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {ReactComponent as Star} from "../assets/star-filled.svg";
import {ReactComponent as StarWhite} from "../assets/star.svg";
import {ReactComponent as Trash} from "../assets/trash.svg";
import {ReactComponent as Edit} from "../assets/edit.svg";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getAllUsersReq} from "../redux/actions/actionCreator";
import StoryTableCardItem from "../components/StoryTableCardItem";

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
  text-align: start;
`;
const StoryTableMain = styled.div`
  margin-top: 10px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid gray;
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
const SearchInput = styled.input`
  max-width: 456px;
  width: 100%;
  padding: 6px 8px;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  color: #7A869A;
  border: none;
  background: none;
`;
const SearchInputContent = styled.div`
  max-width: 456px;
  width: 100%;
  border: 1px solid rgb(211, 216, 230);
  border-radius: 4px;
  margin: 0;
`;


const SearchContent = styled.div`
 display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 22px;
  margin-bottom: 12px;
`;


const StoryContentTitleButton = styled.div`
  margin: 0;
  background: #007AFF;
  border-radius: 4px;
  padding: 6px 32px;
  font-size: 14px;
  line-height: 20px;
  color: white;
  font-weight: bold;
  cursor: pointer;
`;
const data1 = [
    {name : "Айнура1 Иманалиева",educ: "Доктор", spec: "Психолог", exp:"12 лет", phone:"+ 996 777 77 77 77"},
    {name : "Айнура2 Иманалиева",educ: "extysq", spec: "trst", exp:"12 лет", phone:"+ 996 888 88 88 88"},
    {name : "Айнура3 Иманалиева",educ: "qwerty", spec: "map", exp:"12 лет", phone:"+ 996 666 66 66 66"},
    {name : "Айнура4 Иманалиева",educ: "Д123", spec: "3423423", exp:"12 лет", phone:"+ 996 555 55 55 55"},
    {name : "Айнура5 Иманалиева",educ: "Доктор наук", spec: "fdsfdsf", exp:"12 лет", phone:"+ 996 444 44 44 44"},
    {name : "Айнура6 Иманалиева",educ: "Доктор наук", spec: "czxcz", exp:"12 лет", phone:"+ 996 333 33 33 33"},
    {name : "Айнура7 Иманалиева",educ: "Доктор наук", spec: "qwe", exp:"12 лет", phone:"+ 996 111 11 11 11"},
    {name : "Айнура8 Иманалиева",educ: "Доктор наук", spec: "45qwer", exp:"12 лет", phone:"+ 996 222 22 22 22"},
]


function SettingPage() {
    const {users} = useSelector(store => store.login)
    const [searchValue, setSearchValue] = useState("")
    const [usersData, setUsersData] = useState(users)
    const dispatch = useDispatch()
    const navigate = useNavigate();

    useEffect(() => {
        const token = JSON.parse(localStorage.getItem('token'))?.message
        if (!token) {
            navigate('/login')
        }
        dispatch(getAllUsersReq())
    },[] )

    const handleSearch = (str) => {
        const res = users ?? [];
        setUsersData(res.filter(it => it.name.includes(str)));
    };

    useEffect(() => {
        handleSearch(searchValue);
    }, [searchValue, users]);

    const handleDeleteUser = (status, it) => {
        if (status) {
            setUsersData(usersData?.filter(el => el.id !== it.id))
            console.log(usersData,"------")
        }
    }
    return (
        <MainContainer>
            <StoryContent>
                <StoryContentTitle>
                    Настройки
                </StoryContentTitle>

                <SearchContent>
                    <SearchInputContent>
                        <SearchInput onChange={(e) => setSearchValue(e.target.value)} placeholder='Искать ФИО'/>
                    </SearchInputContent>
                    <StoryContentTitleButton>
                        Добавить +
                    </StoryContentTitleButton>
                </SearchContent>

                <StoryTableMain>
                    <Star style={{width: "5%", margin: 0}}/>
                    <StoryTableTitle>ФИО психолога</StoryTableTitle>
                    <StoryTableTitle>Образование</StoryTableTitle>
                    <StoryTableTitleSmall>Специальность</StoryTableTitleSmall>
                    <StoryTableTitleSmall>Опыт</StoryTableTitleSmall>
                    <StoryTableTitle>Номер</StoryTableTitle>
                    <StoryTableTitleSmall>Редактировать</StoryTableTitleSmall>
                    <StoryTableTitleSmall>Удалить</StoryTableTitleSmall>
                </StoryTableMain>

                <div style={{marginTop: '10px'}}>
                    {
                        usersData?.map((it,id) => {
                            return(
                                <StoryTableCardItem handleDeleteUser={handleDeleteUser} it={it} id={id}/>
                            )
                        })
                    }
                </div>
            </StoryContent>
        </MainContainer>
    );
}

export default SettingPage;
