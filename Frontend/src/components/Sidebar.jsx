import { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import Logout from "../auth/Logout";

const Sidebar = () => {
  const [user, setUser] = useState("");
  const [role, setRole] = useState("");
  const [showAccount, setShowAccount] = useState(false);

  const userLogin = localStorage.getItem("userId");
  const userRole = localStorage.getItem("userRole");
 
  useEffect(() => {
    setUser(userLogin);
    setRole(userRole);
  },[userLogin, userRole]);

  function handleAccountClick() {
    setShowAccount(!showAccount);
    
  }

  return (
    <aside className="fixed top-0 left-0 z-20 h-screen bg-gray-800 text-white shadow-md flex flex-col">
      <div className="p-6 text-2xl font-extrabold"><Link to={"/"}>BookSpace</Link></div>
      <nav className="flex-1 overflow-y-auto">
        <ul>
          <li className="hover:bg-gray-700">
            <Link
              to="/book"
              className="block p-4 transition-colors duration-300"
            >
              Book
            </Link>
          </li>
          <li className="hover:bg-gray-700">
            <Link to={"/bookshelf"}
              className="block p-4 transition-colors duration-300"
            >
              Bookshelf
            </Link>
          </li>
          <li className="hover:bg-gray-700">
            <a
              href="#contact"
              className="block p-4 transition-colors duration-300"
            >
              Contact
            </a>
          </li>
          {role === "OFFICER" && (
            <li className="hover:bg-gray-700">
            <Link to={"/member"}
              href="#contact"
              className="block p-4 transition-colors duration-300"
            >
              Member
            </Link>
          </li>
          )
          }
          {role === "ADMIN" && (
            <li className="hover:bg-gray-700">
            <a
              href="#contact"
              className="block p-4 transition-colors duration-300"
            >
              Officer
            </a>
          </li>
          )
          }
        </ul>
      </nav>
      {!user && (
        <div className="bg-gray-700 p-4">
          <Link
            to="/login"
            className="block w-full text-center py-2 bg-blue-500 rounded hover:bg-blue-600 transition-colors duration-300"
          >
            Login
          </Link>
        </div>
      )}
      {user && (
        <div className="bg-gray-700 p-4">
          <div
            
            className="block w-full text-center py-2 bg-blue-500 rounded hover:bg-blue-600 transition-colors duration-300"
          >
            <Logout handleAccountClick={handleAccountClick} />
          </div>
        </div>
      )}
    </aside>
  );
};

export default Sidebar;
