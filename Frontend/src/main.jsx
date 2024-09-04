import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { createBrowserRouter } from "react-router-dom";
import { RouterProvider } from "react-router-dom";
import Home from "./pages/Home.jsx";
import Login from "./auth/Login.jsx";
import ForgotPassword from "./auth/ForgotPassword.jsx";
import Book from "./pages/Book.jsx";
import Member from "./pages/Member.jsx";
import Bookshelf from "./pages/Bookshelf.jsx";
import AddBookshelf from "./bookshelf/AddBookshelf.jsx";
import AddBook from "./book/AddBook.jsx";
import AddMember from "./officer/AddMember.jsx";
import ViewBookshelf from "./bookshelf/ViewBookshelf.jsx";
import Officer from "./pages/Officer.jsx";
import AddOfficer from "./admin/AddOfficer.jsx";
import ViewBook from "./book/ViewBook.jsx";
import ViewMember from "./officer/ViewMember.jsx";
import Borrowing from "./pages/Borrowing.jsx";
import ReturnBook from "./officer/ReturnBook.jsx";
import RecordBook from "./officer/RecordBook.jsx";
import ViewOfficer from "./admin/ViewOfficer.jsx";
import ViewBorrowing from "./officer/ViewBorrowing.jsx";
import Profile from "./pages/Profile.jsx";

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
        path: "/book",
        element: <Book />,
      },
      {
        path: "/member",
        element: <Member />,
      },
      {
        path: "/profile",
        element: <Profile />,
      },
      {
        path: "/view-member",
        element: <ViewMember />,
      },
      {
        path: "/officer",
        element: <Officer />,
      },      {
        path: "/view-officer",
        element: <ViewOfficer />,
      },
      {
        path: "/bookshelf",
        element: <Bookshelf />,
      },{
        path: "/create-bookshelf",
        element: <AddBookshelf />,
      },
      {
        path: "/view-bookshelf",
        element: <ViewBookshelf />,
      },
      {
        path: "/create-book",
        element: <AddBook />,
      },{
        path: "/view-book",
        element: <ViewBook />,
      },
      {
        path: "/create-member",
        element: <AddMember />,
      },{
        path: "/create-officer",
        element: <AddOfficer />,
      },      {
        path: "/borrowing",
        element: <Borrowing />,
      },
      {
        path: "/create-borrowing",
        element: <RecordBook />,
      },{
        path: "/create-return",
        element: <ReturnBook />,
      },{
        path: "/view-borrowing",
        element: <ViewBorrowing />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
