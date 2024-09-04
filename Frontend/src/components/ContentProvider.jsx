/* eslint-disable react-refresh/only-export-components */
/* eslint-disable react/prop-types */
import { createContext, useState, useContext } from "react";

// Membuat Context
const ContentContext = createContext();

// Membuat Provider
export const ContentProvider = ({ children }) => {
  const [contentType, setContentType] = useState("book");

  const field = [
    {
      book: {
        name: "book",
        bool: true,
        link: ["/create-book", "/view-book", "/update-book", "/delete-book"],
      },
    },
    {
      member: {
        name: "member",
        bool: true,
        link: ["/create-member", "/view-member", "/update-member", "/delete-member"],
      },
    },
    {
      officer: {
        name: "officer",
        bool: true,
        link: ["/create-officer", "/view-officer", "/update-officer", "/delete-officer"],
      },
    },
    {
      bookshelf: {
        name: "bookshelf",
        bool: true,
        link: ["/create-bookshelf", "/view-bookshelf", "/update-bookshelf", "/delete-bookshelf"],
      },
    },
    {
      borrowing: {
        name: "borrowing",
        bool: false,
        name2: "return book",
        link: ["/create-borrowing", "/create-return", "/view-borrowing", "/delete-borrowing"],
      },
    },
    {
      return: {
        name: "return book",
        link: ["/create-return", "/view-return", "/update-return", "/delete-return"],
      },
    },
  ];

  return (
    <ContentContext.Provider value={{ contentType, setContentType, field }}>
      {children}
    </ContentContext.Provider>
  );
};

// Custom hook untuk menggunakan Context
export const useContent = () => useContext(ContentContext);
