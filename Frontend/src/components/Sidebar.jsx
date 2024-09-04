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
  }, [userLogin, userRole]);

  function handleAccountClick() {
    setShowAccount(!showAccount);
  }

  return (
    <aside className="fixed top-0 left-0 z-20 h-screen bg-gray-800 text-white shadow-md flex flex-col">
      <div className="p-6 pb-3 text-2xl font-extrabold">
        <Link to={"/"}>
        <img className="w-32 h-28 pb-0 mb-0" src="src/image/Untitled Design-001.png" alt="" />
        {/* <h2>BookSpace</h2> */}
        </Link>
      </div>
      <nav className="flex-1 overflow-y-auto">
        <ul>
          {role === "ADMIN" && (
            <li className="hover:bg-gray-700">
            <Link
              to={"/profile"}
              className="block p-4 transition-colors duration-300"
            >
              Profile
            </Link>
          </li>
          )}
          <li className="hover:bg-gray-700">
            <Link
              to="/book"
              className="block p-4 transition-colors duration-300"
            >
              Book
            </Link>
          </li>
          <li className="hover:bg-gray-700">
            <Link
              to={"/bookshelf"}
              className="block p-4 transition-colors duration-300"
            >
              Bookshelf
            </Link>
          </li>

          {role === "OFFICER" && (
            <>
            <li className="hover:bg-gray-700">
                <Link
                  to={"/borrowing"}
                  className="block p-4 transition-colors duration-300"
                >
                  Borrowing
                </Link>
              </li>
              <li className="hover:bg-gray-700">
                <Link
                  to={"/member"}
                  className="block p-4 transition-colors duration-300"
                >
                  Member
                </Link>
              </li>
            </>
          )}
          {role === "ADMIN" && (
             <>
             <li className="hover:bg-gray-700">
                 <Link
                   to={"/borrowing"}
                   className="block p-4 transition-colors duration-300"
                 >
                   Borrowing
                 </Link>
               </li>
            <li className="hover:bg-gray-700">
              <Link
                to={"/officer"}
                className="block p-4 transition-colors duration-300"
              >
                Officer
              </Link>
            </li>
            </>
          )}
        </ul>
      </nav>
      {!user && (
          <Link
            to="/login"
            className="block w-4/5 self-center mb-4 text-center py-2 bg-blue-500 rounded hover:bg-blue-600 transition-colors duration-300"
          >
            Login
          </Link>
      )}
      {user && (
          <div className="block w-4/5 self-center text-center py-2 mb-4 bg-blue-500 rounded hover:bg-blue-600 transition-colors duration-300">
            <Logout handleAccountClick={handleAccountClick} />
          </div>
      )}
    </aside>
  );
};

export default Sidebar;
