import React, {useState} from "react";
import styled from "styled-components";
import {ReactComponent as LogoFlower} from "../assets/logoFlower.svg";
import {ReactComponent as LogoAman} from "../assets/AmanLogo.svg";
import {ReactComponent as Down} from "../assets/down.svg";

const NavbarContainer = styled.div`
  width: 100%;
  border-bottom: 1px solid gray;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
`;
const NavbarLogos = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 110px;
  margin: 0;
`;
const NavbarUser = styled.button`
  background: rgba(103, 173, 255, 0.6);
  border-radius: 3px;
  padding: 4px 12px;
  border: none;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 0;
  position: relative;
`;
const NavbarUserText = styled.p`
  margin-right: 6px;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
`;
const NavbarUserLogout = styled.div`
  background: #E9F2FF;
  border-radius: 3px;
  padding: 4px 12px;
  border: none;
  display: flex;
  align-items: center;
  //justify-content: space-between;
  margin: 0;
  position: absolute;
  bottom: -40px;
  left: 0;
  width: 100%;
  height: 32px;
  cursor: pointer;
`;
const NavbarUserLogoutText = styled.p`
  text-align: center;
  margin: 0;
`;
const NavLogoutIcon = styled.div`
  width: 12px;
  height: 12px;
  background: rgba(9, 30, 66, 0.13);
  border-radius: 2px;
  margin: 0;
  margin-right: 12px;
`;

function NavBar() {
    const [show, setShow] = useState(false)
    const userName = JSON.parse(localStorage.getItem('token'))?.username
    const showLogout = () => {
        setShow(!show)
    }

    const logOutUser = () => {
        localStorage.removeItem('token')
        window.location.reload();
    }

    return (
        <NavbarContainer>
            <NavbarLogos>
                <LogoFlower/>
                <LogoAman/>
            </NavbarLogos>
            <NavbarUser>
                <NavbarUserText onClick={showLogout}>{userName}</NavbarUserText>
                <Down onClick={showLogout}/>
                {show && <NavbarUserLogout onClick={logOutUser}>
                    <NavLogoutIcon/>
                    <NavbarUserLogoutText>Выйти</NavbarUserLogoutText>
                </NavbarUserLogout>}
            </NavbarUser>
        </NavbarContainer>
    );
}

export default NavBar;
