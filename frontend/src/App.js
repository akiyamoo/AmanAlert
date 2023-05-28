import React, {useEffect, useState} from 'react';
import {BrowserRouter, Route, Routes, Link} from 'react-router-dom';
import styled from "styled-components";

import LoginPage from "./views/LoginPage";
import './App.css';
import MainView from "./views/MainView";
import NavBar from "./components/NavBar";
import {ReactComponent as MainICon} from "./assets/mainIcon.svg";
import {ReactComponent as AnkeICon} from "./assets/anketIcon.svg";
import {ReactComponent as NewsIcon} from "./assets/newsIcon.svg";
import {ReactComponent as SettingICon} from "./assets/settingsIcon.svg";
import {ReactComponent as MainIConActive} from "./assets/mainIconActive.svg";
import {ReactComponent as AnkeIConActive} from "./assets/anketIconActive.svg";
import {ReactComponent as NewsIconActive} from "./assets/newsIconActive.svg";
import {ReactComponent as SettingIConActive} from "./assets/settingsIconActive.svg";
import StoryPage from "./views/StoryPage";
import NewsPage from "./views/NewsPage";
import SettingPage from "./views/SettingsPage";
import Modal from "./components/Modal";
import {useSelector} from "react-redux";
import ModalEdit from './components/ModalEditForm';

const NavbarContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const MenuContent = styled.div`
  width: 20%;
  height: 100vh;
  margin: 0;
  border-right: 1px solid gray;
`;
const PageContent = styled.div`
  width: 80%;
  height: 100vh;
  margin: 0;
`;
const LinkContent = styled.div`
  margin-top: 75px;
  margin-left: 8px;
`;
const MyLink = styled(Link)`
  text-decoration: none !important;
  display: flex;
  align-items: center;
  color: #44546F;
  width: 100%;
  padding: 8px;
  background: ${props => props.active ? '#E9F2FF' : 'none'};
  color: ${props => props.active ? '#0C66E4' : 'none'};
  border-bottom-left-radius: 4px;
  border-top-left-radius: 4px;
`;
const MyLinkTitle = styled.p`
  margin: 0px;
  margin-left: 16px;
`;
function App() {
    const [activePath,setActivePath] = useState(window.location.pathname)
    const modal = useSelector(store => store.login)

    useEffect(() => {
        if (window.location.pathname === '') {
            setActivePath('/')
        } else {
            setActivePath(window.location.pathname)
        }
    },[window.location.pathname])

    return (
        <BrowserRouter>
            <NavBar/>
            <Modal show={modal?.modalStatus}></Modal>
            <ModalEdit show={modal?.modalForm}></ModalEdit>
            <NavbarContainer>
                <MenuContent>
                    <LinkContent>
                        <MyLink to='/' onClick={() => setActivePath('/')} active={activePath === '/'}>
                            {activePath === '/' ? <MainIConActive style={{margin: 0}}/> : <MainICon style={{margin: 0}}/>}
                            <MyLinkTitle>Главная</MyLinkTitle>
                        </MyLink>
                        <MyLink to='/story' onClick={() => setActivePath('/story')} active={activePath === '/story'}>
                            {activePath === '/story' ? <AnkeIConActive style={{margin: 0}}/> : <AnkeICon style={{margin: 0}}/>}
                            <MyLinkTitle>Анкеты</MyLinkTitle>
                        </MyLink>
                        <MyLink to='/news' onClick={() => setActivePath('/login')} active={activePath === '/news'}>
                            {activePath === '/news' ? <NewsIconActive style={{margin: 0}}/> : <NewsIcon style={{margin: 0}}/>}
                            <MyLinkTitle>Новости</MyLinkTitle>
                        </MyLink>
                        <MyLink to='/settings' onClick={() => setActivePath('/settings')} active={activePath === '/settings'}>
                            {activePath === '/settings' ? <SettingIConActive style={{margin: 0}}/> : <SettingICon style={{margin: 0}}/>}
                            <MyLinkTitle>Настройки</MyLinkTitle>
                        </MyLink>
                    </LinkContent>
                </MenuContent>

                <PageContent>
                    <Routes>
                        <Route path="/" element={<MainView/>}/>
                    </Routes>
                    <Routes>
                        <Route path="/story" element={<StoryPage/>}/>
                    </Routes>
                    <Routes>
                        <Route path="/news" element={<NewsPage/>}/>
                    </Routes>
                    <Routes>
                        <Route path="/settings" element={<SettingPage/>}/>
                    </Routes>
                    <Routes>
                        <Route path="/login" element={<LoginPage/>}/>
                    </Routes>
                </PageContent>
            </NavbarContainer>

        </BrowserRouter>
    );
}

export default App;