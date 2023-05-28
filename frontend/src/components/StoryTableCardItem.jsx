import React, {useState} from 'react';
import {ReactComponent as StarWhite} from "../assets/star.svg";
import {ReactComponent as Edit} from "../assets/edit.svg";
import {ReactComponent as Trash} from "../assets/trash.svg";
import styled from "styled-components";
import EditUserModal from "./EditUserModal";
import {deleteUserSaga} from "../redux/sagas";
import {deleteUserApi} from "../api";
import {useDispatch} from "react-redux";
import {DELETE_USER} from "../redux/constants";
import {deleteUserReq} from "../redux/actions/actionCreator";

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
const StoryTableCard = styled.div`
  border-radius: 8px;
  padding: 2px 0;
  display: flex;
  align-items: start;
  justify-content: space-between;
  position: relative;
  &:hover {
    box-shadow: 0px 4px 24px rgba(106, 122, 152, 0.15);
  }
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
const token = localStorage.getItem('token');
const role = JSON.parse(token)?.message

const StoryTableCardItem = ({it,id, handleDeleteUser}) => {
    const [showModal, setShowModal] = useState(false)
    const dispatch = useDispatch()
    const toggleModal = () => {
        setShowModal(!showModal)
    }

    const deleteUser = async () => {
        deleteUserApi(it.id)
        dispatch(deleteUserReq(it.id))
    }
    return (
        <>
            <StoryTableCard key={id}>
                <StarWhite style={{width: "5%", margin: 0,marginTop:'10px', cursor: "pointer", display: 'flex', alignItems: 'center', justifyContent:'space-between'}}/>
                <StoryTableTitle>
                    <StoryTableName>
                        <StoryTableNameImg src='https://pbs.twimg.com/profile_images/1356606198435438595/4Nad8CDb_400x400.jpg'/>
                        <StoryTableNameTitle>{it.name}</StoryTableNameTitle>
                    </StoryTableName>
                </StoryTableTitle>
                <StoryTableTitle>{it.education}</StoryTableTitle>
                <StoryTableTitleSmall>{it.position}</StoryTableTitleSmall>
                <StoryTableTitleSmall>{it.experience}</StoryTableTitleSmall>
                <StoryTableTitle>{it.phone}</StoryTableTitle>
                <StoryTableTitleSmall style={{display:'flex', cursor: "pointer"}}><Edit onClick={toggleModal} /></StoryTableTitleSmall>
                <StoryTableTitleSmall ><Trash onClick={deleteUser} style={{width:'18px',height:'24px', cursor: "pointer"}} /></StoryTableTitleSmall>
            </StoryTableCard>
            {
                showModal && <EditUserModal it={it} setShowModal={setShowModal} show={showModal}/>
            }
        </>
    );
};

export default StoryTableCardItem;