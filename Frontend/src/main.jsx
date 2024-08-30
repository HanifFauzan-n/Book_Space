import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { createBrowserRouter } from "react-router-dom";
import { RouterProvider } from "react-router-dom";
import Home from "./pages/Home.jsx";
import Login from "./auth/Login.jsx";
import ForgotPassword from "./auth/ForgotPassword.jsx";
import Registration from "./officer/Registration.jsx";
import Book from "./pages/Book.jsx";
import Member from "./pages/Member.jsx";
import Bookshelf from "./pages/Bookshelf.jsx";
import AddBookshelf from "./bookshelf/AddBookshelf.jsx";
import AddBook from "./book/AddBook.jsx";
import AddMember from "./officer/AddMember.jsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/forgot",
        element: <ForgotPassword />,
      },
      {
        path: "/regis",
        element: <Registration />,
      },
      {
        path: "/book",
        element: <Book />,
      },
      {
        path: "/member",
        element: <Member />,
      },{
        path: "/bookshelf",
        element: <Bookshelf />,
      },{
        path: "/create-bookshelf",
        element: <AddBookshelf />,
      },{
        path: "/create-book",
        element: <AddBook />,
      },{
        path: "/create-member",
        element: <AddMember />,
      }
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
