/* eslint-disable react/prop-types */
import { useContext } from "react";
import { AuthContext } from "./AuthProvider";
import {  useNavigate } from "react-router-dom";

export default function Logout({ handleAccountClick }) {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    handleAccountClick();
    auth.handleLogout();
    navigate("/login", { state: { message: " You have been logged out!" } });
    window.location.reload()

  };

  return (
    <>
      
      <button className="dropdown-item" onClick={handleLogout}>
        Logout
      </button>
    </>
  );
}
